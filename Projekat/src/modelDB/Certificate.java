package modelDB;

public class Certificate {

	String ID;
	String certificateName;
	

	public Certificate() {
		// TODO Auto-generated constructor stub
	}
	
	public Certificate(String iD, String certificateName) {
		super();
		ID = iD;
		this.certificateName = certificateName;
		
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

}
