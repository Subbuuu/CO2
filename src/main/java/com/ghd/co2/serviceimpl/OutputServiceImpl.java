package com.ghd.co2.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ghd.co2.dto.ResponseDTO;
import com.ghd.co2.entity.Colour;
import com.ghd.co2.entity.Labels;
import com.ghd.co2.repository.ChartRepository;
import com.ghd.co2.repository.ColourRepository;
import com.ghd.co2.repository.LabelsRepository;
import com.ghd.co2.service.OutputService;
import com.ghd.co2.vo.Dataset;
import com.ghd.co2.vo.OutputJson;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;

@Service
public class OutputServiceImpl implements OutputService {
	
	@Autowired
	ChartRepository chartRepository;
	
	@Autowired
	LabelsRepository labelsRepository;
	
	@Autowired
	ColourRepository colourRepository;
	
	@Autowired
	Environment env;
	
	@Override
	public ResponseDTO<List<OutputJson>> extractOutputData() {
		
		ResponseDTO<List<OutputJson>> response = new ResponseDTO<List<OutputJson>>();
		
		List<OutputJson> outputData = new ArrayList<OutputJson>();
		
		List<Integer> chartCount = chartRepository.getChartList();
		
		try {
			
			String location = this.downloadBlob();
			
			FileInputStream fis = new FileInputStream(new File(location  + "\\Abatement_Option_sheet_template.xlsx"));

			XSSFWorkbook wb = new XSSFWorkbook(fis);
		
			for(int i : chartCount) {
			
				OutputJson output = new OutputJson();
				List<Dataset> datasets = new ArrayList<Dataset>();
				String title = chartRepository.getTitle(i);
				List<Labels> allLabels = labelsRepository.getAllLabels(i);
				List<String> labels = labelsRepository.getLabelsbyId(i);
			
				output.setTitle(title);
				output.setLabels(labels);
			
				for(Labels next : allLabels) {
				
					List<Colour> colourData = colourRepository.getAllColourData(next.getId());
				
					for(Colour colour : colourData) {
				
						Dataset newDataset = new Dataset();
					
						newDataset.setLabel(next.getLabelTitle());
						newDataset.setBackground(colour.getColourData());
						newDataset.setStack(next.getStackName());
							
						Integer resultData = Integer.valueOf(wb.getSheetAt(3).getRow(colour.getRowId()-1).getCell(colour.getColumnId()-1).getRawValue());
						
						List<Integer> dataList = new ArrayList<Integer>(Collections.nCopies(allLabels.size(), 0));
						dataList.set(next.getPosition(), resultData);		
							
						newDataset.setData(dataList);
						datasets.add(newDataset);
						
					}

				}
			
				output.setDatasets(datasets);
				
				outputData.add(output);
			}
			
			response.setMessage("Success");
			response.setResultSet(outputData);
			return response;
			
		} catch (Exception e) {
			response.setMessage("-- extractOutputData Failed - " + e.getMessage() + " --");
			return response;
		}
	
	}

	@Override
	public String downloadBlob() {
		try
		{
			String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=bridgei2i;AccountKey=WoaPYIJaWX2n/9HBTGO26U9K2u7p00g+iF5eWhqBszNtwFErg5pHXSLSn9JElGgsLdk7zM0E8c1vC9OYiJ/wNw==;EndpointSuffix=core.windows.net";
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString );
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference("co2");
			String home = env.getProperty("TEMP");
			for (ListBlobItem blobItem : container.listBlobs()) {
		       if (blobItem instanceof CloudBlob) {
		            CloudBlob blob = (CloudBlob) blobItem;
			        blob.download(new FileOutputStream(home + "\\" + blob.getName()));
			    }
			}
			return(home);
		}
		
		catch (Exception e)
		{
		    e.printStackTrace();
		    return("Failure " + e.getMessage());
		}
	}

}
