package ui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import entity.Message;
import util.Cache;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;

public class MessagePanel extends JPanel {

	URL headUrl = null;
	URL imageUrl = null;
	ImageIcon imageIcon = null;
	ImageIcon headIcon = null;
	private JLabel usernameLabel = new JLabel();
	GridBagConstraints gbc_lblNewLabel;
	JLabel imageLabel = new JLabel();
	private Message myMessage = null;
	/**
	 * Create the panel.
	 */
	public MessagePanel(Message message) {
		myMessage = message;
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = null;
		
		if(!message.getImageUrl().equals("")) {
			this.setPreferredSize(new Dimension(350, 300));
			gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] {350};
			gridBagLayout.rowHeights = new int[] {50, 100, 150};
			gridBagLayout.columnWeights = new double[]{1.0};
			gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			setLayout(gridBagLayout);
		} else {
			this.setPreferredSize(new Dimension(350, 60));
			gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[] {350};
			gridBagLayout.rowHeights = new int[] {20, 40};
			gridBagLayout.columnWeights = new double[]{1.0};
			gridBagLayout.rowWeights = new double[]{1.0, 2.0};
			setLayout(gridBagLayout);
		}
		
		
		try {
			headUrl = new URL(message.getAuthor().getImage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ImageIcon headIcon = new ImageIcon(headUrl);
		headIcon = new ImageIcon(headUrl);
		headIcon.setImage(headIcon.getImage().getScaledInstance(45, 45,Image.SCALE_DEFAULT));
		JLabel usernameLabel = new JLabel();
		usernameLabel.setIcon(headIcon);
		
		usernameLabel.setText(message.getAuthor().getUsername());
		usernameLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
		usernameLabel.setForeground(Color.BLACK);
		usernameLabel.setBackground(Color.WHITE);
		usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(usernameLabel, gbc_lblNewLabel);
		
		JTextArea txtrNml = new JTextArea();
		txtrNml.setLineWrap(true);
		txtrNml.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		txtrNml.setPreferredSize(new Dimension(350, 50));
		txtrNml.setText(message.getBody() + "\n" + message.getTimeString());
		txtrNml.setEditable(false);
		GridBagConstraints gbc_txtrNml = new GridBagConstraints();
		gbc_txtrNml.insets = new Insets(0, 0, 5, 0);
		gbc_txtrNml.fill = GridBagConstraints.BOTH;
		gbc_txtrNml.gridx = 0;
		gbc_txtrNml.gridy = 1;
		add(txtrNml, gbc_txtrNml);
		
		
		if(message.getImageUrl().equals("")) 
			return ;

	}
	
	public void addHeader(String url) {
		
		try {
			headUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.remove(usernameLabel);
		headIcon = new ImageIcon(headUrl);
		headIcon.setImage(headIcon.getImage().getScaledInstance(45, 45,Image.SCALE_DEFAULT));
		usernameLabel.setIcon(headIcon);
		this.add(usernameLabel, gbc_lblNewLabel);
		this.validate();
		this.repaint();
		
	}
	
	public void addImage(String url) {
		//if( imageLabel != null) this.remove(imageLabel);
		
		if(url.equals("")) return ;

		imageIcon = Cache.getImageIcon(url);
		
		double setsize = (double)imageIcon.getIconWidth() / imageIcon.getIconHeight();
		imageIcon.setImage(imageIcon.getImage().getScaledInstance((int)(140 * setsize), 140, Image.SCALE_DEFAULT));
		imageLabel = new JLabel(imageIcon);
		imageLabel.setSize(new Dimension(350, 100));
		GridBagConstraints gbc_imageLabel = new GridBagConstraints();
		gbc_imageLabel.gridx = 0;
		gbc_imageLabel.gridy = 2;
		final String url2 = url;
		
		imageLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ImageWindow imageWindow = new ImageWindow(url2);
                imageWindow.frame.setVisible(true);
			}
		});
		
		
		add(imageLabel, gbc_imageLabel);
	}


}
