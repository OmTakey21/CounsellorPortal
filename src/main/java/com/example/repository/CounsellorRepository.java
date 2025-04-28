package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Counsellor;


public interface CounsellorRepository extends JpaRepository<Counsellor, Long> {
	
	public Counsellor findByEmailAndPass(String email,String pass);
	
	public Optional<Counsellor> findByEmail(String email);
}
