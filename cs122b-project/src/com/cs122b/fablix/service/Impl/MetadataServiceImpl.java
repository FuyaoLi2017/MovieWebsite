package com.cs122b.fablix.service.Impl;

import java.util.ArrayList;
import java.util.List;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.dao.MetadataDao;
import com.cs122b.fablix.dao.Impl.MetadataImpl;
import com.cs122b.fablix.entity.vo.DBTableVo;
import com.cs122b.fablix.entity.vo.MetadataVo;
import com.cs122b.fablix.service.MetadataService;

public class MetadataServiceImpl implements MetadataService {

	private MetadataDao metadataDao = new MetadataImpl();
	@Override
	public ResponseModel<List<DBTableVo>> showMetaData() {
		List<DBTableVo> result = new ArrayList<>();
		
		DBTableVo creditcards = new DBTableVo();
		List<MetadataVo> creditcardsList = metadataDao.selectMetadataByTable("creditcards");
		creditcards.setTableName("creditcards");
		creditcards.setMetadataVoList(creditcardsList);
        result.add(creditcards);
        
        DBTableVo customers = new DBTableVo();
		List<MetadataVo> customersList = metadataDao.selectMetadataByTable("customers");
		customers.setTableName("customers");
		customers.setMetadataVoList(customersList);
        result.add(customers);
        
        DBTableVo employees = new DBTableVo();
		List<MetadataVo> employeesList = metadataDao.selectMetadataByTable("employees");
		employees.setTableName("employees");
		employees.setMetadataVoList(employeesList);
        result.add(employees);
        
        DBTableVo genres = new DBTableVo();
		List<MetadataVo> genresList = metadataDao.selectMetadataByTable("genres");
		genres.setTableName("genres");
		genres.setMetadataVoList(genresList);
        result.add(genres);
        
        DBTableVo genres_in_movies = new DBTableVo();
		List<MetadataVo> genresInMoviesList = metadataDao.selectMetadataByTable("genres_in_movies");
		genres_in_movies.setTableName("genres_in_movies");
		genres_in_movies.setMetadataVoList(genresInMoviesList);
        result.add(genres_in_movies);
        
        DBTableVo movies = new DBTableVo();
		List<MetadataVo> moviesList = metadataDao.selectMetadataByTable("movies");
		movies.setTableName("movies");
		movies.setMetadataVoList(moviesList);
        result.add(movies);
        
        DBTableVo ratings = new DBTableVo();
		List<MetadataVo> ratingsList = metadataDao.selectMetadataByTable("ratings");
		ratings.setTableName("ratings");
		ratings.setMetadataVoList(ratingsList);
        result.add(ratings);
        
        DBTableVo sales = new DBTableVo();
		List<MetadataVo> salesList = metadataDao.selectMetadataByTable("sales");
		sales.setTableName("sales");
		sales.setMetadataVoList(salesList);
        result.add(sales);
        
        DBTableVo stars = new DBTableVo();
		List<MetadataVo> starsList = metadataDao.selectMetadataByTable("stars");
		stars.setTableName("stars");
		stars.setMetadataVoList(starsList);
        result.add(stars);
        
        DBTableVo stars_in_movies = new DBTableVo();
		List<MetadataVo> starsInMoviesList = metadataDao.selectMetadataByTable("stars_in_movies");
		stars_in_movies.setTableName("stars_in_movies");
		stars_in_movies.setMetadataVoList(starsInMoviesList);
        result.add(stars_in_movies);
        
        return ResponseModel.createBySuccess("return a list of metadata info", result);
	}

}
