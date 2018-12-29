package client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import service.Service;
import util.Cache;

public class Client2 {
	public static void main(String[] args) {
		System.out.println(Cache.getHeader("http://timeline.infinitex.cn/img/d5/53f363b508f8aafa1634dd3970dc5a.jpg", "zxl"));
	}
}
