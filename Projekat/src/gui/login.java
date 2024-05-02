package gui;

import java.awt.EventQueue;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

import dataAccess.DataAccess;
import dataAccess.DataAccess.*;
import modelDB.Certificate;
import modelDB.Person;
import workWithKeys.WorkWithKeys;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class login extends JFrame {

	private static JPanel contentPane;
	private static JComboBox cbSertifikati;
	private static JLabel lbUsername;
	private static JLabel lblPassword;
	private static JTextField tbUsername;
	private static JTextField tbPassword;
	private static JButton btnLogin;
	private JButton btnRegister;
	private static Integer brojPokusaja = 0;
	public static boolean pomocnaVarijabla = false;
	
	public static final String USER_CERTS = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\certs\\";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	

	/**
	 * Create the frame.
	 */
	public login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlCerts = new JPanel();
		pnlCerts.setBounds(0, 11, 434, 37);
		contentPane.add(pnlCerts);
		pnlCerts.setLayout(null);
		
		
		JPanel pnlLogin = new JPanel();
		pnlLogin.setBounds(0, 59, 434, 202);
		contentPane.add(pnlLogin);
		pnlLogin.setLayout(null);
		
		lbUsername = new JLabel("username:");
		lbUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lbUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbUsername.setBounds(10, 11, 81, 26);
		pnlLogin.add(lbUsername);
		
		lblPassword = new JLabel("password:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(10, 67, 81, 26);
		pnlLogin.add(lblPassword);
		
		tbUsername = new JTextField();
		tbUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tbUsername.setBounds(101, 16, 323, 20);
		pnlLogin.add(tbUsername);
		tbUsername.setColumns(10);
		
		tbPassword = new JTextField();
		tbPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tbPassword.setColumns(10);
		tbPassword.setBounds(101, 72, 323, 20);
		pnlLogin.add(tbPassword);
		
		btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					List<Person> person = DataAccess.getUsernameAndPassword(checkCert.izabraniSertifikat);
					String korisnickoIme = "";
					String lozinka = "";
					for(Person p : person) {
						korisnickoIme = p.getUsername();
						lozinka = p.getPassword();
					}
					String passHash = WorkWithKeys.hash(tbPassword.getText());
					
					if(++brojPokusaja < 3) {
						if(korisnickoIme.equals(tbUsername.getText()) && lozinka.equals(passHash)) {
							JOptionPane.showMessageDialog(new JFrame(), "Uspjesna prijava.", "Dialog",
							        JOptionPane.INFORMATION_MESSAGE);
						}else {
							System.out.println("NETACNO");
						}
					}else {
						System.out.println(checkCert.izabraniSertifikat);
						workWithCertificates.Certs.revokeCertificate(checkCert.izabraniSertifikat);
						JOptionPane.showMessageDialog(new JFrame(), "Iskoristili ste tri pokusaja.", "Dialog",
						        JOptionPane.ERROR_MESSAGE);
						pomocnaVarijabla = true;
						
					}
					
					//NIJE DOBAR OVAJ MEHANIZAM
					
					if(pomocnaVarijabla) {
						if(korisnickoIme.equals(tbUsername.getText()) && lozinka.equals(passHash)) {
							JOptionPane.showMessageDialog(new JFrame(), "Uspjesna prijava. Vas sertifikat je raktiviran.", "Dialog",
							        JOptionPane.INFORMATION_MESSAGE);
							try {
								workWithCertificates.Certs.reactivateCertificate(checkCert.izabraniSertifikat);
							}catch (Exception ee) {
								// TODO: handle exception
								System.out.println("Greska prilikom reaktivacije sertifikata.");
							}
							
						}else {
							System.out.println("NETACNO");
						}
					}
					
					
				}
			}
		);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogin.setBounds(266, 135, 121, 33);
		pnlLogin.add(btnLogin);
		
		btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new registration().show();
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRegister.setBounds(135, 135, 121, 33);
		pnlLogin.add(btnRegister);
			
	}
	
	
}

