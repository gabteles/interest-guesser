package br.com.gabrielteles.interestguesser.messaging;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MessagingConfiguration {
	@Autowired
	Environment env;
	
	@Bean
	public Producer<String, String> kafkaProducer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", env.getProperty("kafka.bootstrap.servers"));
		props.put("acks", "1");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		return new KafkaProducer<>(props);
	}
}
