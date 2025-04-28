package com.example.services;

import java.util.List;

import com.example.dto.DashboardDto;
import com.example.dto.EnquiriesDto;

public interface EnquiriesServices {
	public DashboardDto getDashboard(Long counsellorId);
	public boolean upsertEnquiries(EnquiriesDto enqs,Long counsellorId);
	public List<EnquiriesDto> viewEnquiries(Long counsellorId);
	public List<EnquiriesDto> filterEnquiries(EnquiriesDto enqs,Long counsellorId);
	public EnquiriesDto getEnquiry(Long enqId);
}
