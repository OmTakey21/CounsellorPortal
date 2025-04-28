package com.example.services;

import com.example.dto.CounsellorDto;

public interface CounsellorService {
	public CounsellorDto login(String email,String pwd);
	
	public boolean registerCounsellor(CounsellorDto cns);
	
	public boolean isEmailUnique(String email);


}
