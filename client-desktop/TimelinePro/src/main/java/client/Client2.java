package client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import service.Service;
import util.Cache;

public class Client2 {
	public static void main(String[] args) {
		try {
			System.out.println(Service.uploadImage(new File("D://timg.jpg")));
			ImageIcon imageIcon = Cache.getImageIcon("http://timeline.infinitex.cn/img/99/94914517e73d35c7f6adf502d77ec7.jpg");
			Image image = imageIcon.getImage();
			System.out.println(image.getWidth(imageIcon.getImageObserver()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
