package workWithCertificates;

import dbConnection.*;
import modelDB.Certificate;
import modelDB.Data;
import modelDB.Person;
import workWithFiles.WorkWithFiles;
import workWithKeys.WorkWithKeys;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CRLReason;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

//import javax.crypto.SecretKey;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import dataAccess.*;

public class Main {

	private static final String aes = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\sessionKeys\\";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// Security.addProvider(new BouncyCastleProvider());
		   Certs tmp = new Certs();
		 //DataAccess.insertCertRequest("1203999162528", "Marko.csr");
		 //X509Certificate pom = tmp.getCertificate(file); //radi
		// System.out.println(pom);
		// KeyPair kp = tmp.generateKeyPair(); //radi
		// System.out.println(kp.getPrivate());
		 
		/*
		  try {
			  
			  String hash = WorkWithKeys.hash("petar");
			  dataAccess.DataAccess.insertPerson("1203999162528", "Petar", "petar", hash);
			  
			  tmp.certificateRequest("1203999162528", "Petar", "petar@mail.com", "BA", "RS", "BL", "PMF", "PMF"); 
			  
			  }catch (Exception e) {
				  // TODO: handle 
				  System.out.println(e.getMessage());
				 // e.printStackTrace();
		  }
		*/
		 /*
		 tmp.certificateSigning("2003999162528", "Drago");
	
		// PrivateKey pk = tmp.getCAPrivateKey();
		 File file1 = new File("C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\certs\\Bojana.crt");
		 File caFile = new File("C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\rootca.pem");
		 /*try {
			 tmp.revokeCertificate("Drago.crt");
		 }catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
		 
		 try {
			// tmp.reactivateCertificate("Bojana.crt");
			 //tmp.revokeCertificate("Bojana.crt");
			 tmp.validateCertificate(file1);
		 }catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			 System.out.println(e.getMessage());
		} 
		 
		 
		 // System.out.println(tmp.getCertificate(file1).getSerialNumber());
	//	System.out.println(tmp.getCertificate(file1));
		  //tmp.validateCertificate(file1); 
		// tmp.revokeCert(caFile);
		/*
		 * byte[] key = Files.readAllBytes(Paths.get(
		 * "C:\\Users\\Bojan\\ProjekatKRZ\\Okruzenje\\requests\\Korisnik2kljuc.key"));
		 * KeyFactory keyFactory = KeyFactory.getInstance("RSA"); PKCS8EncodedKeySpec
		 * keySpec = new PKCS8EncodedKeySpec(key); PrivateKey finalKey =
		 * keyFactory.generatePrivate(keySpec); System.out.println(finalKey);
		 */
		/*
		 * String key =
		 * "C:\\Users\\Bojan\\ProjekatKRZ\\Okruzenje\\private\\private4096.key"; try {
		 * String content = new String(Files.readAllBytes(Paths.get(key)));
		 * System.out.println(content); }catch (IOException e) { // TODO: handle
		 * exception e.printStackTrace(); }
		 */

		// PrivateKey pk = tmp.getCAPrivateKey();

		DBConnection conn = new DBConnection();

		// System.out.println(conn.getConnection());
		/*
		 * conn.getConnection(); System.out.println("OTOVRENA");
		 * if(conn.closeConnection()) System.out.println("Zatvorena");
		 */
		/*
		 * List<String> cs = DataAccess.getCerts(); if(cs.isEmpty())
		 * System.out.println("Lista je prazna"); else { for (String credentials : cs) {
		 * System.out.println(credentials); } }
		 */
		/*
		 * List<String> f = DataAccess.getAllFiles(); if(f.isEmpty())
		 * System.out.println("LISTA JE PRAZNA"); else { for(String s : f)
		 * System.out.println(s); }
		 */

		// WorkWithFiles.showFileDialog("1003999162528");
		/*
		 * String s = DataAccess.checkCerts("1103999162528"); if(s.length() > 0)
		 * System.out.println(s);
		 */

