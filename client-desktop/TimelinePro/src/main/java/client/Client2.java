package client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import service.Service;

public class Client2 {
	public static void main(String[] args) {
		/*
		URL url = null;
		try {
			url = new URL("http://timeline.infinitex.cn/img/e1/93c91859233c47410d379ef94a6034.jpg");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon imageIcon2 = new ImageIcon(url);
		Image image = imageIcon2.getImage();
		*/
		
		try {
			System.out.println(Service.uploadImage(new File("H:\\61758PICWZY_1024.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
