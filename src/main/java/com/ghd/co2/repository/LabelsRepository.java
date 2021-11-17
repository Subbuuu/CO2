package com.ghd.co2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ghd.co2.entity.Labels;

@Repository
public interface LabelsRepository extends JpaRepository<Labels, Integer> {

	@Query("select l.labelTitle from Labels l where l.chartId = :chartId")
	List<String> getLabelsbyId(@Param("chartId") Integer chartId);
	
	@Query("select l from Labels l where l.chartId = :chartId")
	List<Labels> getAllLabels(@Param("chartId") Integer chartId);

}
