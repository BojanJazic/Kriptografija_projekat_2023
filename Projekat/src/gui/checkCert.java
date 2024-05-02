package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataAccess.DataAccess;
import modelDB.Certificate;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.awt.event.ActionEvent;

public class checkCert extends JFrame {

	private JPanel contentPane;
	private static JComboBox cbSertifikati;
	public static String izabraniSertifikat = "";
	
	public static final String USER_CERTS = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\certs\\";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					checkCert frame = new checkCert();
					frame.setVisible(true);
					
					DataAccess da = new DataAccess();
					List<Certificate> var = da.getAllCerts();
					
					for(Certificate str : var)
					cbSertifikati.addItem(str.getCertificateName());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public checkCert() {
		setTitle("Provjera sertifikata");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    cbSertifikati = new JComboBox();
		cbSertifikati.setBounds(64, 11, 294, 35);
		contentPane.add(cbSertifikati);
		
		cbSertifikati.addFocusListener(new FocusAdapter() {
	    	public void focusGained(FocusEvent e) {
	    		cbSertifikati.setSelectedIndex(-1);
	    	}
		});
		
		JButton btnSubmit = new JButton("Provjera");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(cbSertifikati.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(new JFrame(), "Potrebno je da prvo odaberete Vaš sertifikat.", "Dialog",
					        JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					
					File file = new File(USER_CERTS + cbSertifikati.getSelectedItem().toString());
					try {
						workWithCertificates.Certs.validateCertificate(file);
						izabraniSertifikat = cbSertifikati.getSelectedItem().toString();
						new login().show();
					}catch (Exception ee) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(new JFrame(), "Vaš sertifikat je povucen.", "Dialog",
						        JOptionPane.ERROR_MESSAGE);
					}
					
					
				}
				
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSubmit.setBounds(156, 69, 119, 35);
		contentPane.add(btnSubmit);
	}
}
