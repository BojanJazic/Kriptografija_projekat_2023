package workWithCertificates;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateRevokedException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.security.auth.x500.X500Principal;

import java.security.cert.CRLReason;
import java.security.cert.CertificateException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import org.bouncycastle.asn1.ASN1Primitive;
//import org.bouncycastle.asn1.cmp.CertAnnContent;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLNumber;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CRLConverter;
import org.bouncycastle.cert.jcajce.JcaX509CRLHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v2CRLBuilder;
import org.bouncycastle.jcajce.provider.asymmetric.X509;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.X509CRLObject;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.x509.X509V2CRLGenerator;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import dataAccess.DataAccess;
import modelDB.CertRequests;
import workWithKeys.WorkWithKeys;
public class Certs {

	public static final String USER_CERTS = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\certs\\";
	public static final String KEYSTORE_FILE = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\keystore.p12";
	public static final String CA_CERT = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\rootca.pem";
	public static final String CERT_REQUESTS = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\requests\\";
	public static final String CRL_LIST = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\crl\\crl.crl";
	public static final String CA_KEY = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\private\\private4096.key";
	public static final String KEYS = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\requests\\";
	public static final String CONF_FILE = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\openssl.cnf";
	public static final String SERIAL_NUMBER = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\serial";
	public static final String KEYSTORE_PASS = "sigurnost";

	public static Integer serialNumber = 1;
	public static final String PROVIDER = "BC";
	public static final String KEY_ALG = "RSA";
	public static final String SIGNATURE_ALG = "SHA256WithRSAEncryption";
	
	/**
	 * @param cert
	 * @return
	 * @throws Exception
	 */
	public static X509Certificate getCertificate(File cert) throws Exception {
		FileInputStream fis = new FileInputStream(cert.getAbsolutePath());
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate x509 = (X509Certificate) cf.generateCertificate(fis);

		return x509;
	}
	

	/**
	 * @param
	 * @throws Exception
	 */
	public static void validateCertificate(File cert) throws Exception {

		File fajlCA = new File(CA_CERT);
		X509Certificate caCert = getCertificate(fajlCA);

		X509Certificate userCert = getCertificate(cert);
		File fajlCRL = new File(CRL_LIST);
		  
	    byte[] crlBytes = Files.readAllBytes(Paths.get(CRL_LIST));
	    CertificateFactory cf = CertificateFactory.getInstance("X.509"); 
	    X509CRL crlList = (X509CRL) cf.generateCRL(new ByteArrayInputStream(crlBytes));
		 //System.out.println(crlList.toString());
		PublicKey publicCAKey = caCert.getPublicKey(); // separation the public CA key
			userCert.verify(publicCAKey); // verifying that the certificate is signed by a CA
			userCert.checkValidity(new Date()); // checking if the certificate is still valid
		   // crlList.isRevoked(userCert); //checking if the certificate has been revoked
			isCertRevoked(userCert, crlList);
			
	
	}

	public static void certificateRequest(String ID, String name, String mail, String state, String country,
										  String location, String orgUnit, String org) throws Exception {

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		KeyPair keyPair = null;
		try {
			
			keyPair = WorkWithKeys.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		//saveKeyPairToFile(keyPair, name);
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		ContentSigner signer = new JcaContentSignerBuilder(SIGNATURE_ALG).setProvider(PROVIDER).build(privateKey);
		X500NameBuilder nameBuilder = new X500NameBuilder();
		
		nameBuilder.addRDN(BCStyle.CN, name);
		nameBuilder.addRDN(BCStyle.EmailAddress, mail);
		nameBuilder.addRDN(BCStyle.C, country);
		nameBuilder.addRDN(BCStyle.ST, state);
		nameBuilder.addRDN(BCStyle.L, location);
		nameBuilder.addRDN(BCStyle.O, org);
		nameBuilder.addRDN(BCStyle.OU, orgUnit);
		X500Name tmp = nameBuilder.build();
		PKCS10CertificationRequestBuilder genReq = new JcaPKCS10CertificationRequestBuilder(tmp, publicKey);

		// Build the certificate request
		PKCS10CertificationRequest csr = genReq.build(signer);
		

		
		// Save request to the specific folder
		Path requests = Paths.get(CERT_REQUESTS);
		Path csrFile = requests.resolve(name + ".csr");
		Files.write(csrFile, csr.getEncoded());

		//System.out.println("OVE PODATKE SALJEM: " + ID +" " + name + ".csr");
		DataAccess.insertCertRequest(ID, name + ".csr");
		certificateSigning(ID, name, privateKey);
	}

	private static void saveKeyPairToFile(KeyPair kp, String name) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(kp.getPrivate().getEncoded());
		FileOutputStream fos = new FileOutputStream(KEYS + name + "kljuc.key");
		fos.write(keySpec.getEncoded());
		fos.close();
	}

