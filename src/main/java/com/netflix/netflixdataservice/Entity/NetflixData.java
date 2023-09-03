package com.netflix.netflixdataservice.Entity;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class NetflixData {

	@Id
	private String id;
	public String age_certification;
	public String description;
	List<String> genres = new ArrayList<>() ;
	public List<String> production_countries = new ArrayList<>();
	public int release_year;
	public int runtime;
	public String title;
	public String type;
	public double imdb_score;



}