		// tmp.certificateSigning("Korisnik1.csr");

		//File cert = new File("C:\\Users\\Bojan\\ProjekatKRZ\\Okruzenje\\certs\\Janko.crt");
		// tmp.validateCertificate(cert);
		//X509Certificate crt = tmp.getCertificate(cert);
		//System.out.println(crt);
		//System.out.println(CRLReason.CERTIFICATE_HOLD.ordinal());
		/*try {
			tmp.revokeCert(cert);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}*/
		
		/*PublicKey pubKey = crt.getPublicKey();
		byte[] pkb = pubKey.getEncoded();
		String pkt = Base64.getEncoder().encodeToString(pkb);
		System.out.println(pkt);
		PrivateKey pk = tmp.getPrivateKey();
		byte[] privateKeyBytes = pk.getEncoded();
		String privateKeyText = Base64.getEncoder().encodeToString(privateKeyBytes);*/
		//System.out.println(privateKeyText + "\n");
		
		/*RSAPrivateCrtKey rsaPrivateCrtKey = (RSAPrivateCrtKey) pk;
		RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(rsaPrivateCrtKey.getModulus(), rsaPrivateCrtKey.getPublicExponent());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);*/
		//System.out.println(pk.toString());
		//System.out.println("\n" + publicKey.toString());
		
		/*byte[] publicKeyBytes = publicKey.getEncoded();
		String publicKeyText = Base64.getEncoder().encodeToString(publicKeyBytes);
		System.out.println(publicKeyText);*/
		//System.out.println(tmp.getCertificate(cert));
		/*SecretKey sk = WorkWithKeys.generateAESKey("jazzajaza");
		try {
			WorkWithKeys.encryptSymmetricKey(crt, sk);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		*/
		//Path filePath = Paths.get(aes);
		//Files.write(filePath, sk.getEncoded());
		//String str = Base64.getEncoder().encodeToString(sk.getEncoded());
		//System.out.println(str);;
		//tmp.validateCertificate(cert);
		
		//KeyPair kp = wwk.generateKeyPair();
		//wwk.generateAESKey("jazzajazza");
		//System.out.println(sk.getEncoded());
		//wwk.encryptSymmetricKey(userCert, sk);
		//Key key = wwk.genSessionKey();
		//System.out.println(key.getEncoded());
		
		WorkWithKeys wwk = new WorkWithKeys();
		File userCertFile = new File("C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\certs\\Marko.crt");
		
		try{
			//Key key = wwk.generateSessionKey();
			//System.out.println("Main: " + key.getEncoded());
			//byte[] bajt = key.getEncoded();
			//System.out.println("MAIN: " + new String(bajt, StandardCharsets.UTF_8));
			//File userCertFile1 = new File("C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\certs\\Drago.crt");
			//X509Certificate userCert1 = tmp.getCertificate(userCertFile1);
			//tmp.revokeCertificate("Marko.crt");
			//tmp.validateCertificate(userCertFile);
			//tmp.reactivateCertificate("Marko.crt");
			//wwk.encryptSymmetricKey(userCert, key);
			//System.out.println("Main2: " + key.getEncoded());
			//System.out.println(pom);
			//Key symmetric = wwk.decryptSymmetricKey(userCert);
		//	System.out.println(symmetric.getEncoded());
			//wwk.encryption(symmetric);
			//wwk.decryption(symmetric);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		/*
		byte[] hash = wwk.passwordHashing("sigurnost");
		//DataAccess.insertPerson("2303999162528", "Dragan", "Draganic", "065788899", "Patre 5", "dragan", new String(hash, StandardCharsets.UTF_8));
		byte[] hash2 = wwk.passwordHashing("sigurnostt");
		if(Arrays.equals(hash, hash2)) {
			System.out.println("DOBRO");
		}
	*/
		
		//wwk.documentSegmentation();
		
		
	}

}