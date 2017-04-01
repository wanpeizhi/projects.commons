package com.chengjia.commons.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.chengjia.commons.exception.CustomException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码生成解析类
 * 
 * @author Administrator
 *
 */
public class QrcodeUtils {

	/**
	 * logo
	 * 
	 * @author Administrator
	 *
	 */
	static class LogoConfig {
		// logo默认边框颜色
		public static final Color DEFAULT_BORDER_COLOR = Color.WHITE;
		// logo默认边框宽度
		public static final int DEFAULT_BORDER = 2;
		// logo大小默认为照片的1/5
		public static final int DEFAULT_LOGOPART = 5;

		private final int border = DEFAULT_BORDER;
		private final Color borderColor;
		private final int logoPart;

		public LogoConfig() {
			this(DEFAULT_BORDER_COLOR, DEFAULT_LOGOPART);
		}

		public LogoConfig(Color borderColor, int logoPart) {
			this.borderColor = borderColor;
			this.logoPart = logoPart;
		}

		public Color getBorderColor() {
			return borderColor;
		}

		public int getBorder() {
			return border;
		}

		public int getLogoPart() {
			return logoPart;
		}

	}

	final class BufferedImageLuminanceSource extends LuminanceSource {

		private static final double MINUS_45_IN_RADIANS = -0.7853981633974483; // Math.toRadians(-45.0)
		private final BufferedImage image;
		private final int left;
		private final int top;

		public BufferedImageLuminanceSource(BufferedImage image) {
			this(image, 0, 0, image.getWidth(), image.getHeight());
		}

		public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
			super(width, height);

			if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
				this.image = image;
			} else {
				int sourceWidth = image.getWidth();
				int sourceHeight = image.getHeight();
				if (left + width > sourceWidth || top + height > sourceHeight) {
					throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
				}

				this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);

