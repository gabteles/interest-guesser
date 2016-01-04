package br.com.gabrielteles.interestguesser.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@EnableRedisHttpSession
public class SessionConfiguration {
	
	@Autowired
	private Environment env;
	
	@Bean
	public RedisConnectionFactory connectionFactory() {
		JedisConnectionFactory jcf = new JedisConnectionFactory();
		jcf.setHostName(env.getProperty("redis.host"));
		jcf.setPort(env.getProperty("redis.port", Integer.class));
		return jcf;
	}
	
	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}
}
