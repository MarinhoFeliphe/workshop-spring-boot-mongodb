package com.feliphe.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feliphe.workshopmongo.domain.User;
import com.feliphe.workshopmongo.dto.UserDTO;
import com.feliphe.workshopmongo.repository.UserRepository;
import com.feliphe.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public User findById(String id) {
		
		Optional<User> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public User fromDTO(UserDTO objDTO) {
		return new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail());
	}
	
	public User update(User changedUser) {
		
		Optional<User> originalUser = repo.findById(changedUser.getId());
		
		originalUser.get().setName(changedUser.getName());
		originalUser.get().setEmail(changedUser.getEmail());
		
		return repo.save(originalUser.get());
	}
}
