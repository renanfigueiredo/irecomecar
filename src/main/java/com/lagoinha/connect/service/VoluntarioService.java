package com.lagoinha.connect.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.lagoinha.connect.model.voluntario.Voluntario;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VoluntarioService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	private final static String COLLECTION = "voluntario";
	
	public Voluntario save(Voluntario voluntario) {
		if(voluntario.getName() != null) {
			voluntario.setName(voluntario.getName().toUpperCase());
		}
		return mongoTemplate.save(voluntario, COLLECTION);
	}
	
	public List<Voluntario> list(){
		return mongoTemplate.findAll(Voluntario.class, COLLECTION);
	}
	
	public Voluntario findById(String id) {
		return mongoTemplate.findById(id, Voluntario.class, COLLECTION);
	}
	
	public DeleteResult delete(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, Voluntario.class, COLLECTION);
	}
	
	public Voluntario edit(Voluntario voluntario) {
		try {
			Query query  = new Query(Criteria.where("id").is(voluntario.getId()));
			Voluntario voluntarioAuxiliar = mongoTemplate.findOne(query, Voluntario.class);
			if(voluntarioAuxiliar != null) {
				if(voluntario.getName() != null) {
					voluntario.setName(voluntario.getName().toUpperCase());
				}
				return mongoTemplate.save(voluntario, COLLECTION);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private boolean isNotEmptyString(String string) {
	    return string != null && !string.isEmpty();
	}
	
}
