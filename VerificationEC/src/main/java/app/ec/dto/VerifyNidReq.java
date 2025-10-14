package app.ec.dto;

import java.sql.Date;

public class VerifyNidReq {
	
    public VerifyNidReq() {
		super();
	}
	public VerifyNidReq(String nid, String type, String name, String nameEn, String fname, String mname) {
		super();
		this.nid = nid;
		this.type = type;
		this.name = name;
		this.nameEn = nameEn;
		this.fname = fname;
		this.mname = mname;
	}
	private String nid;
    private String type;
    private String name;
    private String nameEn;
    private Date dob;
    private String fname;
    private String mname;
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
    
    

}
