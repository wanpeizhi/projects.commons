package com.chengjia.commons.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import com.chengjia.commons.exception.CustomException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 图片工具类
 * 
 * @author Administrator
 *
 */
public class ImageUtils {

	// 初始随机数
	private Random randomInit = new Random();

	/**
	 * 从网络下载图片
	 * 
	 * @param networkUrl
	 * @param targetUrl
	 * @throws CustomException
	 */
	public void fetchNetworkPic(String networkUrl, String targetUrl) throws CustomException {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream inStream = null;
		byte[] data = null;
		File imageFile = null;
		FileOutputStream outStream = null;
		try {
			// new一个URL对象
			url = new URL(networkUrl);
			// 打开链接
			conn = (HttpURLConnection) url.openConnection();
			// 设置请求方式为"GET"
			conn.setRequestMethod("GET");
			// 超时响应时间为5秒
			conn.setConnectTimeout(10 * 1000);
			// 通过输入流获取图片数据
			inStream = conn.getInputStream();
			// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
			data = readInputStream(inStream);
			// new一个文件对象用来保存图片，默认保存当前工程根目录
			imageFile = new File(targetUrl);
			// 创建输出流
			outStream = new FileOutputStream(imageFile);
			// 写入数据
			outStream.write(data);
			// 关闭输出流

		} catch (Exception e) {
			throw new CustomException("网络下载图片失败!" + e.getMessage(), e);
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
					outStream = null;
				}
				if (inStream != null) {
					inStream.close();
					inStream = null;
				}
				if (imageFile != null)
					imageFile = null;
				if (conn != null)
					conn.disconnect();
			} catch (Exception e) {
				throw new CustomException("文件流关闭失败!" + e.getMessage(), e);
			}
		}

	}

	/**
	 * 输入流转为字节数组
	 * 
	 * @param inStream
	 * @return
	 * @throws CustomException
	 */
	public byte[] readInputStream(InputStream inStream) throws CustomException {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// 创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			// 每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			// 使用一个输入流从buffer里把数据读取出来
			while ((len = inStream.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			// 关闭输入流
			inStream.close();
			// 把outStream里的数据写入内存
			return outStream.toByteArray();
		} catch (Exception e) {
			throw new CustomException("文件输入流读取失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 生成随机验证码文件,并返回验证码值
	 * 
	 * @param w
	 * @param h
	 * @param outputFile
	 * @param verifySize
	 * @return
	 * @throws IOException
	 */
	public String outputVerifyImage(int w, int h, File outputFile, int verifySize) throws IOException {
		String verifyCode = StringUtils.generateVerifyCode(verifySize);
		outputImage(w, h, outputFile, verifyCode);
		return verifyCode;
	}

	/**
	 * 输出随机验证码图片流,并返回验证码值
	 * 
	 * @param w
	 * @param h
	 * @param os
	 * @param verifySize
	 * @return
	 * @throws IOException
	 */
	public String outputVerifyImage(int w, int h, OutputStream os, int verifySize) throws IOException {
		String verifyCode = StringUtils.generateVerifyCode(verifySize);
		outputImage(w, h, os, verifyCode);
		return verifyCode;
	}

	/**
	 * 生成指定验证码图像文件
	 * 
	 * @param w
	 * @param h
	 * @param outputFile
	 * @param code
	 * @throws IOException
	 */
	public void outputImage(int w, int h, File outputFile, String code) throws IOException {
		if (outputFile == null) {
			return;
		}
		File dir = outputFile.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			outputFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(outputFile);
			outputImage(w, h, fos, code);
			fos.close();
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 输出指定验证码图片流
	 * 
	 * @param w
	 * @param h
	 * @param os
	 * @param code
	 * @throws IOException
	 */
	public void outputImage(int w, int h, OutputStream os, String code) throws IOException {
		int verifySize = code.length();
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Random rand = new Random();
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color[] colors = new Color[5];
		Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
				Color.ORANGE, Color.PINK, Color.YELLOW };
		float[] fractions = new float[colors.length];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
			fractions[i] = rand.nextFloat();
		}
		Arrays.sort(fractions);

		g2.setColor(Color.GRAY);// 设置边框色
		g2.fillRect(0, 0, w, h);

		Color c = getRandColor(200, 250);
		g2.setColor(c);// 设置背景色
		g2.fillRect(0, 2, w, h - 4);

		// 绘制干扰线
		Random random = new Random();
		g2.setColor(getRandColor(160, 200));// 设置线条的颜色
		for (int i = 0; i < 20; i++) {
			int x = random.nextInt(w - 1);
			int y = random.nextInt(h - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g2.drawLine(x, y, x + xl + 40, y + yl + 20);
		}

		// 添加噪点
		float yawpRate = 0.05f;// 噪声率
		int area = (int) (yawpRate * w * h);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int rgb = getRandomIntColor();
			image.setRGB(x, y, rgb);
		}

		shear(g2, w, h, c);// 使图片扭曲

		g2.setColor(getRandColor(100, 160));
		int fontSize = h - 4;
		Font font = new Font("Algerian", Font.ITALIC, fontSize);
		g2.setFont(font);
		char[] chars = code.toCharArray();
		for (int i = 0; i < verifySize; i++) {
			AffineTransform affine = new AffineTransform();
			affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
					(w / verifySize) * i + fontSize / 2, h / 2);
			g2.setTransform(affine);
			g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
		}

		g2.dispose();
		ImageIO.write(image, "jpg", os);
		os.flush();
		os.close();
	}

	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + randomInit.nextInt(bc - fc);
		int g = fc + randomInit.nextInt(bc - fc);
		int b = fc + randomInit.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private int getRandomIntColor() {
		int[] rgb = getRandomRgb();
		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}

	private int[] getRandomRgb() {
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = randomInit.nextInt(255);
		}
		return rgb;
	}

	private void shear(Graphics g, int w1, int h1, Color color) {
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	private void shearX(Graphics g, int w1, int h1, Color color) {
		int period = randomInit.nextInt(2);
		boolean borderGap = true;
		int frames = 1;
		int phase = randomInit.nextInt(2);
		for (int i = 0; i < h1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}
	}

	private void shearY(Graphics g, int w1, int h1, Color color) {
		int period = randomInit.nextInt(40) + 10; // 50;
		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}
		}
	}

	/**
	 * 图片尺寸不变，修改图片类型
	 * 
	 * @param sourceFile
	 * @param restFileName不含后缀
	 * @param type
	 * @throws Exception
	 */
	public void thumbImgType(String sourceFile, String restFileName, String type) throws Exception {
		Thumbnails.of(sourceFile).scale(1f).outputFormat(type).toFile(restFileName);
	}

	/**
	 * 图片尺寸不变，压缩图片文件大小,上传图片时使用
	 * 
	 * @param sourceFile
	 * @param restFileName
	 * @param type注意使用该方法时输出的图片格式必须为jpg（即outputFormat("jpg")
	 * @param quality输出的图片质量，范围：0.0~1.0，1为最高质量。
	 * @throws Exception
	 */
	public void thumbImgQuality(String sourceFile, String restFileName, String type, float quality) throws Exception {
		Thumbnails.of(sourceFile).scale(1f).outputQuality(quality).outputFormat(type).toFile(restFileName);
	}

	/**
	 * 图片尺寸不变，压缩图片文件大小,上传图片时使用
	 * 
	 * @param is
	 * @param restFileName
	 * @param type注意使用该方法时输出的图片格式必须为jpg（即outputFormat("jpg")
	 * @param quality输出的图片质量，范围：0.0~1.0，1为最高质量。
	 * @throws Exception
	 */
	public void thumbImgQuality(InputStream is, String restFileName, String type, float quality) throws Exception {
		Thumbnails.of(is).scale(1f).outputQuality(quality).outputFormat(type).toFile(restFileName);
	}

	/**
	 * 图片尺寸不变，压缩图片文件大小,上传图片时使用
	 * 
	 * @param is
	 * @param os
	 * @param type注意使用该方法时输出的图片格式必须为jpg（即outputFormat("jpg")
	 * @param quality输出的图片质量，范围：0.0~1.0，1为最高质量。
	 * @throws Exception
	 */
	public void thumbImgQuality(InputStream is, OutputStream os, String type, float quality) throws Exception {
		Thumbnails.of(is).scale(1f).outputQuality(quality).outputFormat(type).toOutputStream(os);
	}

	/**
	 * 图片尺寸不变，压缩图片文件大小,上传图片时使用
	 * 
	 * @param sourceFileName
	 * @param os
	 * @param type注意使用该方法时输出的图片格式必须为jpg（即outputFormat("jpg")
	 * @param quality输出的图片质量，范围：0.0~1.0，1为最高质量。
	 * @throws Exception
	 */
	public void thumbImgQuality(String sourceFileName, OutputStream os, String type, float quality) throws Exception {
		Thumbnails.of(sourceFileName).scale(1f).outputQuality(quality).outputFormat(type).toOutputStream(os);
	}

	/**
	 * 压缩至指定图片尺寸（例如：横400高300）
	 * 
	 * @param sourceFile
	 * @param restFileName
	 * @param type
	 * @param w
	 * @param h
	 * @param keepAspectRatio是否保持原图比例
	 * @throws Exception
	 */
	public void thumbImgSize(String sourceFile, String restFileName, String type, int w, int h, boolean keepAspectRatio)
			throws Exception {
		Thumbnails.of(sourceFile).size(w, h).keepAspectRatio(false).outputFormat(type).toFile(restFileName);
	}

	/**
	 * 压缩至指定图片尺寸（例如：横400高300）
	 * 
	 * @param sourceFile
	 * @param os
	 * @param type
	 * @param w
	 * @param h
	 * @param keepAspectRatio
	 * @return
	 * @throws Exception
	 */
	public OutputStream thumbImgSize(String sourceFile, OutputStream os, String type, int w, int h,
			boolean keepAspectRatio) throws Exception {
		Thumbnails.of(sourceFile).size(w, h).keepAspectRatio(false).outputFormat(type).toOutputStream(os);
		return os;
	}

	/**
	 * 压缩至原图片的百分之多少。
	 * 
	 * @param sourceFile
	 * @param restFileName
	 * @param type
	 * @param scale,0~1
	 * @throws Exception
	 */
	public void thumbImgScale(String sourceFile, String restFileName, String type, float scale) throws Exception {
		Thumbnails.of(sourceFile).scale(scale).outputFormat(type).toFile(restFileName);
	}

	/**
	 * 压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉
	 * 
	 * @param sourceFile
	 * @param restFileName
	 * @param type
	 * @param w
	 * @param h
	 * @throws Exception
	 */
	public void thumbImgCut(String sourceFile, String restFileName, String type, int w, int h) throws Exception {
		BufferedImage image = ImageIO.read(new File(sourceFile));
		Builder<BufferedImage> builder = null;
		int imageWidth = image.getWidth();
		int imageHeitht = image.getHeight();
		if ((float) w / h != (float) imageWidth / imageHeitht) {
			if (imageWidth > imageHeitht) {
				image = Thumbnails.of(sourceFile).height(h).asBufferedImage();
			} else {
				image = Thumbnails.of(sourceFile).width(w).asBufferedImage();
			}
			builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, w, h).size(w, h);
		} else {
			builder = Thumbnails.of(image).size(w, h);
		}

		builder.outputFormat(type).toFile(restFileName);
	}

	/**
	 * 图片旋转
	 * 
	 * @param sourceFile
	 * @param restFileName
	 * @param type
	 * @param rote
	 * @throws Exception
	 */
	public void thumbImgRote(String sourceFile, String restFileName, String type, double rote) throws Exception {
		Thumbnails.of(sourceFile).rotate(rote).outputFormat(type).toFile(restFileName);
	}

	/**
	 * 图片加水印
	 * 
	 * @param sourceFile
	 * @param restFileName
	 * @param waterPic
	 * @param type
	 * @param alpha透明度0-1
	 * @throws Exception
	 */
	public void thumbWatermark(String sourceFile, String restFileName, String waterPic, String type, float alpha)
			throws Exception {
		Thumbnails.of(sourceFile).size(400, 400)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(waterPic)), alpha).outputQuality(0.8f)
				.toFile(restFileName);
	}

	/**
	 * 输出成文件流OutputStream
	 * 
	 * @param sourceFile
	 * @param restFile
	 * @return
	 * @throws Exception
	 */
	public OutputStream readToOs(String sourceFile, String restFile) throws Exception {
		OutputStream os = new FileOutputStream(restFile);
		Thumbnails.of(sourceFile).toOutputStream(os);
		return os;
	}

	/**
	 * 输出BufferedImage
	 * 
	 * @param sourceFile
	 * @param restFile
	 * @throws Exception
	 */
	public BufferedImage readToBufferImg(String sourceFile, String restFile) throws Exception {
		BufferedImage bi = Thumbnails.of(sourceFile).asBufferedImage();
		ImageIO.write(bi, "jpg", new File(restFile));
		return bi;
	}

}
