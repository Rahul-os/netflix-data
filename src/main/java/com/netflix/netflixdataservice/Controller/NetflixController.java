package com.netflix.netflixdataservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.netflix.netflixdataservice.Entity.NetflixData;
import com.netflix.netflixdataservice.Service.NetflixService;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/netflixData")
public class NetflixController {

	@Autowired
	NetflixService service;

	@RequestMapping(value = "/insertData" , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NetflixData> insertNetflixData(@RequestBody NetflixData data) {
		NetflixData newData = service.saveNetflixData(data);
		if(newData != null)
			return new ResponseEntity<>(data, HttpStatus.OK);
		else
			throw new RuntimeException("Inserting new data into database failed!!!");
	}

	@PatchMapping("/updateDetails/{title}")
	public ResponseEntity<NetflixData> updateNetflixData(@PathVariable String title , @RequestBody NetflixData newData) {
		NetflixData updatedNetflixData = service.updateNetflixData(title , newData);
		if(updatedNetflixData != null)
			return new ResponseEntity<>(updatedNetflixData,HttpStatus.ACCEPTED);
		else
			throw new RuntimeException("Internal server error!!");
	}

	@GetMapping("/findByTitle/{title}")
	public ResponseEntity<Optional<List<NetflixData>>> findByNetflixTitle(@PathVariable String title) {
		Optional<List<NetflixData>> data = service.findByTitle(title);
		if(data.isPresent()){				// since I used Optional<List<NetflixData>> , i am checking if the data is present and then get the data , and check if it is not empty.
			List<NetflixData> list = data.get();
			if(!list.isEmpty())
				return new ResponseEntity<>(data , HttpStatus.FOUND);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("/deleteByTitle/{title}")
	public ResponseEntity<String> deleteNetflixDataByTitle(@PathVariable String title) {
		Optional<List<NetflixData>> data = service.findByTitle(title);
		if(data.isPresent()){				// since I used Optional<List<NetflixData>> , i am checking if the data is present and then get the data , and check if it is not empty.
			List<NetflixData> list = data.get();
			if(!list.isEmpty()) {
				service.deleteMovieAndShowData(title);
				return new ResponseEntity<>("The movie/show with given title " + title + " is deleted", HttpStatus.OK);
			}
			else
				return new ResponseEntity<>("!!!There is no movie / show with the entered title. Please enter correct title to delete the data!!!", HttpStatus.NOT_FOUND);

		}
		else
			return new ResponseEntity<>("!!!There is no movie / show with the entered title. Please enter correct title to delete the data!!!", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/getAllRecords" , method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NetflixData>> getListOfAllMoviesAndShows() {
		List<NetflixData> listOfRecords = service.getAllRecords();
		if(!listOfRecords.isEmpty())
			return new ResponseEntity<>(listOfRecords,HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
