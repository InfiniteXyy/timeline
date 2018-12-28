package client;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

public class Client2 {
	public static void main(String[] args) {
		URL url = null;
		try {
			url = new URL("http://timeline.infinitex.cn/img/e1/93c91859233c47410d379ef94a6034.jpg");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon imageIcon2 = new ImageIcon(url);
		Image image = imageIcon2.getImage();
		
	}
}
