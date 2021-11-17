package com.ghd.co2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ghd.co2.entity.Colour;

@Repository
public interface ColourRepository extends JpaRepository<Colour, Integer> {

	@Query("select c from Colour c where c.labelId = :labelId")
	List<Colour> getAllColourData(@Param("labelId") Integer labelId);
}
