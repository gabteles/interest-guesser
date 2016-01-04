package br.com.gabrielteles.interestguesser.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class PersistenceConfiguration {

	@Autowired
	private Environment env;
	
	@Bean
	public CassandraClusterFactoryBean cluster() {
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(env.getProperty("cassandra.contactpoints"));
		cluster.setPort(env.getProperty("cassandra.port", Integer.class));
		
		return cluster;
	}
	
	@Bean
	public CassandraMappingContext mappingContext() {
		return new BasicCassandraMappingContext();
	}
	
	@Bean
	public CassandraConverter converter() {
		return new MappingCassandraConverter(this.mappingContext());
	}
	
	@Bean
	public CassandraSessionFactoryBean session() throws Exception {
		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(this.cluster().getObject());
		session.setKeyspaceName(env.getProperty("cassandra.keyspace"));
		session.setConverter(this.converter());
		session.setSchemaAction(SchemaAction.NONE);
		
		return session;
	}
	
	@Bean
	public CassandraOperations cassandraTemplate() throws Exception {
		return new CassandraTemplate(this.session().getObject());
	}
}
