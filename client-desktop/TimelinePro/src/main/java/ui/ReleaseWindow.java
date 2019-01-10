package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JTextArea;

import org.joda.time.DateTime;
import org.json.JSONException;

import control.MainControl;
import entity.Message;
import entity.Message.Author;
import service.Service;
import util.ImgUtil;

import java.awt.GridBagConstraints;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.JPanel;

public class ReleaseWindow {

	public JFrame frame;
	public JTextArea textArea;
	private JButton imageButton;
	private JButton releaseButton;
	private JButton deleteButton;
	private GridBagLayout gridBagLayout;
	private ImageIcon image = null;
	private JLabel imageLabel = null;
	private File file = null;
	private File fileClone = null;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReleaseWindow window = new ReleaseWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */
	/**
	 * Create the application.
	 */
	public ReleaseWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(800, 300, 300, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[] {280, 80};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 0;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 0;
		frame.getContentPane().add(textArea, gbc_textArea);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		frame.getContentPane().add(panel, gbc_panel);
		
		imageButton = new JButton("添加图片");
		panel.add(imageButton);
		
		releaseButton = new JButton("发布");
		panel.add(releaseButton);
		
		deleteButton = new JButton("删除图片");
		panel.add(deleteButton);
		
		imageButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(imageLabel != null) frame.remove(imageLabel);
				JFileChooser jfc = new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
		        jfc.showDialog(new JLabel(), "选择");  
		        file = jfc.getSelectedFile(); 
		        //未选择文件
				if(file == null) return ;
				
				//判断是否为图片文件
				Image image2;
				try {
					image2 = ImageIO.read(file);
					if(image2 == null) {
						JOptionPane.showMessageDialog(null, "文件格式错误", "", JOptionPane.ERROR_MESSAGE);
						return ;
					}		
				} catch (IOException e2) {
					//JOptionPane.showMessageDialog(null, "?", "", JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, "?", "", JOptionPane.ERROR_MESSAGE);
					return ;
				}
				
				
		        //fileClone = new File(System.getProperty("user.dir") + "\\1.jpg");
		        
		        /*
		        try {
					Files.copy(file.toPath(), fileClone.toPath());
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
		        */
				try {
					image = new ImageIcon(file.toURL());
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				
				double setsize = (double)image.getIconWidth() / image.getIconHeight();
				image.setImage(image.getImage().getScaledInstance((int)(150 * setsize), 150, Image.SCALE_DEFAULT));
				imageLabel = new JLabel(image);
				frame.setBounds(800, 300, 300, 530);
				gridBagLayout.rowHeights = new int[] {280, 80, 150};
				gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
				gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0};
				GridBagConstraints gbc_image = new GridBagConstraints();
				gbc_image.fill = GridBagConstraints.BOTH;
				gbc_image.gridx = 0;
				gbc_image.gridy = 2;
				frame.getContentPane().add(imageLabel, gbc_image);
				frame.validate();
				frame.repaint();
				
				/*
				if(fileClone.length() >= 1048576) {
					float rate = 1000000 / fileClone.length();
				while(file.length() >= 1048576) {
					try {
						ImgUtil.compressPictureByQality(fileClone, rate);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				*/
			}
		});
		
		releaseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(MainControl.user == null) {
					JOptionPane.showMessageDialog(null, "未登录", "", JOptionPane.ERROR_MESSAGE);
					return ;
				}
					
				DateTime dt = new DateTime();
				String createdAt = dt.toString();
				Message message = new Message();
				
				message.setCreatedAt(createdAt);
				message.setBody(textArea.getText());
				message.setUpdatedAt("");
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						DateTime dt = new DateTime();
						String createdAt = dt.toString();
						Message message = new Message();
						
						String imageUrl = "";
						
						if(file != null) {
							try {
								imageUrl = Service.uploadImage(new File(file.toString()));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						
						message.setCreatedAt(createdAt);
						message.setBody(textArea.getText());
						message.setUpdatedAt("");
						message.setImageUrl(imageUrl);
						
						if(Service.createMessage(MainControl.user, message)) {
							frame.dispose();
							/*
							try {
								Files.delete(fileClone.toPath());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							*/
						} else {
							frame.dispose();
							JOptionPane.showMessageDialog(null, "发布失败", "", JOptionPane.ERROR_MESSAGE);
						}	
						
					}
				}).start();
			}
		});
		
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(imageLabel != null) frame.remove(imageLabel);
				file = null;
				frame.setBounds(800, 300, 300, 380);
				gridBagLayout.rowHeights = new int[] {280, 80};
				gridBagLayout.rowWeights = new double[]{1.0, 1.0};
				frame.validate();
				frame.repaint();
			}
		});
		
	}
	
}
