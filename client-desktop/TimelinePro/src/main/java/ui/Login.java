package ui;


import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import control.MainControl;
import entity.User;
import service.Service;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;

public class Login {

	public JFrame frmTimeline;
	public JPanel panel_3 = null;
	public JPanel panel = null;
	public JLabel lblNewLabel = null;
	public JLabel emailLabel = null;
	public JPanel panel_1 = null;
	public JLabel passwordLabel = null;
	public JPanel panel_2 = null;
	public JButton loginButton = null;
	public JButton registerButton = null;
	public URL headUrl = null;
	private JTextField email;
	private JPasswordField password;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmTimeline.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Login window = new Login();
		window.frmTimeline.setVisible(true);
		
	}
	 */
	/**	
	 * Create the application.
	 */
	public Login() {
		
		try
	    {
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	    }
	    catch(Exception e)
	    {
	        //TODO exception
	    }
	    
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTimeline = new JFrame();
		frmTimeline.setTitle("TIMELINE");
		frmTimeline.getContentPane().setBackground(Color.WHITE);
		frmTimeline.setBounds(700, 300, 450, 400);
		frmTimeline.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTimeline.setDefaultCloseOperation(frmTimeline.DISPOSE_ON_CLOSE);
		frmTimeline.getContentPane().setLayout(new GridLayout(4, 1, 0, 0));
		
		panel_3 = new JPanel();
		panel_3.setForeground(SystemColor.menu);
		panel_3.setBackground(Color.WHITE);
		frmTimeline.getContentPane().add(panel_3);
		
		lblNewLabel = new JLabel("LOGIN");
		panel_3.add(lblNewLabel);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBackground(SystemColor.menu);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("΢���ź� Light", Font.BOLD, 24));
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frmTimeline.getContentPane().add(panel);
		
		emailLabel = new JLabel("邮箱：");
		emailLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 18));
		panel.add(emailLabel);
		
		email = new JTextField();
		email.setPreferredSize(new Dimension (15, 30)); 
		panel.add(email);
		email.setColumns(15);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		frmTimeline.getContentPane().add(panel_1);
		
		passwordLabel = new JLabel("密码：");
		passwordLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 18));
		panel_1.add(passwordLabel);
		
		password = new JPasswordField();
		password.setPreferredSize(new Dimension (15, 30)); 
		panel_1.add(password);
		password.setColumns(15);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		frmTimeline.getContentPane().add(panel_2);
		
		loginButton = new JButton("登录");
		loginButton.setBackground(Color.WHITE);
		loginButton.setFont(new Font("微软雅黑 Light", Font.BOLD, 14));
		loginButton.setPreferredSize(new Dimension (75, 35)); 
		panel_2.add(loginButton);
		
		registerButton = new JButton("注册");
		registerButton.setBackground(Color.WHITE);
		registerButton.setFont(new Font("微软雅黑 Light", Font.BOLD, 14));
		registerButton.setPreferredSize(new Dimension (75, 35)); 
		panel_2.add(registerButton);
		
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				user.setEmail(email.getText());	
				user.setPassword(password.getText());
				User user2 = Service.login(user);
				if(user2 == null) {
					JOptionPane.showMessageDialog(null, "账号或密码错误", "", JOptionPane.ERROR_MESSAGE);
				} else {
					//System.out.println(user2.getToken());
					MainControl.user = user2;
					UserIndex.window.userPanel.remove(UserIndex.window.loginButton);
					try {
						headUrl = new URL(user2.getImage());
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon headIcon = new ImageIcon(headUrl);
					headIcon.setImage(headIcon.getImage().getScaledInstance(45, 45,Image.SCALE_DEFAULT));
					UserIndex.window.headLebal = new JLabel(headIcon);
					UserIndex.window.userPanel.add(UserIndex.window.headLebal);
					
					UserIndex.window.headLebal.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							MainControl.user = null;
							UserIndex.window.userPanel.remove(UserIndex.window.headLebal);
							UserIndex.window.userPanel.add(loginButton);
							UserIndex.window.userPanel.validate();
							UserIndex.window.userPanel.repaint();
						}
					});
					
					frmTimeline.dispose();
				}
			}
		});
		
		registerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Register register = new Register();
				register.frame.setBounds(frmTimeline.getBounds());
				register.frame.setVisible(true);
				frmTimeline.dispose();
			}
		});
	}

}
