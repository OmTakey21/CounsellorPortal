package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dto.CounsellorDto;
import com.example.dto.DashboardDto;
import com.example.dto.EnquiriesDto;
import com.example.services.CounsellorService;
import com.example.services.EnquiriesServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	@Autowired
	CounsellorService consService;
	
	@Autowired
	EnquiriesServices enqService;
	
	@GetMapping("/index")
	public String login(Model model) {
		CounsellorDto consDto=new CounsellorDto();
		model.addAttribute("counsellorDto", consDto);
		return "loginPage";
	}
	
	@PostMapping("/login")
	public String validateLogin(CounsellorDto counsellor,HttpServletRequest request,Model model) {
		
		CounsellorDto consDto=consService.login(counsellor.getEmail(), counsellor.getPass());
		
		if(consDto==null) {
			model.addAttribute("emsg", "Invalid credentials");
			return "loginPage";
		}
		else {
			HttpSession session=request.getSession(true);
			session.setAttribute("cid",consDto.getCounsellorId());
			return "redirect:dashboard";
		}
	}
	
	@GetMapping("/dashboard")
	public String viewDashboard(HttpServletRequest req, Model model) {
		HttpSession session=req.getSession(false);
		
		Long consId=(Long)session.getAttribute("cid");
		
		DashboardDto dashDto=enqService.getDashboard(consId);
		
		model.addAttribute("dashDto", dashDto);
		
		return "dashboardPage";
	}

	@GetMapping("/register")
	public String register(Model model) {
		CounsellorDto counsellorDto=new CounsellorDto();
		model.addAttribute("counsellorDto", counsellorDto);
		return "register";
	}
	
	@PostMapping("/register")
	public String registerCounsellor(CounsellorDto consDto,Model model) {
	
		boolean flag=consService.isEmailUnique(consDto.getEmail());
		
		if(flag) {
			if(consService.registerCounsellor(consDto)) {
			model.addAttribute("smsg","User registration successfull");
			}
			else {
				model.addAttribute("emsg","Registration failed");
			}
		}
		else {
			model.addAttribute("emsg", "User is already registered with this email id, try with another");
		}
		
		return "register";
	}
		
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
}