				WritableRaster raster = this.image.getRaster();
				int[] buffer = new int[width];
				for (int y = top; y < top + height; y++) {
					image.getRGB(left, y, width, 1, buffer, 0, sourceWidth);
					for (int x = 0; x < width; x++) {
						int pixel = buffer[x];

						// The color of fully-transparent pixels is irrelevant.
						// They are often, technically, fully-transparent
						// black (0 alpha, and then 0 RGB). They are often used,
						// of course as the "white" area in a
						// barcode image. Force any such pixel to be white:
						if ((pixel & 0xFF000000) == 0) {
							pixel = 0xFFFFFFFF; // = white
						}

						// .229R + 0.587G + 0.114B (YUV/YIQ for PAL and NTSC)
						buffer[x] = (306 * ((pixel >> 16) & 0xFF) + 601 * ((pixel >> 8) & 0xFF) + 117 * (pixel & 0xFF)
								+ 0x200) >> 10;
					}
					raster.setPixels(left, y, width, 1, buffer);
				}

			}
			this.left = left;
			this.top = top;
		}

		@Override
		public byte[] getRow(int y, byte[] row) {
			if (y < 0 || y >= getHeight()) {
				throw new IllegalArgumentException("Requested row is outside the image: " + y);
			}
			int width = getWidth();
			if (row == null || row.length < width) {
				row = new byte[width];
			}
			// The underlying raster of image consists of bytes with the
			// luminance values
			image.getRaster().getDataElements(left, top + y, width, 1, row);
			return row;
		}

		@Override
		public byte[] getMatrix() {
			int width = getWidth();
			int height = getHeight();
			int area = width * height;
			byte[] matrix = new byte[area];
			// The underlying raster of image consists of area bytes with the
			// luminance values
			image.getRaster().getDataElements(left, top, width, height, matrix);
			return matrix;
		}

		@Override
		public boolean isCropSupported() {
			return true;
		}

		@Override
		public LuminanceSource crop(int left, int top, int width, int height) {
			return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);
		}

		/**
		 * This is always true, since the image is a gray-scale image.
		 *
		 * @return true
		 */
		@Override
		public boolean isRotateSupported() {
			return true;
		}

		@Override
		public LuminanceSource rotateCounterClockwise() {
			int sourceWidth = image.getWidth();
			int sourceHeight = image.getHeight();

			// Rotate 90 degrees counterclockwise.
			AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);

			// Note width/height are flipped since we are rotating 90 degrees.
			BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);

			// Draw the original image into rotated, via transformation
			Graphics2D g = rotatedImage.createGraphics();
			g.drawImage(image, transform, null);
			g.dispose();

			// Maintain the cropped region, but rotate it too.
			int width = getWidth();
			return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(),
					width);
		}

		@Override
		public LuminanceSource rotateCounterClockwise45() {
			int width = getWidth();
			int height = getHeight();

			int oldCenterX = left + width / 2;
			int oldCenterY = top + height / 2;

			// Rotate 45 degrees counterclockwise.
			AffineTransform transform = AffineTransform.getRotateInstance(MINUS_45_IN_RADIANS, oldCenterX, oldCenterY);

			int sourceDimension = Math.max(image.getWidth(), image.getHeight());
			BufferedImage rotatedImage = new BufferedImage(sourceDimension, sourceDimension,
					BufferedImage.TYPE_BYTE_GRAY);

			// Draw the original image into rotated, via transformation
			Graphics2D g = rotatedImage.createGraphics();
			g.drawImage(image, transform, null);
			g.dispose();

			int halfDimension = Math.max(width, height) / 2;
			int newLeft = Math.max(0, oldCenterX - halfDimension);
			int newTop = Math.max(0, oldCenterY - halfDimension);
			int newRight = Math.min(sourceDimension - 1, oldCenterX + halfDimension);
			int newBottom = Math.min(sourceDimension - 1, oldCenterY + halfDimension);

			return new BufferedImageLuminanceSource(rotatedImage, newLeft, newTop, newRight - newLeft,
					newBottom - newTop);
		}

	}

	/**
	 * 给二维码图片添加Logo
	 * 
	 * @param qrPic
	 * @param logoPic
	 */
	public void addLogo_QRCode(File qrPic, File logoPic, LogoConfig logoConfig) throws CustomException {
		if (!qrPic.isFile() || !logoPic.isFile()) {
			throw new CustomException("file not find");
		}

		try {

			/**
			 * 读取二维码图片，并构建绘图对象
			 */
			BufferedImage image = ImageIO.read(qrPic);
			Graphics2D g = image.createGraphics();

			/**
			 * 读取Logo图片
			 */
			BufferedImage logo = ImageIO.read(logoPic);

			int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();

			// 计算图片放置位置
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - logo.getHeight()) / 2;

			// 开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, widthLogo, heightLogo);

			g.dispose();

			ImageIO.write(image, "jpeg", qrPic);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 二维码的解析
	 * 
	 * @param file
	 */
	public void parseQR_CODEImage(File file) {
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			if (!file.exists()) {
				return;
			}
			BufferedImage image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			Result result = formatReader.decode(binaryBitmap, hints);

			System.out.println("result = " + result.toString());
			System.out.println("resultFormat = " + result.getBarcodeFormat());
			System.out.println("resultText = " + result.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将二维码生成为文件
	 * 
	 * @param bm
	 * @param imageFormat
	 * @param file
	 */
	public void decodeQR_CODE2ImageFile(BitMatrix bm, String imageFormat, File file) {
		try {
			if (null == file || file.getName().trim().isEmpty()) {
				throw new IllegalArgumentException("文件异常，或扩展名有问题！");
			}
			BufferedImage bi = fileToBufferedImage(bm);
			ImageIO.write(bi, "jpeg", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将二维码生成为输出流
	 * 
	 * @param content
	 * @param imageFormat
	 * @param os
	 */
	public void decodeQR_CODE2OutputStream(BitMatrix bm, String imageFormat, OutputStream os) {
		try {
			BufferedImage image = fileToBufferedImage(bm);
			ImageIO.write(image, imageFormat, os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构建初始化二维码
	 * 
	 * @param bm
	 * @return
	 */
	public BufferedImage fileToBufferedImage(BitMatrix bm) {
		BufferedImage image = null;
		try {
			int w = bm.getWidth(), h = bm.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * 生成二维码bufferedImage图片
	 * 
	 * @param content
	 *            编码内容
	 * @param barcodeFormat
	 *            编码类型
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @param hints
	 *            设置参数
	 * @return
	 */
	public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height,
			Map<EncodeHintType, ?> hints) {
		MultiFormatWriter multiFormatWriter = null;
		BitMatrix bm = null;
		BufferedImage image = null;
		try {
			multiFormatWriter = new MultiFormatWriter();

			// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);

			int w = bm.getWidth();
			int h = bm.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			// 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
				}
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * 设置二维码的格式参数
	 * 
	 * @return
	 */
	public Map<EncodeHintType, Object> getDecodeHintType() {
		// 用于设置QR二维码参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);

		return hints;
	}

	public static void main(String[] args) throws WriterException {
		// String content = "【优秀员工】恭喜您，中奖了！！！领取方式，请拨打电话：15998099997*咨询。";
		String content = "http://yunzhongbang.com/wap/6";

		try {
			File file = file = new File("D:/", new Date().getTime() + ".jpg");
			if (!file.exists()) {
				file.createNewFile();

			}

			QrcodeUtils zp = new QrcodeUtils();
			BufferedImage bim = zp.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 300, 100,
					zp.getDecodeHintType());
			ImageIO.write(bim, "jpeg", file);

			// zp.addLogo_QRCode(file, new File("D:/111.jpg"), new
			// LogoConfig());

			Thread.sleep(5000);
			// zp.parseQR_CODEImage(new File("D:/newPic.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
