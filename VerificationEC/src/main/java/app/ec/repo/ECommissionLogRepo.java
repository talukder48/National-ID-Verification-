package app.ec.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import app.ec.model.ECommissionLog;


public interface ECommissionLogRepo extends JpaRepository<ECommissionLog,String> {

}
