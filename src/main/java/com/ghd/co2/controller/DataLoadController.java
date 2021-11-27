package com.ghd.co2.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ghd.co2.dto.ResponseDTO;
import com.ghd.co2.service.DataLoadService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/inputData", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DataLoadController {

	@Autowired
	DataLoadService dataLoadService;
	
	@PostMapping("/userInput")
	public ResponseDTO<ResponseEntity<String>> saveUserInputData(@RequestBody List<Map<String,String>> userInputResponseJson) throws FileNotFoundException, IOException {
		ResponseDTO<ResponseEntity<String>> status = dataLoadService.saveUserInputData(userInputResponseJson);
		return status;	
	}
}