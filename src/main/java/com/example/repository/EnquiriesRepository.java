package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entities.Enquiries;


public interface EnquiriesRepository extends JpaRepository<Enquiries, Long> {
	@Query(value="Select count(*) from Enquiries where counsellor_id=?",nativeQuery=true)
	public Integer findTotalEnqs(Long counsellorId);
	
	@Query(value="Select count(*) from Enquiries where counsellor_id=? and enq_status=?",nativeQuery=true)
	public Integer findByEnqStatus(Long counsellorId,String status);
	
	public List<Enquiries> findByClassModeAndCounsellorCounsellorId(String classMode,Long counsellorId);
	
	public List<Enquiries> findByCounsellorCounsellorId(Long counsellorId);
}
