package client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import javax.swing.ImageIcon;

import service.Service;
import util.Cache;

public class Client2 {
	public static void main(String[] args) {
		//System.out.println(Cache.getHeader("http://timeline.infinitex.cn/img/d5/53f363b508f8aafa1634dd3970dc5a.jpg", "zxl"));
		File f1 = new File("H://1.jpg");
		File f2 = new File("H://2.jpg");
		try {
			Files.copy(f1.toPath(), f2.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
