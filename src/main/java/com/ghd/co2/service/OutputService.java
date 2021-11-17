package com.ghd.co2.service;

import java.util.List;

import com.ghd.co2.dto.ResponseDTO;
import com.ghd.co2.vo.OutputJson;

public interface OutputService {

	ResponseDTO<List<OutputJson>> extractOutputData();

}
