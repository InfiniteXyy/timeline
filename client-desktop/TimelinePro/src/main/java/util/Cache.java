package util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Cache {
	private static void savePic(ImageIcon imageIcon, String path) {
		//判断download文件夹是否存在
		String dirPath = System.getProperty("user.dir") + "\\download\\";
		File file = new File(dirPath);
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		Image image = imageIcon.getImage();
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
	
	private static boolean fileExist(String url) {
		String[] parts = url.split("/");
		String path = System.getProperty("user.dir") + "\\download\\" + 
				parts[parts.length - 2] + "_" + parts[parts.length - 1];
		File file = new File(path);
		if(file.isFile()) {
			return true;
		}
		return false;
	}
	
	public static ImageIcon getImageIcon(String url) {
		URL imageUrl = null;
		ImageIcon image = null;
		String[] parts = url.split("/");
		String path = System.getProperty("user.dir") + "\\download\\" + 
				parts[parts.length - 2] + "_" + parts[parts.length - 1];
		if(fileExist(url)) {
			image = new ImageIcon(path);
			return image;
		}
		try {
			imageUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		image = new ImageIcon(imageUrl);
		savePic(image, path);
		return image;
	}
}
