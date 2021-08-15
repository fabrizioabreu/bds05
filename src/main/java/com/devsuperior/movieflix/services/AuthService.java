package com.devsuperior.movieflix.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.ForbiddenException;
import com.devsuperior.movieflix.services.exceptions.UnauthorizedException;

@Service
public class AuthService {

	@Autowired
	private UserRepository repository;
	
	// Pegando usuário autenticado
	@Transactional(readOnly = true)
	public User authenticated() {
		try {	// Pega nome do usuário			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return repository.findByEmail(username);
		} catch (Exception e) {
			throw new UnauthorizedException("Usuário Inválido");
		}
	}
	
	public void validateSelfOrAdmin(Long userId) {
		User user = authenticated();
		if (!user.getId().equals(userId)) {
			throw new ForbiddenException("Acesso Negado !!");
		}
	}
}
