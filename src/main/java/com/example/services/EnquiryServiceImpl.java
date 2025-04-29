package com.example.services;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.dto.DashboardDto;
import com.example.dto.EnquiriesDto;
import com.example.entities.Counsellor;
import com.example.entities.Enquiries;
import com.example.repository.CounsellorRepository;
import com.example.repository.EnquiriesRepository;

@Service
public class EnquiryServiceImpl implements EnquiriesServices {
	@Autowired
	EnquiriesRepository enqRepo;
	
	@Autowired
	CounsellorRepository consRepo;
	
	@Override
	public DashboardDto getDashboard(Long counsellorId) {
		//Creating counsellor object and injecting counsellor id with other fields as default values
		Counsellor counsellor=new Counsellor();
		counsellor.setCounsellorId(counsellorId);
		
		//Injecting counsellor object to enquiries object where counsellor object is an data variable
		Enquiries enqs=new Enquiries();
		enqs.setCounsellor(counsellor);
		
//		dashDto.setTotalEnqs(enqRepo.findTotalEnqs(counsellorId));	//total enquiries
//		dashDto.setEnrolledEnqs(enqRepo.findByEnqStatus(counsellorId,"ENROLLED"));//enrolled enquiries
//		dashDto.setLostEnqs(enqRepo.findByEnqStatus(counsellorId,"OPENED"));//opened enquiries
//		dashDto.setLostEnqs(enqRepo.findByEnqStatus(counsellorId,"LOST"));//lost enquiries
		
//		int total=enqRepo.findByCounsellorCounsellorId(counsellorId).size();
		
		
		List<Enquiries> lstEnqs=enqRepo.findAll(Example.of(enqs));//it will return all the records where enqs field have values
																 //In this CASE only counsellor field have value in enquiries object,
																//in counsellor object only counsellorId have value, 
															   //it will fetch record on the basis of only counsellorId
		int total=lstEnqs.size();
		
		int enrolled=lstEnqs.stream().filter(e-> e.getEnqStatus().equalsIgnoreCase("ENROLLED")).
										collect(Collectors.toList()).size();
		
		int open=lstEnqs.stream().filter(e->e.getEnqStatus().equalsIgnoreCase("OPEN"))
								 .collect(Collectors.toList()).size();
		
		int lost=lstEnqs.stream().filter(e->e.getEnqStatus().equalsIgnoreCase("LOST")).
								collect(Collectors.toList()).size();
		
//		DashboardDto dashDto=new DashboardDto();
//		dashDto.setTotalEnqs(total);
//		dashDto.setEnrolledEnqs(enrolled);
//		dashDto.setOpenEnqs(open);
//		dashDto.setLostEnqs(lost);
		
		
		return DashboardDto.builder()
						   .totalEnqs(total)
						   .enrolledEnqs(enrolled)
						   .openEnqs(open)
						   .lostEnqs(lost)
						   .build();
				 
	}

	@Override
	public boolean upsertEnquiries(EnquiriesDto enqDto, Long counsellorId) {
		
		Enquiries enq=new Enquiries();
		
		BeanUtils.copyProperties(enqDto, enq);
	
		//setting enquiryDto to enquiries object to communicate with DB 
//		enq.setName(enqs.getName());
//		enq.setCounsellorId(cons);
//		enq.setEnqStatus(enqs.getEnqStatus());
//		enq.setClassMode(enqs.getClassMode());
//		enq.setCourseName(enqs.getCourseName());
//		enq.setPhno(enqs.getPhno());
		
		Counsellor counsellor=consRepo.findById(counsellorId).get();
		
		enq.setCounsellor(counsellor);
		
		Enquiries enqs=enqRepo.save(enq);
		
		return enqs.getEnqId()!=null;
	}

	@Override
	public List<EnquiriesDto> viewEnquiries(Long counsellorId) {
		
		List<EnquiriesDto> lstEnqDto=new ArrayList<>();
		
		Counsellor counsellor=new Counsellor();
		counsellor.setCounsellorId(counsellorId);
		
		Enquiries enqs=new Enquiries();
		enqs.setCounsellor(counsellor);
		
		List<Enquiries> lstEnq=enqRepo.findAll(Example.of(enqs));
		
		ListIterator lstIterator=lstEnq.listIterator();
		
		while(lstIterator.hasNext()) {
			EnquiriesDto enqsDto=new EnquiriesDto();
			Enquiries enq=(Enquiries)lstIterator.next();
			
//			enqsDto.setName(enq.getName());
//			enqsDto.setEnqStatus(enq.getEnqStatus());
//			enqsDto.setPhno(enq.getPhno());
//			enqsDto.setClassMode(enq.getClassMode());
//			enqsDto.setCourseName(enq.getCourseName());
			
			BeanUtils.copyProperties(enq, enqsDto);
			lstEnqDto.add(enqsDto);
		}
		return lstEnqDto;
	}

	@Override
	public List<EnquiriesDto> filterEnquiries(EnquiriesDto enqsDto, Long counsellorId) {
		
		Enquiries entity=new Enquiries();
		
		if(enqsDto.getClassMode()!=null && !enqsDto.getClassMode().equals(" ")) {
			entity.setClassMode(enqsDto.getClassMode());
		}
		
		if(enqsDto.getCourseName()!=null && !enqsDto.getCourseName().equals(" ")) {
			entity.setCourseName(enqsDto.getCourseName());
		}
		
		if(enqsDto.getEnqStatus()!=null && !enqsDto.getEnqStatus().equals(" ")) {
			entity.setEnqStatus(enqsDto.getEnqStatus());
		}
		
		Counsellor counsellor=consRepo.findById(counsellorId).get();
		
		entity.setCounsellor(counsellor);
		
		List<Enquiries> lstEnqs=enqRepo.findAll(Example.of(entity));
		
		List<EnquiriesDto> lstEnqsDto=new ArrayList<>();
		lstEnqs.forEach(e->{
			EnquiriesDto enqDto=new EnquiriesDto();
			BeanUtils.copyProperties(e, enqDto);
			lstEnqsDto.add(enqDto);
		});
		return lstEnqsDto;
	}

//	@Override
//	public EnquiriesDto getEnquiry(Long enqId) {
//		Enquiries enqs=enqRepo.findById(enqId).get();
//		EnquiriesDto enqsDto=new EnquiriesDto();
//		enqsDto.setName(enqs.getName());
//		enqsDto.setPhno(enqs.getPhno());
//		enqsDto.setCourseName(enqs.getCourseName());
//		enqsDto.setEnqStatus(enqs.getEnqStatus());
//		enqsDto.setCourseName(enqs.getEnqStatus());
//		enqsDto.setClassMode(enqs.getClassMode());
//		enqsDto.setEnqId(enqs.getEnq_id());
//		return enqsDto;
//	}
	
	@Override
	public EnquiriesDto getEnquiry(Long enqId) {
		
		Enquiries enq=enqRepo.findById(enqId).get();
		
		EnquiriesDto enqDto=new EnquiriesDto();
		BeanUtils.copyProperties(enq, enqDto);
		
		return enqDto;
	}

	@Override
	public EnquiriesDto getById(Long enqId) {
		Enquiries enqs=enqRepo.findById(enqId).get();
		
		EnquiriesDto enqDto=new EnquiriesDto();
		
		BeanUtils.copyProperties(enqs, enqDto);
		return enqDto;
	}
}
