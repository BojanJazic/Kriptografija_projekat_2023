package modelDB;

public class CertRequests {
	String ID;
	String requestName;
	

	public CertRequests() {
	}

	public CertRequests(String iD, String requestNam) {
		super();
		ID = iD;
		this.requestName = requestName;
		
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

}