	/**
	 * @throws Exception
	 */
	public static void certificateSigning(String ID, String ime, PrivateKey userPrivateKey) throws Exception {
		// Set the validity period for the certificate request
		Date start = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.add(Calendar.MONTH, 6);
		Date end = calendar.getTime();
		Integer serialNumber = loadAndIncreaseSerialNumber();

		// Loading the self-signed certificate from file
		File caFile = new File(CA_CERT);
		X509Certificate caCert = getCertificate(caFile);

		// get the request name from DB
		/*List<CertRequests> reqInfo = DataAccess.getRequestName(ID);
		String reqName = "";
		for (CertRequests certRequests : reqInfo) {
			reqName = certRequests.getRequestName();
		}*/
		String reqName = DataAccess.getRequestName(ID);
		
		//System.out.println(reqName + " " + keyName);
		// Loading the user cert request
		File csrFile = new File(CERT_REQUESTS + reqName);
	
		byte[] csrBytes = Files.readAllBytes(csrFile.toPath());
		//System.out.println("BYTES: " + csrBytes);
		PKCS10CertificationRequest request = new PKCS10CertificationRequest(csrBytes);

		// Sign the user cert request with CA cert
		X509CertificateHolder holder = new X509CertificateHolder(caCert.getEncoded());

		X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(holder.getSubject(), new BigInteger(serialNumber.toString()),
				start, end, request.getSubject(), request.getSubjectPublicKeyInfo());

		certBuilder.addExtension(Extension.basicConstraints, false, new BasicConstraints(false));
		certBuilder.addExtension(Extension.keyUsage, true,
				new KeyUsage(KeyUsage.digitalSignature | KeyUsage.dataEncipherment | KeyUsage.keyEncipherment));
		
		PrivateKey privateKey = getKeyFromPKCS12();

		ContentSigner signer = new JcaContentSignerBuilder(SIGNATURE_ALG).setProvider(PROVIDER)
				.build(privateKey);

		// Sign the cert with CA
		X509Certificate userCert = new JcaX509CertificateConverter().getCertificate(certBuilder.build(signer));
		
		// Save the signed cert to file
		
		File userCertFile = new File(USER_CERTS, ime + ".crt");
		try (FileOutputStream fos = new FileOutputStream(userCertFile)) {
			fos.write(userCert.getEncoded());
		} catch (IOException e) {
			// Handle the exception
		}

		DataAccess.insertCertificate(ID, ime + ".crt");
		saveToPKCS12(userCert, userPrivateKey, ime);
	}
	
	public static void saveToPKCS12(X509Certificate userCert, PrivateKey privateKey, String name) throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(null, null);
		
		char[] password = "sigurnost".toCharArray();
		keyStore.setKeyEntry("alias", privateKey, password, new X509Certificate[] {userCert});
		X500Principal tmp = userCert.getSubjectX500Principal();
		String subjectName = tmp.getName();
		
