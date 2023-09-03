package com.netflix.netflixdataservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.netflixdataservice.Entity.NetflixData;
import com.netflix.netflixdataservice.Repository.NetflixRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NetflixServiceImpl implements NetflixService{

	@Autowired
	NetflixRepository repo;


	@Override
	public NetflixData saveNetflixData(NetflixData eachrow) {
		return repo.save(eachrow);
	}

	@Override
	public NetflixData updateNetflixData(String title , NetflixData newData) {
		if(!repo.findByTitle(title).isEmpty()) {
			NetflixData updatedData = repo.updateByTitle(title);    // also use 'id' while passing RequestBody from postman inorder to update the already present record.
			//updatedData.setId(newData.getId());
			updatedData.setTitle(newData.getTitle());
			updatedData.setDescription(newData.getDescription());
			updatedData.setRuntime(newData.getRuntime());
			updatedData.setImdb_score(newData.getImdb_score());
			repo.save(updatedData);
			return updatedData;
		} else
			throw new RuntimeException("Enter the proper title to update the details!!");
	}

	@Override
	public Optional<List<NetflixData>> findByTitle(String title) {
		Optional<List<NetflixData>> data = repo.findByTitle(title);
		return  data;

	}

	@Override
	public void deleteMovieAndShowData(String title) {
		NetflixData recordToBeDeleted = repo.deleteDataByTitle(title);  //we can also delete multiple movies which are having the same name by using List<> and then itearte and delete allof them.
		if(recordToBeDeleted != null)
			repo.delete(recordToBeDeleted);

	}

	@Override
	public List<NetflixData> getAllRecords() {
		return repo.findAll();
	}
}
