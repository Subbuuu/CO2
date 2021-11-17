package com.ghd.co2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ghd.co2.entity.Chart;

@Repository
public interface ChartRepository extends JpaRepository<Chart, Integer> {

	@Query("select distinct c.id from Chart c")
	List<Integer> getChartList();
	
	@Query("select c.chartTitle from Chart c where c.id = :id")
	String getTitle(@Param("id") Integer id);
	
	@Query("select c.labelsCount from Chart c where c.id = :id")
	Integer getLabelsCount(@Param("id") Integer id);

}
