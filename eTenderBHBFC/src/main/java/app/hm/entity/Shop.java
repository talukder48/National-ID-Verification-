package app.hm.entity;



import jakarta.persistence.*;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    private String location;
    private Double areaMainFloor;
    private Double areaMezzanineFloor;
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Double getAreaMainFloor() {
		return areaMainFloor;
	}
	public void setAreaMainFloor(Double areaMainFloor) {
		this.areaMainFloor = areaMainFloor;
	}
	public Double getAreaMezzanineFloor() {
		return areaMezzanineFloor;
	}
	public void setAreaMezzanineFloor(Double areaMezzanineFloor) {
		this.areaMezzanineFloor = areaMezzanineFloor;
	}

    
}