		FileOutputStream fos = new FileOutputStream(USER_CERTS + name + ".p12");
		keyStore.store(fos, password);
		fos.close();
	}
	

	public static void revokeCertificate(String certName) {
		try {
			//X509Certificate caCert = getCertificate(caCertFile);
			File certFile = new File(USER_CERTS + certName);
			X509Certificate certToRevoke = getCertificate(certFile);
			Calendar calendar = Calendar.getInstance();
	        Date now = calendar.getTime();
	        calendar.add(Calendar.YEAR, 1);
	        Date endDate = calendar.getTime();
	        X509v2CRLBuilder builder = new JcaX509v2CRLBuilder(certToRevoke.getIssuerX500Principal(), now);
	        
	        byte[] crlBytes = Files.readAllBytes(Paths.get(CRL_LIST));
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
			X509CRL crl = (X509CRL) certificateFactory.generateCRL(new ByteArrayInputStream(crlBytes));
			
			//System.out.println("CRL:\n" + crl.toString());
		
			//check if the certificate is already revoked
			isCertRevoked(certToRevoke, crl);
				
	        
	         
	        
		     	InputStream inputStream = new FileInputStream(CRL_LIST);
	            CertificateFactory cf = CertificateFactory.getInstance("X.509");
	            X509CRLHolder crlHolder = new X509CRLHolder(inputStream);
	            builder.addCRL(crlHolder);

	        
	     
	        builder.addCRLEntry(certToRevoke.getSerialNumber(), now, CRLReason.PRIVILEGE_WITHDRAWN.ordinal());
	        builder.setNextUpdate(endDate);
	        builder.addExtension(Extension.authorityKeyIdentifier, false, new JcaX509ExtensionUtils()
	                .createAuthorityKeyIdentifier(certToRevoke));
	        
	        builder.addExtension(Extension.cRLNumber, false, new CRLNumber(new BigInteger("1000")));
	        FileOutputStream storec = null;
	        
	            storec = new FileOutputStream(CRL_LIST);

	            X509CRLHolder cRLHolder = builder.build(new JcaContentSignerBuilder(SIGNATURE_ALG)
	                    .build(getKeyFromPKCS12()));

	            storec.write(cRLHolder.getEncoded());
	        
	            if (storec != null) {
	                storec.close();
	            }
	        
		}catch (Exception e) {
			// TODO: handle exception
			//System.out.println("OVDJE JE GRESKA");
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}
		

	}

	public static void reactivateCertificate(String certName) throws Exception {
		
		File certFile = new File(USER_CERTS + certName);
		X509Certificate userCert = getCertificate(certFile);
		//load the crl list
		InputStream is = new FileInputStream(CRL_LIST);
		X509CRL crl = (X509CRL) CertificateFactory.getInstance("X509").generateCRL(is);	
		if(crl.getRevokedCertificates() == null || crl.getRevokedCertificates().isEmpty()) {
			System.out.println("The CRL list is empty.");
			return;
		}
		X509Certificate revokedCert = getCertificate(certFile);
		X509CRLEntry revokedCertEntry = crl.getRevokedCertificate(revokedCert.getSerialNumber());
		
		if(revokedCertEntry != null) {
			//System.out.println("Nije na crl");	
			Set<? extends X509CRLEntry> revokedSet = crl.getRevokedCertificates();
			
			Set<X509CRLEntry> crlEntry = new HashSet<>(revokedSet);
			crlEntry.remove(revokedCertEntry);
			// Create a new X509CRL object with the updated revoked certificates
			X509v2CRLBuilder builder = new X509v2CRLBuilder(new X500Name(crl.getIssuerDN().getName()), new Date());
			
			for (X509CRLEntry entry : crlEntry) {
		        BigInteger serialNumber = entry.getSerialNumber();
		        Date revocationDate = entry.getRevocationDate();
		        int reasonCode = 9;
		        builder.addCRLEntry(serialNumber, revocationDate, reasonCode);
		       
		    }
			 
			 X509CRLHolder updatedCrlHolder = builder.build(new JcaContentSignerBuilder(SIGNATURE_ALG).setProvider(PROVIDER).build(getKeyFromPKCS12()));
			 X509CRL updatedCrl = new JcaX509CRLConverter().setProvider(PROVIDER).getCRL(updatedCrlHolder);
			
			FileOutputStream crlFile = new FileOutputStream(CRL_LIST);
			crlFile.write(updatedCrl.getEncoded());
			crlFile.close();
			
		}
	
	}
	
	/*
	public static X509CRL generateCRL(Set<? extends X509CRLEntry> revokedCertificates,X509Certificate userCert)
            throws IOException, GeneralSecurityException, OperatorCreationException {
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(new Date());
	    cal.add(Calendar.DAY_OF_MONTH, 7);
	    Date date = cal.getTime();
		
        X509v2CRLBuilder crlGen = new JcaX509v2CRLBuilder(
              userCert.getSubjectX500Principal(), new Date());
        crlGen.setNextUpdate(date);
        // add extensions to CRL
        JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
        crlGen.addExtension(Extension.authorityKeyIdentifier, false,
                extUtils.createAuthorityKeyIdentifier(userCert));

        for (X509CRLEntry revokedCertificate : revokedCertificates) {
            crlGen.addCRLEntry(revokedCertificate.getSerialNumber(), revokedCertificate.getRevocationDate(),
                    CRLReason.PRIVILEGE_WITHDRAWN.ordinal());
        }

        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption")
                .setProvider("BC").build(getKeyFromPKCS12());
        JcaX509CRLConverter converter = new JcaX509CRLConverter().setProvider("BC");
        return converter.getCRL(crlGen.build(signer));
    }
	*/
	public static void isCertRevoked(X509Certificate certToCheck, X509CRL crl) throws Exception {
		Set<? extends X509CRLEntry> revokedCerts = crl.getRevokedCertificates();
		//System.out.println(certToCheck.getSerialNumber());
		if (revokedCerts != null && !revokedCerts.isEmpty()) {
		    // Process the revoked certificates
		    for (X509CRLEntry revokedCert : revokedCerts) {
		      //  System.out.println("Revoked certificate serial number: " + revokedCert.getSerialNumber());
		        if(certToCheck.getSerialNumber().equals(revokedCert.getSerialNumber())) {
		    		//System.out.println(revokedCert.getSerialNumber());
		    		throw new Exception("Sertifikat je povucen");
		    	}
		    }
		} else {
		    System.out.println("No certificates have been revoked in this CRL.");	    
		}
		
	}
	
	
	private static Integer loadAndIncreaseSerialNumber() {
		Integer number = 0;
		try {
			File file = new File(SERIAL_NUMBER);
			Scanner scanner = new Scanner(file);
			
			String hexString = scanner.nextLine().trim();
		    number = Integer.parseInt(hexString, 16);
            scanner.close();
            
            number += 1;
            
         // Open the file for writing
            FileWriter fileWriter = new FileWriter(file);
            
            // Write the updated hexadecimal number to the file
            String updatedHexString = Integer.toHexString(number);
            fileWriter.write(updatedHexString);
            fileWriter.close();
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return number;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	//OVO SE MOZE IZBRISATI
	public static PrivateKey getPrivateKey() throws Exception {

		byte[] keyBytes = Files.readAllBytes(Paths.get("C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\requests\\Mirkokljuc.key"));
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALG);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		PrivateKey pk = keyFactory.generatePrivate(keySpec);
		
		return pk;
	}

	public static PrivateKey getKeyFromPKCS12() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyStore keystore;
		InputStream is;
		PrivateKey privateKey = null;
		// PKCS12KeyStoreSpi store;
		try {
			keystore = KeyStore.getInstance("PKCS12");
			is = new FileInputStream(KEYSTORE_FILE);
			keystore.load(is, KEYSTORE_PASS.toCharArray());

			privateKey = (PrivateKey) keystore.getKey("1", KEYSTORE_PASS.toCharArray());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		return privateKey;
	}
	
	public static PrivateKey getPrivateKeyFromPKCS12(String keyStoreName) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		String keyStorePath = "C:\\Users\\Bojan\\Kriptografija_projekat\\Okruzenje\\certs\\";
		KeyStore keystore;
		InputStream is;
		PrivateKey privateKey = null;
		// PKCS12KeyStoreSpi store;
		try {
			keystore = KeyStore.getInstance("PKCS12");
			is = new FileInputStream(keyStorePath + keyStoreName);
			keystore.load(is, KEYSTORE_PASS.toCharArray());

			
			privateKey = (PrivateKey) keystore.getKey("alias", KEYSTORE_PASS.toCharArray());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		return privateKey;
	}

}