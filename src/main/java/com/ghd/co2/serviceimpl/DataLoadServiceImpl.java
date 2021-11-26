package com.ghd.co2.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ghd.co2.dto.ResponseDTO;
import com.ghd.co2.service.DataLoadService;
import com.ghd.co2.service.OutputService;

@Service
public class DataLoadServiceImpl implements DataLoadService {

	@Autowired
	OutputService outputService;
	
	@Override
	public ResponseDTO<ResponseEntity<String>> saveUserInputData(List<Map<String, String>> userInputResponseJson) throws IOException, FileNotFoundException {
		
		ResponseDTO<ResponseEntity<String>> response = new ResponseDTO<ResponseEntity<String>>();
		
		try {	
		 String location = outputService.downloadBlob();
		 
//		 FileSystem system = FileSystems.getDefault();
//	     Path original = system.getPath(location + "Abatement_Option_sheet_template.xlsx");
//         Path target = system.getPath("D:\\GHD\\Test\\Abatement_Option_sheet_template_copy.xlsx");
//
//	        try {
//	            // Throws an exception if the original file is not found.
//	            Files.copy(original, target, StandardCopyOption.REPLACE_EXISTING);
//	        } catch (IOException ex) {
//	            System.out.println("ERROR");
//	        }
	        
	        FileInputStream fis = new FileInputStream(new File(location + "\\Abatement_Option_sheet_template.xlsx"));

			XSSFWorkbook wb = new XSSFWorkbook(fis); 

			XSSFSheet sheet = wb.getSheetAt(2);

			BufferedReader reader = new BufferedReader(new FileReader(location + "\\JSON_Template.txt"));
			String json = "";
			try {
				StringBuilder sb = new StringBuilder();
				String line = reader.readLine();

				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = reader.readLine();
				}
				json = sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}

			JSONObject obj = new JSONObject(json);

			JSONArray arr = obj.getJSONArray("temp");
			
			for(int j=0; j<userInputResponseJson.size(); j++) {
				
				Map<String,String> inputData = userInputResponseJson.get(j);
				for(int i=0; i<arr.length(); i++) {
					
					String code = arr.getJSONObject(i).getString("Title_code");
					
					if (!arr.getJSONObject(i).getString("Is_year_based").equals("No")) {
						
						String year = arr.getJSONObject(i).getString("Year");
						if (inputData.get("Title_code").equals(code) && inputData.get("Year").equals(year)) {
							
							Row r1 = sheet.getRow(arr.getJSONObject(i).getInt("Row_id") - 1);
							if (r1 == null) {
								r1 = sheet.createRow(arr.getJSONObject(i).getInt("Row_id") - 1);
							}
							
							Cell c1 = r1.getCell(arr.getJSONObject(i).getInt("Column_id") - 1);
							if (c1 == null) {
								c1 = r1.createCell(arr.getJSONObject(i).getInt("Column_id") - 1);
							}
							
							c1.setCellValue(inputData.get("Value"));
							break;
						}
					}
					
					else {
						if (inputData.get("Title_code").equals(code)) {
							
							Row r1 = sheet.getRow(arr.getJSONObject(i).getInt("Row_id") - 1);
							if (r1 == null) {
								r1 = sheet.createRow(arr.getJSONObject(i).getInt("Row_id") - 1);
							}
							
							Cell c1 = r1.getCell(arr.getJSONObject(i).getInt("Column_id") - 1);
							if (c1 == null) {
								c1 = r1.createCell(arr.getJSONObject(i).getInt("Column_id") - 1);
							}
							
							c1.setCellValue(inputData.get("Value"));
							break;
						}
					}
				}
			}
			
			FileOutputStream outputStream = new FileOutputStream(location + "\\Abatement_Option_sheet_template.xlsx"); 
			wb.write(outputStream);
			
			response.setMessage("Success");
			response.setResultSet(new ResponseEntity<>("Values saved successfully", HttpStatus.OK));
			return response;
		}
		
		catch(Exception e) {
			response.setMessage("-- saveInputValuesColumns Failed - " + e.getMessage() + " --");
			return response;
		}
	}

}
