package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.EnquiriesDto;
import com.example.services.EnquiriesServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	@Autowired
	EnquiriesServices enqService;
	
	@GetMapping("/enquiry")
	public String enquiry(Model model) {
		EnquiriesDto enqDto=new EnquiriesDto();
		
		model.addAttribute("enquiry", enqDto);
		
		return "enquiryPage";
	}
	
	@PostMapping("/enquiry")
	public String addEnquiries(@ModelAttribute("enquiry") EnquiriesDto enqDto,HttpServletRequest req,Model model) {
		
		HttpSession session=req.getSession(false);
		
		Long cid=(Long)session.getAttribute("cid");
		
		boolean flag=enqService.upsertEnquiries(enqDto, cid);
		
		if(flag) {
			model.addAttribute("smsg", "Enquiry added successfully");
		}
		else {
			model.addAttribute("emsg", "Enquiry not added");
		}
		
		model.addAttribute("enquiry", new EnquiriesDto());
		
		return "enquiryPage";
	}
	
	@GetMapping("/enquiries")
	public String viewEnquiries(HttpServletRequest req,Model model) {
		
		HttpSession session=req.getSession();
		
		Long cid=(Long)session.getAttribute("cid");
		
		List<EnquiriesDto> lstEnquiries=enqService.viewEnquiries(cid);
		
		model.addAttribute("enquiries",lstEnquiries);
		model.addAttribute("enqDto", new EnquiriesDto());
	
		return "viewEnquiries";
	}
	
	@PostMapping("/filter")
	public String filterEnquiries(@ModelAttribute("enqDto") EnquiriesDto enqDto,HttpServletRequest req,Model model) {
		HttpSession session=req.getSession();
		
		Long cid=(Long)session.getAttribute("cid");
		
		List<EnquiriesDto> lstEnquiries=enqService.filterEnquiries(enqDto, cid);
		
		
//		checking for enqId
		
//		for(EnquiriesDto enq:lstEnquiries) {
//			System.out.println(enq.getEnqId());
//		}
		
		model.addAttribute("enquiries", lstEnquiries);
		
		model.addAttribute("enqDto", new EnquiriesDto());
		
		return "viewEnquiries";
	}
	
	@GetMapping("/edit")
	public String editEnquiry(@RequestParam("enqId") Long enqId, Model model) {
	    EnquiriesDto enqDto = enqService.getById(enqId);
	    model.addAttribute("enquiry", enqDto);  // This should match th:object in your form
	    return "enquiryPage"; // This is the name of your Thymeleaf template
	}


}
