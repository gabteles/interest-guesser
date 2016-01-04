package br.com.gabrielteles.interestguesser.infra.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

import br.com.gabrielteles.interestguesser.domain.UserRepository;
import br.com.gabrielteles.interestguesser.domain.model.User;

@Repository
public class CassandraUserRepository implements UserRepository {
	@Autowired
	private CassandraOperations operations;

	public User findOneByEmail(String email) {
		Select select = QueryBuilder.select().from("user");
		select.where(QueryBuilder.eq("email", email));
		return this.operations.selectOne(select, User.class);
	}

	@Override
	public User save(User user) {
		return this.operations.insert(user);
	}

}
