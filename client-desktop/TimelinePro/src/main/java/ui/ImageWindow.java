package ui;

import java.awt.EventQueue;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import util.Cache;

import java.awt.Color;
import java.awt.FlowLayout;

public class ImageWindow {

	private static JFrame frame = new JFrame();
	
	public static JFrame getImageWindow(String url) {
		initialize(url);
		return frame;
	}

	public ImageWindow(String url) {
		initialize(url);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize(String url) {
		//frame = new JFrame();
		frame.getContentPane().setBackground(new Color(64, 64, 64));
		frame.setBounds(700, 300, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

		ImageIcon imageIcon = Cache.getImageIcon(url);
		
		double width = imageIcon.getImage().getWidth(imageIcon.getImageObserver());
		double height = imageIcon.getImage().getHeight(imageIcon.getImageObserver());
		//double setsize = height / width;
		while(width > 800 || height > 800) {
			width /= 1.1;
			height /= 1.1;
		}
		imageIcon.setImage(imageIcon.getImage().getScaledInstance((int)width, (int)height, Image.SCALE_DEFAULT));
		JLabel imageLabel = new JLabel(imageIcon);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(imageLabel);
		frame.pack();
	}

}
