package app.ec.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import app.ec.model.EcTokenInfo;

public interface EcTokenInfoRepo extends JpaRepository<EcTokenInfo,String> {
	
}
