package app.hm.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.hm.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
	  List<Shop> findByLocation(String location);
	    
	    List<Shop> findByAreaMainFloorGreaterThan(Double area);
	    
	    List<Shop> findByAreaMezzanineFloorGreaterThan(Double area);
}
