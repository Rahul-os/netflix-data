package com.netflix.netflixdataservice.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.netflix.netflixdataservice.Service.NetflixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.netflix.netflixdataservice.Entity.*;
import com.netflix.netflixdataservice.Service.ImportCSVDataService;
import org.springframework.web.multipart.MultipartFile;

@RestController

public class ImportDataFromCSVController {

	@Autowired
	ImportCSVDataService csvDataService;
	@Autowired
	NetflixService netflixService;

	List<NetflixData> netflixDataList = new ArrayList<>();
	@RequestMapping(value = "/importDataFromCsvFile" , method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
	public List<NetflixData> importDataFromCSVFile(@RequestParam("csvFile") MultipartFile file) throws Exception {
		try {
			File tempFile = File.createTempFile("temp",null);

			file.transferTo(tempFile);
			netflixDataList = csvDataService.importNetflixDataFromCSV(tempFile.getAbsolutePath());
			for (NetflixData eachrow : netflixDataList)
				netflixService.saveNetflixData(eachrow);

		}
		catch (Exception e){
			System.out.println(e);
		}
		if(!netflixDataList.isEmpty())
			return netflixDataList;
		else
			return null;
	}
}
