package dataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelDB.*;
import dbConnection.DBConnection;

public class DataAccess {

	private static final String SELECT_CREDENTIALS = "Select username, password from person";
	private static final String INSERT_PERSON = "insert into person(ID, Name, username, password) values(?, ?, ?, ?)";
	private static final String INSERT_CERTIFICATE = "insert into certificates(ID, certificateName) values(?, ?)";
	private static final String SELECT_CERT = "select certificateName from certificates where ID=?";
	private static final String SELECT_ALL_CERT = "select certificateName from certificates";
	private static final String SELECT_USER_FILES = "select file, segment1, segment2, segment3, segment4, segment5, segment6"
													 + " from data where ID=?";
	private static final String SELECT_ALL_FILES = "select file from data";
	private static final String SELECT_USER_KEY = "select keyFile from certificates where ID=?";
	private static final String INSERT_NEW_REQUEST = "insert into certrequests(ID, requestName) values (?, ?)";
	private static final String SELECT_REQUEST = "select requestName from certrequests where ID=?";
	
	private static final String GET_USERNAME_AND_PASSWORD = "select username, password from person p inner join certificates c"
															+ " on p.ID=c.ID where c.certificateName=?";
	
	
	/**
	 * @return
	 */
	public static List<Person> getCredentials() {
		List<Person> person = new ArrayList<Person>();
		Connection conn = DBConnection.getConnection();
		Statement stm;
		ResultSet rst;
		try {
			stm = conn.createStatement();
			rst = stm.executeQuery(SELECT_CREDENTIALS);
			while (rst.next()) {
				Person cr = new Person();
				cr.setUsername(rst.getString("username"));
				cr.setPassword(rst.getString("password"));
				person.add(cr);
			}
			stm.close();
			DBConnection.closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Greska prilikom kreiranja iskaza.");
		} finally {
			DBConnection.closeConnection();
		}

		return person;
	}

	/**
	 * @return
	 */
	public static String getUserCert(String ID) {
		String certs = null;
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstm;
		ResultSet rst;

		try {
			pstm = conn.prepareStatement(SELECT_CERT);
			pstm.setString(1, ID);
			rst = pstm.executeQuery();
			while (rst.next()) {
				certs = rst.getString("certificateName");
			}
			pstm.close();
			DBConnection.closeConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}
		return certs;
	}
	
	
	
	public static List<Certificate> getAllCerts() {
		List<Certificate> certs = new ArrayList<Certificate>();
		Connection conn = DBConnection.getConnection();
		Statement stm;
		ResultSet rst;
		try {
			stm = conn.createStatement();
			rst = stm.executeQuery(SELECT_ALL_CERT);
			while (rst.next()) {
				Certificate cr = new Certificate();
				cr.setCertificateName(rst.getString("certificateName"));
				
				certs.add(cr);
			}
			stm.close();
			DBConnection.closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Greska prilikom kreiranja iskaza.");
		} finally {
			DBConnection.closeConnection();
		}

		return certs;
	}
	public static List<Person> getUsernameAndPassword(String certName) {
		List<Person> person = new ArrayList<Person>();
		Connection conn = DBConnection.getConnection();
		PreparedStatement stm;
		ResultSet rst;
		try {
			stm = conn.prepareStatement(GET_USERNAME_AND_PASSWORD);
			stm.setString(1, certName);
			rst = stm.executeQuery();
			while (rst.next()) {
				Person cr = new Person();
				cr.setUsername(rst.getString("username"));
				cr.setPassword(rst.getString("password"));
				person.add(cr);
			}
			stm.close();
			DBConnection.closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println("Greska prilikom kreiranja iskaza.");
			System.out.println(e.getMessage());
		} finally {
			DBConnection.closeConnection();
		}

		return person;
	}

	public static String getRequestName(String ID) {
		String reqName = new String();
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstm;
		ResultSet rst;
		try {
			pstm = conn.prepareStatement(SELECT_REQUEST);
			pstm.setString(1, ID);
			rst = pstm.executeQuery();
			while (rst.next()) {
				//CertRequests cr = new CertRequests();
			//	cr.setRequestName(rst.getString("requestName"));
				//reqName.add(cr);
				reqName = rst.getString("requestName");
			}

			pstm.close();
			DBConnection.closeConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			// System.out.println("OVDJE JE GRESKA");
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}

		return reqName;
	}

	public static String keyFileName(String ID) {
		String key = null;
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstm;
		ResultSet rst;
		try {
			pstm = conn.prepareStatement(SELECT_USER_KEY);
			pstm.setString(1, ID);
			rst = pstm.executeQuery();
			while (rst.next()) {
				key = rst.getString("keyFile");
			}
			pstm.close();
			DBConnection.closeConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}

		return key;
	}

	public static List<Data> getUserFiles(String ID) {
		List<Data> userFiles = new ArrayList<Data>();
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstm;
		ResultSet rst;
		try {
			pstm = conn.prepareStatement(SELECT_USER_FILES);
			pstm.setString(1, ID);
			rst = pstm.executeQuery();
			while (rst.next()) {
				Data data = new Data();
				data.setFileName(rst.getString("fileName"));
				data.setSegment1(rst.getString("segment1"));
				data.setSegment2(rst.getString("segment2"));
				data.setSegment3(rst.getString("segment3"));
				data.setSegment4(rst.getString("segment4"));
				data.setSegment5(rst.getString("segment5"));
				data.setSegment6(rst.getString("segment6"));
				userFiles.add(data);
			}

			pstm.close();
			DBConnection.closeConnection();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}

		return userFiles;
	}

	public static List<String> getAllFiles() {
		List<String> allFiles = new ArrayList<String>();
		Connection conn = DBConnection.getConnection();
		Statement stm;
		ResultSet rst;
		try {
			stm = conn.createStatement();
			rst = stm.executeQuery(SELECT_ALL_FILES);
			while (rst.next()) {
				allFiles.add(rst.getString("file"));
			}

			stm.close();
			DBConnection.closeConnection();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}

		return allFiles;
	}

	/**
	 * @param ID
	 * @param Name
	 * @param Surname
	 * @param Phone
	 * @param Address
	 */
	public static void insertPerson(String ID, String Name, String Username, String Password) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(INSERT_PERSON);
			pstm.setString(1, ID);
			pstm.setString(2, Name);
			pstm.setString(3, Username);
			pstm.setString(4, Password);
			pstm.executeUpdate();

			pstm.close();
			DBConnection.closeConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}
	}

	public static void insertCertRequest(String ID, String requestName) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(INSERT_NEW_REQUEST);
			pstm.setString(1, ID);
			pstm.setString(2, requestName);
			pstm.executeUpdate();

			pstm.close();
			DBConnection.closeConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}
	}

	public static void insertCertificate(String ID, String certificateName) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(INSERT_CERTIFICATE);
			pstm.setString(1, ID);
			pstm.setString(2, certificateName);
			pstm.executeUpdate();

			pstm.close();
			DBConnection.closeConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
		}
	}

}