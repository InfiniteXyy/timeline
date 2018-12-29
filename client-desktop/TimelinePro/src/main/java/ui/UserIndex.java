package ui;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.ScrollPaneConstants;
import org.json.JSONException;

import control.MainControl;
import entity.Message;
import service.Service;


public class UserIndex {

	public static UserIndex window = null;
	public JFrame frame = null;
	public JPanel panel = null;
	public JPanel userPanel = null;
	public JScrollPane scrollPane = null;
	public GridBagConstraints gbc_panel = null;
	public Vector<MessagePanel> messageList = new Vector<MessagePanel>();
	public List<Message> messages = new ArrayList<Message>();
	private List<MessagePanel> messagePanelList = new ArrayList();
	public Thread loadHead = null;
	public Thread loadImage = null;
	public JButton releaseButton = null;
	public JButton loginButton = null;
	public JButton updateButton = null;
	public JButton logoutButton = null;
	public JLabel headLebal = new JLabel();

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		window = new UserIndex();
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public UserIndex() {
		
		 try { org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF(); }
		 catch(Exception e) {}
		
		initialize();
	}

	public void updateMessage() {

		panel.removeAll();
	
		 

		try {
			messages = Service.getAllMessages();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		messagePanelList.clear();
		for (int i = 0; i < messages.size(); i++) {
			MessagePanel messagePanel = new MessagePanel(messages.get(i));
			messagePanelList.add(messagePanel);
			panel.add(messagePanel);
	
		}
		panel.revalidate();
		scrollPane.revalidate();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < messages.size(); i++) {
					messagePanelList.get(i).addImage(messages.get(i).getImageUrl());
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < messages.size(); i++) {
					messagePanelList.get(i).addHeader(messages.get(i).getAuthor().getImage(), messages.get(i).getAuthor().getUsername());
				}
			}
		}).start();

	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setBounds(700, 200, 450, 770);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 350 };
		gridBagLayout.rowHeights = new int[] { 40, 600, 30 };
		gridBagLayout.columnWeights = new double[] { 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		frame.getContentPane().setLayout(gridBagLayout);

		userPanel = new JPanel();
		userPanel.setPreferredSize(new Dimension(350, 100));
		userPanel.setBackground(SystemColor.textHighlight);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		frame.getContentPane().add(userPanel, gbc_panel_1);
		userPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		releaseButton = new JButton("\u53D1\u5E03");
		releaseButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		releaseButton.setBackground(Color.WHITE);
		releaseButton.setPreferredSize(new Dimension(75, 35));
		userPanel.add(releaseButton);

		updateButton = new JButton("\u52A0\u8F7D\u6700\u65B0\u6D88\u606F");
		updateButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		updateButton.setBackground(Color.WHITE);
		updateButton.setPreferredSize(new Dimension(125, 35));
		userPanel.add(updateButton);
		
		loginButton = new JButton("登录");
		loginButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		loginButton.setPreferredSize(new Dimension(75, 35));
		userPanel.add(loginButton);
		
		logoutButton = new JButton("注销");
		logoutButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		logoutButton.setPreferredSize(new Dimension(75, 35));

		panel = new JPanel();
		panel.setBackground(Color.GRAY);

		scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setPreferredSize(new Dimension(350, 400));
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);

		gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		frame.getContentPane().add(scrollPane, gbc_panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton moreMessageButton = new JButton("更多消息");
		GridBagConstraints gbc_moreMessageButton = new GridBagConstraints();
		gbc_moreMessageButton.fill = GridBagConstraints.BOTH;
		gbc_moreMessageButton.gridx = 0;
		gbc_moreMessageButton.gridy = 2;
		frame.getContentPane().add(moreMessageButton, gbc_moreMessageButton);

		updateMessage();
		
		frame.validate();
		frame.repaint();

		releaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReleaseWindow releaseWindow = new ReleaseWindow();
				releaseWindow.frame.setVisible(true);
			}
		});

		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateMessage();
			}
		});

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.frmTimeline.setVisible(true);
			}
		});
		
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainControl.user = null;
				userPanel.remove(headLebal);
				userPanel.remove(logoutButton);
				userPanel.add(loginButton);
				userPanel.validate();
				userPanel.repaint();
			}
		});
		
		moreMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Message> oldMessages = null;
				final int oldLength = messages.size();
				try {
					oldMessages = Service.getMessagesFrom(5, messages.get(oldLength - 1).getCreatedAt());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				messages.addAll(oldMessages);
				for(int i = oldLength; i < messages.size(); i++) {
					MessagePanel messagePanel = new MessagePanel(messages.get(i));
					panel.add(messagePanel);
					messagePanelList.add(messagePanel);
				}
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						for(int i = oldLength; i < messages.size(); i++) {
							messagePanelList.get(i).addImage(messages.get(i).getImageUrl());
						}
						
					}
				}).start();
			}
		});	

	}

}
