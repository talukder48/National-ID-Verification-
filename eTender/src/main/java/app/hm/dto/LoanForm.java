package app.hm.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class LoanForm {

    @NotBlank
    private String fullName;

    @NotBlank
    private String nid;

    @NotBlank
    private String email;

    @NotBlank
    private String mobile;

    @NotNull
    private LocalDate dob;

    @NotBlank
    private String gender;

    @NotBlank
    private String employmentType;

    private String organization;

    @NotNull
    @Positive
    private Double income;

    @NotBlank
    private String loanType;

    @NotBlank
    private String purpose;

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    @Min(1)
    private Integer tenure;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}
    
    

    // Getters & Setters
    // (Generate them or use Lombok @Data)
}
