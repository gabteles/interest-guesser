package br.com.gabrielteles.interestguesser.infra;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import br.com.gabrielteles.interestguesser.util.Profiles;
import redis.embedded.RedisServer;

@Configuration
@Profile(Profiles.EMBED_REDIS)
public class InMemoryDatabaseConfiguration {
	@Autowired
	Environment env;
	
	@Autowired
	RedisServer server;
	
	@PostConstruct
	public void init() {
		this.server.start();
	}
	
	@PreDestroy
	public void destroy() {
		this.server.stop();
	}
	
	@Bean
	public RedisServer redisServer() {
		return RedisServer.builder()
			.setting("bind " + env.getProperty("redis.host"))
			.port(env.getProperty("redis.port", Integer.class))
			.build();
	}
}
