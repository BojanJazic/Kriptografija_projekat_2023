package modelDB;

public class Data {

	int DataID;
	String ID;
	String FileName;
	String segment1;
	String segment2;
	String segment3;
	String segment4;
	String segment5;
	String segment6;

	public Data() {}
	
	public Data(int dataID, String fileName, String iD) {
		super();
		DataID = dataID;
		FileName = fileName;
		ID = iD;
	}

	public int getDataID() {
		return DataID;
	}

	public void setDataID(int dataID) {
		DataID = dataID;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	public String getSegment1() {
		return segment1;
	}

	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}

	public String getSegment2() {
		return segment2;
	}

	public void setSegment2(String segment2) {
		this.segment2 = segment2;
	}

	public String getSegment3() {
		return segment3;
	}

	public void setSegment3(String segment3) {
		this.segment3 = segment3;
	}

	public String getSegment4() {
		return segment4;
	}

	public void setSegment4(String segment4) {
		this.segment4 = segment4;
	}

	public String getSegment5() {
		return segment5;
	}

	public void setSegment5(String segment5) {
		this.segment5 = segment5;
	}

	public String getSegment6() {
		return segment6;
	}

	public void setSegment6(String segment6) {
		this.segment6 = segment6;
	}

}