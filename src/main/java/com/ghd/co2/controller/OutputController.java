package com.ghd.co2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ghd.co2.dto.ResponseDTO;
import com.ghd.co2.service.OutputService;
import com.ghd.co2.vo.OutputJson;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/outputData", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class OutputController {

	@Autowired
	OutputService outputService;
	
	@GetMapping("/extractOutput")
	public ResponseDTO<List<OutputJson>> extractOutputData() {
		ResponseDTO<List<OutputJson>> status = outputService.extractOutputData();
		return status;	
	}
}
