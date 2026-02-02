package app.hm.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import app.hm.enums.UserRole;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------- Login Info -------- */
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;



    private String phone;

    /* -------- Personal Info -------- */
    private String name;
    private String fatherOrSpouseName;
    private String nationality;
    private String religion;

    @Column(length = 1000)
    private String presentAddress;

    @Column(length = 1000)
    private String permanentAddress;

    private String nidNumber;

    /* -------- Business Info -------- */
    private String businessName;
    private String businessType;
    private String tradeLicenseNo;
    private LocalDate tradeLicenseRenewalDate;

    private String tinNumber;

    /* -------- System -------- */
    private Boolean enabled = true;
    private LocalDate createdAt = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private UserRole userRole ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFatherOrSpouseName() {
		return fatherOrSpouseName;
	}

	public void setFatherOrSpouseName(String fatherOrSpouseName) {
		this.fatherOrSpouseName = fatherOrSpouseName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getNidNumber() {
		return nidNumber;
	}

	public void setNidNumber(String nidNumber) {
		this.nidNumber = nidNumber;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTradeLicenseNo() {
		return tradeLicenseNo;
	}

	public void setTradeLicenseNo(String tradeLicenseNo) {
		this.tradeLicenseNo = tradeLicenseNo;
	}

	public LocalDate getTradeLicenseRenewalDate() {
		return tradeLicenseRenewalDate;
	}

	public void setTradeLicenseRenewalDate(LocalDate tradeLicenseRenewalDate) {
		this.tradeLicenseRenewalDate = tradeLicenseRenewalDate;
	}

	public String getTinNumber() {
		return tinNumber;
	}

	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	
}
