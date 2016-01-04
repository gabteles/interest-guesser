package br.com.gabrielteles.interestguesser.infra;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import br.com.gabrielteles.interestguesser.util.KafkaBrokerPool;
import br.com.gabrielteles.interestguesser.util.Profiles;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;

@Configuration
@Profile(Profiles.EMBED_KAFKA)
public class MessageBrokerConfiguration {
	@Autowired
	Environment env;
	
	@Autowired
	ZooKeeperLocal server;
	
	@Autowired
	KafkaBrokerPool brokers;
	
	@PostConstruct
	public void init() throws IOException, InterruptedException {
		this.server.start();
		this.brokers.stream().forEach(broker -> broker.startup());
	}
	
	@PreDestroy
	public void destroy() throws IOException {
		brokers.stream().forEach(broker -> { broker.shutdown() ; broker.awaitShutdown(); });
		server.shutdown();
	}
	
	@Bean
	public ZooKeeperLocal zookeeperServer() throws IOException {
		File snapshotDir = this.constructDirectory(env.getProperty("zookeeper.snapshotdir"));
		File logDir = this.constructDirectory(env.getProperty("zookeeper.logdir"));
		
		Properties props = new Properties();
		props.setProperty("dataDir", snapshotDir.getAbsolutePath());
		props.setProperty("dataLogDir", logDir.getAbsolutePath());
		props.setProperty("clientPort", env.getProperty("zookeeper.port"));
		props.setProperty("tickTime", env.getProperty("zookeeper.tickTime"));
		props.setProperty("maxClientCnxns", "32");
		return new ZooKeeperLocal(props);
	}
	
	@Bean
	public KafkaBrokerPool kafkaServerPool() {
		KafkaBrokerPool brokers = new KafkaBrokerPool();
		
		String[] brokersAddresses = env.getProperty("kafka.brokers.addresses").split(",");
		int counter = 1;
		
		for (String brokerAddress : brokersAddresses) {
			String[] addr = brokerAddress.split(":");
			
			String host = addr[0];
			String port = addr[1];
			
			String logdir = new File(env.getProperty("kafka.logdir"), String.valueOf(counter)).getAbsolutePath();
			this.constructDirectory(logdir);
			
			Properties props = new Properties();
			props.put("zookeeper.connect", env.getProperty("zookeeper.host") + ":" + env.getProperty("zookeeper.port"));
			props.put("log.flush.interval.messages", "1");
			props.put("zookeeper.session.timeout.ms", "10000");
			props.setProperty("broker.id", String.valueOf(counter++));
			props.setProperty("host.name", host);
            props.setProperty("port", port);
            props.setProperty("log.dir", logdir);
            
            KafkaServerStartable server = new KafkaServerStartable(new KafkaConfig(props));
            
            brokers.add(server);
		}
		
		return brokers;
	}
	
	private File constructDirectory(String path) {
		File directory = new File(path);
		
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		return directory;
	}
}
