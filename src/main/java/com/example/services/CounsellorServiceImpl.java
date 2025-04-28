package com.example.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.CounsellorDto;
import com.example.entities.Counsellor;
import com.example.repository.CounsellorRepository;

@Service
public class CounsellorServiceImpl implements CounsellorService {
	@Autowired
	CounsellorRepository cnsRepo;

	@Override
	public CounsellorDto login(String email, String pwd) {
		
		Counsellor cns=cnsRepo.findByEmailAndPass(email,pwd);
		
//		consDto.setEmail(cns.getEmail());
//		consDto.setName(cns.getName());
//		consDto.setPass(cns.getPass());
//		consDto.setPhno(cns.getPhno());
		if(cns!=null) {
			CounsellorDto consDto = new CounsellorDto();
			BeanUtils.copyProperties(cns, consDto);
			return consDto;	
		}
		return null;
		
	}

	@Override
	public boolean registerCounsellor(CounsellorDto cns) {

			Counsellor counsellor = new Counsellor();
//			counsellor.setEmail(cns.getEmail());
//			counsellor.setName(cns.getName());
//			counsellor.setPass(cns.getPass());
//			counsellor.setPhno(cns.getPhno());
			BeanUtils.copyProperties(cns, counsellor);
			Counsellor cnslr=cnsRepo.save(counsellor);
			
		return cnslr.getCounsellorId()!=null;

	}

	@Override
	public boolean isEmailUnique(String email) {
		Optional<Counsellor> optCounsellor=cnsRepo.findByEmail(email);
		
		if(optCounsellor.isPresent()) {
			return false;
		}
		return true;
	}

}
