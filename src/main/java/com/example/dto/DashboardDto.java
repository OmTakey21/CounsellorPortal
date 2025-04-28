package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardDto {
	private Integer totalEnqs;
	private Integer enrolledEnqs;
	private Integer openEnqs;
	private Integer lostEnqs;
}
