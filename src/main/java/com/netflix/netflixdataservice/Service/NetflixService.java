package com.netflix.netflixdataservice.Service;

import com.netflix.netflixdataservice.Entity.NetflixData;

import java.util.List;
import java.util.Optional;

public interface NetflixService {
	


	NetflixData saveNetflixData(NetflixData newdata);

	NetflixData updateNetflixData(String title , NetflixData newData);

	Optional<List<NetflixData>> findByTitle(String title);

	void deleteMovieAndShowData(String title);

	List<NetflixData> getAllRecords();

	void saveAllRecordsInCsvFileAtOnce(List<NetflixData> netflixDataList);

//	int checkTheCountOfTile(List<NetflixData> eachrecord);
}
