package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

import org.json.JSONException;

import control.MainControl;
import entity.Message;
import entity.User;
import service.Service;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

public class Register {

	public JFrame frame;
	private JTextField email;
	private JTextField username;
	private JPasswordField password;
	private JPasswordField password2;
	public URL headUrl = null;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register window = new Register();
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
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("TIMELINE");
		frame.getContentPane().setBackground(Color.WHITE);
		//frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setForeground(Color.WHITE);
		frame.getContentPane().add(panel_5);
		
		JLabel lblNewLabel = new JLabel("REGISTER");
		panel_5.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("΢���ź� Light", Font.BOLD, 24));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel);
		
		JLabel emailLabel = new JLabel("\u90AE\u7BB1\uFF1A");
		emailLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 18));
		panel.add(emailLabel);
		
		email = new JTextField();
		email.setFont(new Font("微软雅黑 Light", Font.BOLD, 12));
		email.setPreferredSize(new Dimension (15, 25)); 
		panel.add(email);
		email.setColumns(15);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_1);
		
		JLabel usernameLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		usernameLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 18));
		panel_1.add(usernameLabel);
		
		username = new JTextField();
		username.setFont(new Font("微软雅黑 Light", Font.BOLD, 12));
		username.setPreferredSize(new Dimension (15, 25)); 
		username.setColumns(15);
		panel_1.add(username);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_2);
		
		JLabel passwordLabel = new JLabel("\u5BC6\u7801\uFF1A");
		passwordLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 18));
		panel_2.add(passwordLabel);
		
		password = new JPasswordField();
		password.setFont(new Font("微软雅黑 Light", Font.BOLD, 12));
		password.setPreferredSize(new Dimension (15, 25)); 
		password.setColumns(15);
		panel_2.add(password);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_3);
		
		JLabel secondPasswordLabel = new JLabel("\u786E\u8BA4\uFF1A");
		secondPasswordLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 18));
		panel_3.add(secondPasswordLabel);
		
		password2 = new JPasswordField();
		password2.setFont(new Font("微软雅黑 Light", Font.BOLD, 12));
		password2.setPreferredSize(new Dimension (15, 25)); 
		password2.setColumns(15);
		panel_3.add(password2);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_4);
		
		JButton registerButton = new JButton("\u6CE8\u518C");
		registerButton.setFont(new Font("微软雅黑 Light", Font.BOLD, 12));
		registerButton.setPreferredSize(new Dimension (75, 35)); ;
		registerButton.setBackground(Color.WHITE);
		panel_4.add(registerButton);
		
		JButton loginButton = new JButton("返回登录");
		loginButton.setPreferredSize(new Dimension(100, 35));
		loginButton.setFont(new Font("微软雅黑 Light", Font.BOLD, 12));
		loginButton.setBackground(Color.WHITE);
		panel_4.add(loginButton);
		
		registerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				if(!isEmail(email.getText())) {
					JOptionPane.showMessageDialog(null, "邮箱格式错误", "", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!password.getText().equals(password2.getText())) {
					JOptionPane.showMessageDialog(null, "两次密码不一致", "", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				user.setEmail(email.getText());
				user.setUsername(username.getText());
				user.setPassword(password.getText());
				if(Service.register(user)) {
					User user2 = Service.login(user);
					MainControl.user = user2;
					UserIndex.window.userPanel.remove(UserIndex.window.loginButton);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "该邮箱已被注册", "", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.frmTimeline.setBounds(frame.getBounds());
				login.frmTimeline.setVisible(true);
				frame.dispose();
			}
		});
	
	}
	
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|"
				+ "(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

}
