package client;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import service.Service;
import util.Cache;

public class Client2 {
	
	private static void downloadPicture(String urlList) {
        URL url = null;
        int imageNumber = 0;

        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            String imageName =  "D:/test.jpg";

            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
		String url = "http://timeline.infinitex.cn/img/3c/89c24536407dd2d1a5329d5dd28ab3.png";
		System.out.println(Cache.getHeader("http://timeline.infinitex.cn/img/d5/53f363b508f8aafa1634dd3970dc5a.jpg", "zxl"));
		/*
		URL url = null;
		try {
			url = new URL("http://timeline.infinitex.cn/img/3c/89c24536407dd2d1a5329d5dd28ab3.png");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon image = new ImageIcon(url);
		System.out.println(image.getImage());
		Image image2 = image.getImage();
		File f = new File(image2);
		System.out.println();
		*/
		downloadPicture(url);
	}
}
