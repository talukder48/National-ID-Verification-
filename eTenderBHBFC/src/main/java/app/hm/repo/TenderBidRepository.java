package app.hm.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.hm.entity.TenderBid;
import app.hm.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface TenderBidRepository extends JpaRepository<TenderBid, Long> {

	 List<TenderBid> findByUser(User user);

	    // Optional: find by user and status
	    List<TenderBid> findByUserAndStatus(User user, String status);
    

    List<TenderBid> findByStatus(String status);
    
   
}
