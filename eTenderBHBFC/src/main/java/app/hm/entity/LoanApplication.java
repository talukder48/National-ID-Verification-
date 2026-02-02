package app.hm.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import app.hm.enums.LoanStatus;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== Applicant Info =====
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String nid;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private String gender;

    // ===== Employment Info =====
    @Column(nullable = false)
    private String employmentType;

    private String organization;

    @Column(nullable = false)
    private Double income;

    // ===== Loan Info =====
    @Column(nullable = false)
    private String loanType;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Integer tenure;

    @Column(nullable = false)
    private LoanStatus status =LoanStatus.PENDING; // default status

    @Column(nullable = false)
    private LocalDate submittedDate = LocalDate.now();
    
    
    // Link to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // ===== Getters & Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getNid() { return nid; }
    public void setNid(String nid) { this.nid = nid; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public Double getIncome() { return income; }
    public void setIncome(Double income) { this.income = income; }

    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Integer getTenure() { return tenure; }
    public void setTenure(Integer tenure) { this.tenure = tenure; }

   

    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LoanStatus getStatus() {
		return status;
	}
	public void setStatus(LoanStatus status) {
		this.status = status;
	}
	public LocalDate getSubmittedDate() { return submittedDate; }
    public void setSubmittedDate(LocalDate submittedDate) { this.submittedDate = submittedDate; }
}
