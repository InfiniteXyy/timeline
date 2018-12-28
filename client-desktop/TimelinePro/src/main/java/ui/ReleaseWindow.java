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

import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

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
				
				try {
					image = new ImageIcon(file.toURL());
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
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
			}
		});
		
		releaseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				DateTime dt = new DateTime();
				String createdAt = dt.toString();
				Message message = new Message();
				
				message.setCreatedAt(createdAt);
				message.setBody(textArea.getText());
				message.setUpdatedAt("");
				try {
					if(Service.createMessage(MainControl.user, message)) {
						frame.dispose();
					} else {
						frame.dispose();
						JOptionPane.showMessageDialog(null, "发布失败", "", JOptionPane.ERROR_MESSAGE);
					}
				} catch(NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "未登录", "", JOptionPane.ERROR_MESSAGE);
				}
				/*
				UserIndex.window.updateMessage();
				UserIndex.window.loadHead.start();
				UserIndex.window.loadImage.start();
				*/
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
