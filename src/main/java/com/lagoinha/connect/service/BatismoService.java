package com.lagoinha.connect.service;

import com.lagoinha.connect.model.batismo.Batismo;
import com.lagoinha.connect.model.connect.Connect;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BatismoService {

	private final MongoTemplate mongoTemplate;
	
	private static final  String COLLECTION = "batismo";
	
	public Batismo save(Batismo batismo) {
		return mongoTemplate.save(batismo, COLLECTION);
	}
	
	public List<Batismo> list(){
		return mongoTemplate.findAll(Batismo.class, COLLECTION);
	}
	
	public Batismo findById(String id) {
		return mongoTemplate.findById(id, Batismo.class, COLLECTION);
	}
	
	public DeleteResult delete(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, Batismo.class, COLLECTION);
	}
	
	public Batismo edit(Batismo batismo) {
		Query query  = new Query(Criteria.where("id").is(batismo.getId()));
		Batismo batismoAux = mongoTemplate.findOne(query, Batismo.class);
		if(batismoAux != null) {
			return mongoTemplate.save(batismo, COLLECTION);
		}
		return null;
	}
	
	public Batismo addToBatismo(Batismo batismo, Connect connect) {
		List<Connect> connects = batismo.getConnects();
		if(connects == null || connects.isEmpty()) {
			List<Connect> connectsEmpty = new ArrayList<>();
			connectsEmpty.add(connect);
			batismo.setConnects(connectsEmpty);
		}else {
			connects.add(connect);
		}
		
		Query query  = new Query(Criteria.where("id").is(batismo.getId()));
		Batismo batismoAux = mongoTemplate.findOne(query, Batismo.class);
		if(batismoAux != null) {
			return mongoTemplate.save(batismo, COLLECTION);
		}
		return null;
	}

	public Boolean deleteConnect(String idBatismo, String idConnect) {
		try {
			Query query = new Query(Criteria.where("id").is(idBatismo));
			Batismo batismo = mongoTemplate.findOne(query, Batismo.class);
            assert batismo != null;
            List<Connect> connects = batismo.getConnects();
			for(Connect connect: connects) {
				if(connect.getId().equals(idConnect)) {
					connects.remove(connect);
					break;
				}
			}
			batismo.setConnects(connects);
			mongoTemplate.save(batismo, COLLECTION);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
}
