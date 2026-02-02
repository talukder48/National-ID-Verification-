package app.hm.dto;


import org.springframework.web.multipart.MultipartFile;

public class DocumentForm {

	private Long loanId;
    private MultipartFile nidFile;
    private MultipartFile incomeProof;
    private MultipartFile[] additionalDocs;

    public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	// Getters & Setters
    public MultipartFile getNidFile() { return nidFile; }
    public void setNidFile(MultipartFile nidFile) { this.nidFile = nidFile; }

    public MultipartFile getIncomeProof() { return incomeProof; }
    public void setIncomeProof(MultipartFile incomeProof) { this.incomeProof = incomeProof; }

    public MultipartFile[] getAdditionalDocs() { return additionalDocs; }
    public void setAdditionalDocs(MultipartFile[] additionalDocs) { this.additionalDocs = additionalDocs; }
}
