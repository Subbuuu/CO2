package com.ghd.co2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Chart {

	@Id
	Integer id;
	
	String chartTitle;
	
	Integer labelsCount;

	public Integer getLabelsCount() {
		return labelsCount;
	}

	public void setLabelsCount(Integer labelsCount) {
		this.labelsCount = labelsCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}
	
}
