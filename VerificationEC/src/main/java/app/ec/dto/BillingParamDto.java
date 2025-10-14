package app.ec.dto;


public class BillingParamDto {
 String startDate;
 String endDate;
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public BillingParamDto(String startDate, String endDate) {
	super();
	this.startDate = startDate;
	this.endDate = endDate;
}

 
}
