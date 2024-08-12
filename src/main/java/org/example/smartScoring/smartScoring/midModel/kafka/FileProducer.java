/*
package org.example.smartScoring.smartScoring.midModel.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileProducer {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private static final String TOPIC = "file-upload";

	public void sendFilePath(String filePath) {
		kafkaTemplate.send(TOPIC, filePath);
	}
}*/
