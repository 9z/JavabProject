
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import img.a;

public class LoginGUI {


	private BufferedImage icon;
	private JFrame frame;
	private JTextField id;
	private JPasswordField pw;
	private Control cont = new Control();
	
	
	public ThreadTime tt = new ThreadTime();
	public String userID;
	//public String userName = dao.start(userID).getName();
	
	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 419, 306);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel(){
	         public void paintComponent(Graphics g) {
	             try {
	         		String path = a.class.getResource("").getPath(); // ���� Ŭ������ ���� ��θ� �����´�.
	        		//System.out.println(path); // --> ���� ��ΰ� ��µ�
	         		
	                File fileInSamePackage = new File(path + "bgLogin.png");
	                icon = ImageIO.read(fileInSamePackage);
	                Dimension d = getSize();// ��üȭ��
	                g.drawImage(icon, 0, 0, d.width, d.height, null);
	              //  setOpaque(false);
	               // super.paintComponent(g);
	             } catch (IOException e) {
	                e.printStackTrace();
	             }
	          }
	       };
		panel.setBounds(0, 0, 403, 268);
		frame.getContentPane().add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel lblId = new JLabel("\uC544\uC774\uB514");
		panel.add(lblId);
		lblId.setFont(new Font("�޸ո���T", Font.PLAIN, 15));
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblPw = new JLabel("\uBE44\uBC00\uBC88\uD638");
		panel.add(lblPw);
		lblPw.setFont(new Font("�޸ո���T", Font.PLAIN, 15));
		lblPw.setHorizontalAlignment(SwingConstants.CENTER);
		
		id = new JTextField();
		sl_panel.putConstraint(SpringLayout.WEST, id, 118, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, id, -83, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, lblId, 13, SpringLayout.NORTH, id);
		sl_panel.putConstraint(SpringLayout.EAST, lblId, -27, SpringLayout.WEST, id);
		sl_panel.putConstraint(SpringLayout.NORTH, id, 99, SpringLayout.NORTH, panel);
		panel.add(id);
		id.setFont(new Font("�޸ո���T", Font.PLAIN, 20));
		id.setColumns(10);
		
		pw = new JPasswordField();
		sl_panel.putConstraint(SpringLayout.NORTH, pw, 141, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, pw, -86, SpringLayout.SOUTH, panel);
		pw.setFont(new Font("����", Font.PLAIN, 20));
		sl_panel.putConstraint(SpringLayout.SOUTH, id, -1, SpringLayout.NORTH, pw);
		sl_panel.putConstraint(SpringLayout.WEST, pw, 118, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, pw, -83, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, lblPw, 11, SpringLayout.NORTH, pw);
		sl_panel.putConstraint(SpringLayout.EAST, lblPw, -28, SpringLayout.WEST, pw);
		panel.add(pw);
		
		JLabel lblTitle = new JLabel("\uC790\uBC25(JABAB)\uBA39\uC790!");
		sl_panel.putConstraint(SpringLayout.SOUTH, lblTitle, -29, SpringLayout.NORTH, id);
		sl_panel.putConstraint(SpringLayout.EAST, lblTitle, -67, SpringLayout.EAST, panel);
		panel.add(lblTitle);
		lblTitle.setFont(new Font("�޸ո���T", Font.PLAIN, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnLogin = new JButton("\uB85C\uADF8\uC778");
		sl_panel.putConstraint(SpringLayout.NORTH, btnLogin, 6, SpringLayout.SOUTH, pw);
		sl_panel.putConstraint(SpringLayout.WEST, btnLogin, 222, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnLogin, -44, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnLogin, -83, SpringLayout.EAST, panel);
		panel.add(btnLogin);
		btnLogin.setBackground(new Color(255, 153, 0));
		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {// �α��� ��ư �׼�
				/*
				 * ȸ�����Խ� StringŸ������ ���̵� inputID 
				 * �н����带 userPwChange�� ������.
				 */
				String userID = id.getText();
				char[] userPW = pw.getPassword();
				String userPwTemp = "";
				userPwTemp = new String(userPW, 0, userPW.length);
				
				DAO dao = new DAO();
				if (dao.checkLogin(userID, userPwTemp)) {

					JOptionPane.showMessageDialog(frame, "�α��� ����!");

					// �н����带 StringŸ������ userPWchange�� ������.
					String loginId = userID;
					String loginPw = userPwTemp;
					
					cont.setLs(dao.start(userID));
					
					frame.dispose();
					MainGUI main = new MainGUI();
					main.main(null);
				} else {
					
					JOptionPane.showMessageDialog(frame, "�α��� ����!");
				}
				
				

			}

		});
		btnLogin.setFont(new Font("�޸ո���T", Font.PLAIN, 12));
		
		JButton btnJoin = new JButton("\uD68C\uC6D0\uAC00\uC785");
		sl_panel.putConstraint(SpringLayout.NORTH, btnJoin, 6, SpringLayout.SOUTH, pw);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnJoin, -44, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnJoin, -187, SpringLayout.EAST, panel);
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JoinGUI join = new JoinGUI();
				join.main(null);
				frame.dispose();
			}
		});
		sl_panel.putConstraint(SpringLayout.WEST, btnJoin, 118, SpringLayout.WEST, panel);
		panel.add(btnJoin);
		btnJoin.setBackground(new Color(51, 153, 204));
		btnJoin.setFont(new Font("�޸ո���T", Font.PLAIN, 12));
	}

}
	