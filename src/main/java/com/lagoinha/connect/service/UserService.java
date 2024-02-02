package com.lagoinha.connect.service;

import java.util.List;

import com.lagoinha.connect.model.user.RegisterDTO;
import com.lagoinha.connect.model.user.User;
import com.lagoinha.connect.util.Criptografia;
import com.mongodb.client.result.DeleteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	Criptografia criptografia;

	private final static String COLLECTION = "user";

	public User save(RegisterDTO data) {
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.name(), data.login(), encryptedPassword, data.role());
		return mongoTemplate.save(newUser, COLLECTION);
	}

	public List<User> list(){
		return mongoTemplate.findAll(User.class, COLLECTION);
	}

	public User findById(String id) {
		return mongoTemplate.findById(id, User.class, COLLECTION);
	}

	public DeleteResult delete(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.remove(query, User.class, COLLECTION);
	}

	public User edit(User usuario, String id) {
		Query query  = new Query(Criteria.where("id").is(id));
		User usuarioAuxiliar = mongoTemplate.findOne(query, User.class);
		if(usuarioAuxiliar != null) {
			String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getPassword());
			usuario.setPassword(encryptedPassword);
			return mongoTemplate.save(usuario, COLLECTION);
		}
		return null;
	}

    public UserDetails findByLogin(String login) {
		Query query  = new Query(Criteria.where("login").is(login));
		return mongoTemplate.findOne(query, User.class, COLLECTION);
    }
}
