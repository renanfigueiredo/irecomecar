package com.lagoinha.connect.service;

import com.lagoinha.connect.model.connect.Connect;
import com.lagoinha.connect.util.ServiceException;
import com.lagoinha.connect.util.StringHelper;
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
public class PessoaService {

	private final MongoTemplate mongoTemplate;

	private static final String COLLECTION = "pessoa";
	
	public Connect save(Connect connect) {
		try {
			if(Boolean.TRUE.equals(validarConnect(connect))) {
				return mongoTemplate.save(connect, COLLECTION);
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
	}
	
	public Boolean validarConnect(Connect connect) {
		Boolean retorno = true;
		List<String> mensagens = new ArrayList<>();
		if(Boolean.FALSE.equals(StringHelper.validarString(connect.getName()))) {
			mensagens.add("O campo nome é obrigatório.");
		}
		if(Boolean.FALSE.equals(StringHelper.validarString(connect.getBirthDate()))) {
			mensagens.add("O campo data de nascimento é obrigatório.");
		}
		if(Boolean.FALSE.equals(StringHelper.validarString(connect.getPhone()))) {
			mensagens.add("O campo telefone é obrigatório.");
		}
		
		if(Boolean.TRUE.equals(!StringHelper.validarTelefone(connect.getPhone())) && Boolean.TRUE.equals(StringHelper.validarString(connect.getPhone()))){
			mensagens.add("O campo telefone está no formato errado.");
		}
		if(!mensagens.isEmpty()) {
			throw new ServiceException(StringHelper.listToString(mensagens));
		}
		return retorno;
	}
	
	public List<Connect> list(){
		return mongoTemplate.findAll(Connect.class, COLLECTION);
	}
	
	public Connect findById(String id) {
		return mongoTemplate.findById(id, Connect.class, COLLECTION);
	}
	
	public DeleteResult delete(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, Connect.class, COLLECTION);
	}
	
	public Connect edit(Connect connect) {
		try {
			if(Boolean.TRUE.equals(validarConnect(connect))) {
				Query query  = new Query(Criteria.where("id").is(connect.getId()));
				Connect usuarioAuxiliar = mongoTemplate.findOne(query, Connect.class);
				if(usuarioAuxiliar != null) {
					return mongoTemplate.save(connect, COLLECTION);
				}
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
}
