package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
import hkust.cse.calendar.users.User;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/** NOTE FOR LOGIN DIALOG **/
/*
 * Currently for this version, I only hardcode id: user and pw: user as the user admin
 * When you sign up as a new user, you won't be able to access manage users and manage locations.
 */
public class LoginDialog extends JFrame implements ActionListener
{
	private JTextField userName;
	private JPasswordField password;
	private JButton button;
	private JButton closeButton;
	private JButton signupButton;

	public JRadioButton adminButton;
	public JRadioButton userButton;
	private ButtonGroup userbg;


	private ApptStorageControllerImpl controller;
	private boolean textFieldEmpty;

	public LoginDialog()		// Create a dialog to log in
	{

		adminButton = new JRadioButton("Admin");
		userButton = new JRadioButton("Regular User");

		userbg = new ButtonGroup();

		adminButton.setActionCommand("Admin");
		userButton.setActionCommand("Regular User");

		userbg.add(adminButton);
		userbg.add(userButton);

		JPanel userTypePanel = new JPanel();
		userTypePanel.add(new JLabel("User Type:"));
		userTypePanel.add(adminButton);
		userTypePanel.add(userButton);
		userButton.setSelected(true);
		adminButton.addActionListener(this);
		userButton.addActionListener(this);
		

		setTitle("Log in");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		controller = new ApptStorageControllerImpl(new ApptStorageNullImpl());
		//load userList from xml
		controller.LoadUserFromXml();
		if(controller.searchUser("user") == null){
			User user = new User("user", "user");
			user.setAdmin(true);
			controller.addUser(user);
		}

		Container contentPane;
		contentPane = getContentPane();

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

		JPanel messPanel = new JPanel();
		messPanel.add(new JLabel("Please input your user name and password to log in."));
		top.add(messPanel);

		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("User Name:"));
		userName = new JTextField(15);
		namePanel.add(userName);
		top.add(namePanel);

		JPanel pwPanel = new JPanel();
		pwPanel.add(new JLabel("Password:  "));
		password = new JPasswordField(15);
		pwPanel.add(password);
		top.add(pwPanel);

		top.add(userTypePanel);

		JPanel signupPanel = new JPanel();
		signupPanel.add(new JLabel("If you don't have an account, please:"));
		signupButton = new JButton("Sign up now");
		signupButton.addActionListener(this);
		signupPanel.add(signupButton);
		top.add(signupPanel);

		contentPane.add("North", top);

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		button = new JButton("Log in");
		button.addActionListener(this);
		butPanel.add(button);

		closeButton = new JButton("Close program");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);
		
		contentPane.add("South", butPanel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);	

	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button)
		{
			// Trim to remove whitespaces
			String username = userName.getText().trim();
			String pw = new String(password.getPassword()).trim();

			areFieldsEmpty(username, pw);
			if (textFieldEmpty) {
				JOptionPane.showMessageDialog(this, "Empty Username and/or Password", "Input Error", JOptionPane.WARNING_MESSAGE);
			} else if (controller.searchUser(username) != null) {
				User user = controller.searchUser(username);
				if (user.getPassword().equals(pw)) {
					controller.setCurrentUser(user);
					// TODO Check if user is to be removed. 
					String currUsername = user.getUsername();
					if (user.isTobeRemoved()) {
						JOptionPane.showMessageDialog(this, "You have been removed", "User Removed", JOptionPane.INFORMATION_MESSAGE);
						controller.removeUser(currUsername);
						return;
					}	

					CalGrid grid = new CalGrid(controller);
					setVisible(false);

				} else {
					JOptionPane.showMessageDialog(this, "Invalid Password", "Input Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Invalid Username", "Input Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getSource() == signupButton)
		{
			// Trim to remove whitespaces
			String username = userName.getText().trim();
			String pw = new String(password.getPassword()).trim();

			areFieldsEmpty(username, pw);
			if (textFieldEmpty) {
				JOptionPane.showMessageDialog(this, "Empty Username and/or Password", "Input Error", JOptionPane.WARNING_MESSAGE);
			} else if (controller.searchUser(username) != null) {
				JOptionPane.showMessageDialog(this, "Username already exists", "Username not available", JOptionPane.WARNING_MESSAGE);
			} else {
				User user = new User(username,pw);
				
				if(adminButton.isSelected()){
					user.setAdmin(true);
				}
				else
				{
					user.setAdmin(false);
				}
				
				controller.addUser(user);
				
				JOptionPane.showMessageDialog(this, "Sign Up successful", "Registered!", JOptionPane.INFORMATION_MESSAGE);
			}
			
			
			
			
		}
		else if(e.getSource() == closeButton)
		{
			int n = JOptionPane.showConfirmDialog(null, "Exit Program ?",
					"Confirm", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				controller.SaveUserToXml();
				System.exit(0);			
			}
		}
	
	}

	// This method checks whether a string is a valid user name or password, as they can contains only letters and numbers
	public static boolean ValidString(String s)
	{
		char[] sChar = s.toCharArray();
		for(int i = 0; i < sChar.length; i++)
		{
			int sInt = (int)sChar[i];
			if(sInt < 48 || sInt > 122)
				return false;
			if(sInt > 57 && sInt < 65)
				return false;
			if(sInt > 90 && sInt < 97)
				return false;
		}
		return true;
	}

	public void areFieldsEmpty(String username, String password) {
		if (username.isEmpty() || password.isEmpty()) 
			textFieldEmpty = true;
		else 
			textFieldEmpty = false;

	}
}
