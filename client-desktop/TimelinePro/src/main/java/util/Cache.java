package util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;

public class Cache {
	/*
	private static void savePic(ImageIcon imageIcon, String path) {
		// 判断download文件夹是否存在
		String dirPath = System.getProperty("user.dir") + "\\download\\";
		File file = new File(dirPath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		Image image = imageIcon.getImage();
		File file2 = new File("path");

		int w = image.getWidth(imageIcon.getImageObserver());
		int h = image.getHeight(imageIcon.getImageObserver());
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = bi.getGraphics();
		try {
			g.drawImage(image, 0, 0, null);
			// 将BufferedImage变量写入文件中。
			ImageIO.write(bi, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	private static void savePic(String urlList, String path) {
		// 判断download文件夹是否存在
		String dirPath = System.getProperty("user.dir") + "\\download\\";
		File file = new File(dirPath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		
		
		URL url = null;
		int imageNumber = 0;

		try {
			url = new URL(urlList);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());

			String imageName = path;

			FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int length;

			while ((length = dataInputStream.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			byte[] context = output.toByteArray();
			fileOutputStream.write(output.toByteArray());
			dataInputStream.close();
			fileOutputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean fileExist(String url) {
		String[] parts = url.split("/");
		String path = System.getProperty("user.dir") + "\\download\\" + parts[parts.length - 2] + "_"
				+ parts[parts.length - 1];
		File file = new File(path);
		if (file.isFile()) {
			return true;
		}
		return false;
	}

	public static ImageIcon getImageIcon(String url) {
		URL imageUrl = null;
		ImageIcon image = null;
		String[] parts = url.split("/");
		String path = System.getProperty("user.dir") + "\\download\\" + parts[parts.length - 2] + "_"
				+ parts[parts.length - 1];
		if (fileExist(url)) {
			image = new ImageIcon(path);
			return image;
		}
		
		savePic(url, path);
		return getImageIcon(url);
	}

	public static ImageIcon getHeader(String url, String username) {
		URL imageUrl = null;
		ImageIcon image = null;
		String[] parts = url.split("/");
		String path = System.getProperty("user.dir") + "\\header\\" + username + ".jpg";
		if (new File(path).exists()) {
			image = new ImageIcon(path);
			return image;
		}
		saveHeaderPic(url, path);
		return getHeader(url, username);
	}

	private static void saveHeaderPic(String urlList, String path) {
		// 判断header文件夹是否存在
		String dirPath = System.getProperty("user.dir") + "\\header\\";
		File file = new File(dirPath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		
		URL url = null;

		try {
			url = new URL(urlList);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());

			String imageName = path;

			FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int length;

			while ((length = dataInputStream.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			byte[] context = output.toByteArray();
			fileOutputStream.write(output.toByteArray());
			dataInputStream.close();
			fileOutputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
