package br.com.gabrielteles.interestguesser.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabrielteles.interestguesser.domain.UserRepository;
import br.com.gabrielteles.interestguesser.domain.model.User;

@Service
public class AuthenticationApplicationService {
	@Autowired
	UserRepository userRepository;
	
	public void fake() {
		User user = new User("gab.teles@hotmail.com", "123456");
		
		userRepository.save(user);
	}
}
