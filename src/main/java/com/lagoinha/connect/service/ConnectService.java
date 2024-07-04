package com.lagoinha.connect.service;

import java.util.ArrayList;
import java.util.List;
import com.lagoinha.connect.model.connect.Connect;
import com.lagoinha.connect.util.ServiceException;
import com.lagoinha.connect.util.StringHelper;
import com.mongodb.client.result.DeleteResult;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConnectService {

	private final MongoTemplate mongoTemplate;

	private static final String COLLECTION = "connect";
	
	public Connect save(Connect connect) {
		try {
			if(Boolean.TRUE.equals(validarConnect(connect))) {
				salvarStrategy(connect);
				return mongoTemplate.save(connect, COLLECTION);
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
	}

	private void salvarStrategy(Connect connect){
		switch(connect.getTipo()){
			case KIDS:
				connect.setIsKids(true);
				break;
			case MEMBRO:
				connect.setIsMembro(true);
				break;
			case RECOMECO:
				connect.setIsRecomeco(true);
				break;
			case VOLUNTARIO:
				connect.setIsVoluntario(true);
				break;
			case BATIZAR:
				connect.setProntoBatismo(true);
		}
	}

	public String redirectByType(Connect connect){
		switch(connect.getTipo()){
			case KIDS:
				return "redirect:/connect/index/kids";
			case MEMBRO:
				return "redirect:/connect/index/membro";
			case RECOMECO:
				return "redirect:/connect/index/recomeco";
			case VOLUNTARIO:
				return "redirect:/connect/index/voluntario";
			case BATIZAR:
				return "redirect:/connect/index/batizar";
			default:
				return "redirect:/connect/index";
		}
	}

	public String redirectErrorByType(Connect connect){
		switch(connect.getTipo()){
			case KIDS:
				return "connect/add-connect-kids";
			case MEMBRO:
				return "connect/add-connect-membro";
			case RECOMECO:
				return "connect/add-connect-recomeco";
			case VOLUNTARIO:
				return "connect/add-connect-voluntario";
			case BATIZAR:
				return "connect/add-connect-batizar";
			default:
				return "connect/add-connect";
		}
	}
	
	public Boolean validarConnect(Connect connect) {
		Boolean retorno = true;
		List<String> mensagens = new ArrayList<>();
		if(Boolean.FALSE.equals(StringHelper.validarString(connect.getName()))) {
			mensagens.add("O campo nome é obrigatório.");
		}
		if(Boolean.FALSE.equals(StringHelper.validarString(connect.getPhone()))) {
			mensagens.add("O campo telefone é obrigatório.");
		}
		if(connect.getTipo() == null){
			mensagens.add("O campo tipo é obrigatório.");
		}
		if(!mensagens.isEmpty()) {
			throw new ServiceException(StringHelper.listToString(mensagens));
		}
		return retorno;
	}
	
	public List<Connect> list(){
		return mongoTemplate.findAll(Connect.class, COLLECTION);
	}

	public List<Connect> listKids(){
		Query query = new Query(Criteria.where("isKids").is(true));
		return mongoTemplate.find(query, Connect.class, COLLECTION);
	}

	public List<Connect> listMembros(){
		Query query = new Query(Criteria.where("isMembro").is(true));
		return mongoTemplate.find(query, Connect.class, COLLECTION);
	}

	public List<Connect> listRecomecos(){
		Query query = new Query(Criteria.where("isRecomeco").is(true));
		return mongoTemplate.find(query, Connect.class, COLLECTION);
	}

	public List<Connect> listBatizar(){
		Query query = new Query(Criteria.where("prontoBatismo").is(true));
		return mongoTemplate.find(query, Connect.class, COLLECTION);
	}

	public List<Connect> listVoluntarios(){
		Query query = new Query(Criteria.where("isVoluntario").is(true));
		return mongoTemplate.find(query, Connect.class, COLLECTION);
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
					if(Boolean.TRUE.equals(validarConnect(connect))) {
						salvarStrategy(connect);
						return mongoTemplate.save(connect, COLLECTION);
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
}
