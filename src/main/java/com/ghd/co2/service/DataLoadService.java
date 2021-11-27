package com.ghd.co2.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ghd.co2.dto.ResponseDTO;

public interface DataLoadService {

	ResponseDTO<ResponseEntity<String>> saveUserInputData(List<Map<String, String>> userInputResponseJson) throws FileNotFoundException, IOException;

}
