package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bouncycastle.asn1.x509.TBSCertificate;

import dataAccess.DataAccess;
import modelDB.Certificate;
import workWithCertificates.Certs;
import workWithKeys.WorkWithKeys;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;


public class registration extends JFrame {

	private JPanel contentPane;
	private JTextField tbIme;
	private JTextField tbEmail;
	private JTextField tbDrzava;
	private JTextField tbZemlja;
	private JTextField tbOrgJed;
	private JTextField tbOrganizacija;
	private JLabel lbOrganizacija;
	private JButton tbRegistracija;
	private JLabel lbLokacija;
	private JTextField tbLokacija;
	private JLabel lbJMBG;
	private JTextField tbJMBG;
	private JTextField tbKorisnickoIme;
	private JTextField tbLozinka;
	
	private static byte[] hashOdLozinke = null;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					registration frame = new registration();
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
	public registration() {
		setTitle("REGISTRACIJA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 437, 464);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ime:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(25, 49, 100, 27);
		contentPane.add(lblNewLabel);
		
		tbIme = new JTextField();
		tbIme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbIme.setBounds(145, 49, 225, 27);
		contentPane.add(tbIme);
		tbIme.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(25, 87, 100, 27);
		contentPane.add(lblEmail);
		
		tbEmail = new JTextField();
		tbEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbEmail.setColumns(10);
		tbEmail.setBounds(145, 87, 225, 27);
		contentPane.add(tbEmail);
		
		JLabel lbDrzava = new JLabel("Država:");
		lbDrzava.setHorizontalAlignment(SwingConstants.CENTER);
		lbDrzava.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbDrzava.setBounds(25, 125, 100, 27);
		contentPane.add(lbDrzava);
		
		tbDrzava = new JTextField();
		tbDrzava.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbDrzava.setColumns(10);
		tbDrzava.setBounds(145, 125, 225, 27);
		contentPane.add(tbDrzava);
		
		JLabel lbZemlja = new JLabel("Zemlja:");
		lbZemlja.setHorizontalAlignment(SwingConstants.CENTER);
		lbZemlja.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbZemlja.setBounds(25, 163, 100, 27);
		contentPane.add(lbZemlja);
		
		tbZemlja = new JTextField();
		tbZemlja.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbZemlja.setColumns(10);
		tbZemlja.setBounds(145, 163, 225, 27);
		contentPane.add(tbZemlja);
		
		JLabel lbOrgJed = new JLabel("Org. jed:");
		lbOrgJed.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrgJed.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbOrgJed.setBounds(25, 234, 100, 27);
		contentPane.add(lbOrgJed);
		
		tbOrgJed = new JTextField();
		tbOrgJed.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbOrgJed.setColumns(10);
		tbOrgJed.setBounds(145, 234, 225, 27);
		contentPane.add(tbOrgJed);
		
		tbOrganizacija = new JTextField();
		tbOrganizacija.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbOrganizacija.setColumns(10);
		tbOrganizacija.setBounds(145, 272, 225, 27);
		contentPane.add(tbOrganizacija);
		
		lbOrganizacija = new JLabel("Organizacija:");
		lbOrganizacija.setHorizontalAlignment(SwingConstants.CENTER);
		lbOrganizacija.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbOrganizacija.setBounds(25, 272, 100, 27);
		contentPane.add(lbOrganizacija);
		
		tbRegistracija = new JButton("Registruj se");
		tbRegistracija.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Certs crt = new Certs();
				String hashLozinke = WorkWithKeys.hash(tbLozinka.getText());
				
				//System.out.println(new String(hashLozinke, StandardCharsets.UTF_8));
				DataAccess.insertPerson(tbJMBG.getText(), tbIme.getText(), tbKorisnickoIme.getText(), hashLozinke);
				try {
					crt.certificateRequest(tbJMBG.getText(), tbIme.getText(), tbEmail.getText(), tbDrzava.getText(), tbZemlja.getText(), tbLokacija.getText(), tbOrgJed.getText(), tbOrganizacija.getText());
				}catch (Exception ex) {
					// TODO: handle exception
					System.out.println(ex.getMessage());
				}
				new login().show();
				dispose();
			}
		});
		tbRegistracija.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbRegistracija.setBounds(157, 382, 141, 32);
		contentPane.add(tbRegistracija);
		
		lbLokacija = new JLabel("Lokacija:");
		lbLokacija.setHorizontalAlignment(SwingConstants.CENTER);
		lbLokacija.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbLokacija.setBounds(25, 201, 100, 27);
		contentPane.add(lbLokacija);
		
		tbLokacija = new JTextField();
		tbLokacija.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbLokacija.setColumns(10);
		tbLokacija.setBounds(145, 201, 225, 27);
		contentPane.add(tbLokacija);
		
		lbJMBG = new JLabel("JMBG:");
		lbJMBG.setHorizontalAlignment(SwingConstants.CENTER);
		lbJMBG.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbJMBG.setBounds(25, 11, 100, 27);
		contentPane.add(lbJMBG);
		
		tbJMBG = new JTextField();
		tbJMBG.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbJMBG.setColumns(10);
		tbJMBG.setBounds(145, 11, 225, 27);
		contentPane.add(tbJMBG);
		
		JLabel lbKorisnickoIme = new JLabel("Korisnicko ime:");
		lbKorisnickoIme.setHorizontalAlignment(SwingConstants.CENTER);
		lbKorisnickoIme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbKorisnickoIme.setBounds(25, 308, 100, 27);
		contentPane.add(lbKorisnickoIme);
		
		tbKorisnickoIme = new JTextField();
		tbKorisnickoIme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbKorisnickoIme.setColumns(10);
		tbKorisnickoIme.setBounds(145, 308, 225, 27);
		contentPane.add(tbKorisnickoIme);
		
		JLabel lbLozinka = new JLabel("Lozinka:");
		lbLozinka.setHorizontalAlignment(SwingConstants.CENTER);
		lbLozinka.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbLozinka.setBounds(25, 344, 100, 27);
		contentPane.add(lbLozinka);
		
		tbLozinka = new JTextField();
		tbLozinka.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tbLozinka.setColumns(10);
		tbLozinka.setBounds(145, 344, 225, 27);
		contentPane.add(tbLozinka);
	}
}
