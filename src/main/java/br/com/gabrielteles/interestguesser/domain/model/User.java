package br.com.gabrielteles.interestguesser.domain.model;

import java.util.UUID;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table("user")
public class User {
	
	@PrimaryKey
	@Column("userid")
	private String userid;
	
	@Column("email")
	private String email;
	
	@Column("password")
	private String password;
	
	public User() {
		
	}
	
	public User(final String email, final String password) {
		this.userid = UUID.randomUUID().toString();
		this.email = email;
		this.password = password;
	}
	
	public User(final String userid, String email, String password) {
		this(email, password);
		this.userid = UUID.fromString(userid).toString();
	}

	public String getUserId() {
		return userid;
	}


	public String getEmail() {
		return email;
	}


	public String getPassword() {
		return password;
	}
}
