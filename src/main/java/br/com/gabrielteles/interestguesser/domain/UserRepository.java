package br.com.gabrielteles.interestguesser.domain;

import br.com.gabrielteles.interestguesser.domain.model.User;

public interface UserRepository {

	User findOneByEmail(String email);

	User save(User user);

}
