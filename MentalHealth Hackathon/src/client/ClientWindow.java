package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
//import java.awt.TextField;

import javax.swing.JPanel;
//import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientWindow {

	private JFrame Frame;
	private JTextField messageField;
	private static JTextArea textArea = new JTextArea();

	private Client client;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					ClientWindow window = new ClientWindow();
					window.Frame.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
		
		String name = JOptionPane.showInputDialog("Enter Name");
		//same port as in server
		client = new Client(name,"Localhost",52864);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Frame = new JFrame();
		Frame.setTitle("Chatter Box");
		Frame.setBounds(100, 100, 550, 290);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		Frame.getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		Frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		
		messageField = new JTextField();
		panel.add(messageField);
		messageField.setColumns(30);
		
		JButton btnSend = new JButton("Send");
		
		btnSend.addActionListener(e -> {
			if (!messageField.getText().equals("")) {
				client.send(messageField.getText());
				messageField.setText("");
				
			}
			
		});
		
		panel.add(btnSend);
		
		
		Frame.setLocationRelativeTo(null);
		
	}
	
	public static void printToConsole(String message) {
		textArea.setText(textArea.getText()+message+"\n");
	}

	
}
