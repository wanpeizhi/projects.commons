package com.chengjia.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.chengjia.commons.common.Enums;
import com.chengjia.commons.exception.CustomException;

public class FileUtils extends org.apache.commons.io.FileUtils {

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static void newFolder(String folderPath) throws CustomException {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.setWritable(true, false);
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			throw new CustomException("新建目录失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 新建文件
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static void newFile(String folderPath, String fileName) throws CustomException {
		try {
			newFolder(folderPath);
			File file = new File(folderPath + "/" + fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			throw new CustomException("新建目录失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 将byte数组写到某个文件
	 * 
	 * @param file
	 * @param data
	 * @throws IOException
	 */
	public static void newFile(File file, byte[] data) throws CustomException {
		final int MAX_BUFFER_SIZE = 4096;
		FileOutputStream output = null;
		FileChannel fc = null;
		try {
			output = new FileOutputStream(file);
			fc = output.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(MAX_BUFFER_SIZE);
			int offset = 0;
			while (offset < data.length) {
				buffer.clear();
				int len = data.length - offset;
				if (len > MAX_BUFFER_SIZE)
					len = MAX_BUFFER_SIZE;
				buffer.put(data, offset, len);
				offset += len;
				buffer.flip();
				fc.write(buffer);
			}
		} catch (Exception e) {
			throw new CustomException("文件读写失败!" + e.getMessage(), e);
		} finally {
			if (fc != null) {
				try {
					fc.close();
					fc = null;
				} catch (IOException e) {
					throw new CustomException("文件读写失败!" + e.getMessage(), e);
				}
			}
			if (output != null) {
				try {
					output.close();
					output = null;
				} catch (IOException e) {
					throw new CustomException("文件读写失败!" + e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) throws CustomException {
		int byteread = 0;
		InputStream is = null;
		FileOutputStream os = null;
		try {
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				is = new FileInputStream(oldPath); // 读入原文件
				os = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = is.read(buffer)) != -1) {
					os.write(buffer, 0, byteread);
				}
			} else {
				throw new CustomException("文件复制失败,源文件不存在!");
			}
		} catch (Exception e) {
			throw new CustomException("文件复制失败!" + e.getMessage(), e);
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (Exception e) {
				throw new CustomException("文件复制失败!" + e.getMessage(), e);
			}
			try {
				if (os != null) {
					os.close();
					os = null;
				}
			} catch (Exception e) {
				throw new CustomException("文件复制失败!" + e.getMessage(), e);
			}

		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) throws CustomException {
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
					output = null;
					input = null;
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			throw new CustomException("文件夹复制失败!" + e.getMessage(), e);
		}

	}

	/**
	 * 移动文件
	 */
	public static void moveFile(String srcFile, String destPath, String destFileName) throws CustomException {
		if (srcFile == null || destPath == null || destFileName == null || srcFile.length() == 0
				|| destPath.length() == 0 || destFileName.length() == 0)
			throw new CustomException("源文件或目标文件路径参数为空!");
		try {
			File file = new File(srcFile);
			if (!file.exists())
				throw new CustomException("源文件不存在!");
			File targePath = new File(destPath);
			if (!targePath.exists())
				targePath.mkdirs();
			file.renameTo(new File(destPath, destFileName));
		} catch (Exception e) {
			throw new CustomException("文件移动失败!" + e.getMessage(), e);
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) throws CustomException {
		try {
			File file = new File(filePathAndName);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			throw new CustomException("文件删除失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 删除文件夹和下面的所有文件、文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) throws CustomException {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			File folder = new File(folderPath);
			if (folder.exists())
				folder.delete(); // 删除空文件夹
		} catch (Exception e) {
			throw new CustomException("文件夹删除失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) throws CustomException {
		try {
			File file = new File(path);
			if (!file.exists()) {
				throw new CustomException(path + "文件不存在!");
			}
			if (!file.isDirectory()) {
				throw new CustomException(path + "不是目录文件!");
			}

			String[] tempList = file.list();
			File temp = null;
			for (int i = 0; i < tempList.length; i++) {
				if (path.endsWith(File.separator)) {
					temp = new File(path + tempList[i]);
				} else {
					temp = new File(path + File.separator + tempList[i]);
				}
				if (temp.isFile()) {
					temp.delete();
				}
				if (temp.isDirectory()) {
					delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
					delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				}
			}
		} catch (Exception e) {
			throw new CustomException("删除文件夹内的所有文件和目录失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 读取文件到流
	 * 
	 * @param file
	 * @param output
	 * @throws IOException
	 */
	public static void readFile(File file, OutputStream output) throws CustomException {
		FileInputStream input = null;
		FileChannel fc = null;
		try {
			input = new FileInputStream(file);
			fc = input.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(4096);
			for (;;) {
				buffer.clear();
				int n = fc.read(buffer);
				if (n == (-1))
					break;
				output.write(buffer.array(), 0, buffer.position());
			}
			output.flush();
		} catch (Exception e) {
			throw new CustomException("文件读取失败!" + e.getMessage(), e);
		} finally {
			if (fc != null) {
				try {
					fc.close();
				} catch (IOException e) {
					throw new CustomException("文件读取失败!" + e.getMessage(), e);
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new CustomException("文件读取失败!" + e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 获得文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension(String fileName) throws CustomException {
		try {
			if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
				return fileName.substring(fileName.lastIndexOf(".") + 1);
			} else {
				return fileName;
			}
		} catch (Exception e) {
			throw new CustomException("获取文件后缀失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 获得文件名不含后缀
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) throws CustomException {
		try {
			if ((filename != null) && (filename.length() > 0)) {
				int dot = filename.lastIndexOf('.');
				if ((dot > -1) && (dot < (filename.length()))) {
					return filename.substring(0, dot);
				}
			}
			return filename;
		} catch (Exception e) {
			throw new CustomException("获取文件名失败!" + e.getMessage(), e);
		}
	}
	
	/**
	 * 获得文件名,不含路径，含后缀
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoPath(String filename) throws CustomException {
		try {
			if ((filename != null) && (filename.length() > 0)) {
				int dot = filename.lastIndexOf('/');
				if ((dot > -1) && (dot < (filename.length()))) {
					return filename.substring(dot+1);
				}
			}
			return filename;
		} catch (Exception e) {
			throw new CustomException("获取文件名失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 获得文件头类型
	 * 
	 * @param fileUrl
	 * @return
	 * @throws java.io.IOException
	 */
	public static String getMimeType(String fileUrl) throws CustomException {
		try {
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			String type = fileNameMap.getContentTypeFor(fileUrl);
			return type;
		} catch (Exception e) {
			throw new CustomException("获取文件头类型失败!" + e.getMessage(), e);
		}
	}

	/**
	 * 获取文件类型
	 * 
	 * @param mime
	 * @return
	 */
	public static String getFileTypeByMime(String mime) throws CustomException {
		try {
			if (mime.contains("video")) {
				return "video";
			} else if (mime.contains("image")) {
				return "image";
			} else if (mime.contains("audio")) {
				return "voice";
			} else {
				throw new CustomException("未知的文件类型!" + mime);
			}
		} catch (Exception e) {
			throw new CustomException("获取文件类型失败!" + mime);
		}
	}

	/**
	 * 获取文件类型
	 * 
	 * @param extensions
	 * @return
	 */
	public static String getFileType(String extensions) throws CustomException {
		try {
			if (Enums.AttachmentExtensionsEnum.DOC.getValue().contains(extensions)) {
				return Enums.AttachmentExtensionsEnum.DOC.name();
			} else if (Enums.AttachmentExtensionsEnum.IMG.getValue().contains(extensions)) {
				return Enums.AttachmentExtensionsEnum.IMG.name();
			} else if (Enums.AttachmentExtensionsEnum.MEDIA.getValue().contains(extensions)) {
				return Enums.AttachmentExtensionsEnum.MEDIA.name();
			} else {
				throw new CustomException("未知的文件类型!" + extensions);
			}
		} catch (Exception e) {
			throw new CustomException("获取文件类型失败!" + extensions);
		}
	}

	/**
	 * 获取根目录
	 * 
	 * @return
	 */
	public static String serverRootDirectory(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath(File.separator);
	}

	/**
	 * 自定义文件名称
	 * 
	 * @return
	 */
	public static String createFileName() {
		SimpleDateFormat simpledateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Random random = new Random();
		return simpledateFormat.format(new Date()) + random.nextInt(10000);
	}
}
