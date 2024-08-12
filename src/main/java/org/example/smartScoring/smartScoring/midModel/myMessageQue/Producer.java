package org.example.smartScoring.smartScoring.midModel.myMessageQue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
public class Producer {
	@Autowired
	private BlockingQueue<String> messageQueue;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void produce(String message) throws InterruptedException {
		int remain = messageQueue.remainingCapacity();
		if(remain > 0 && !DataBaseStatus.hasData()){
			messageQueue.put(message);
			System.out.println("Produced: " + message);
		}else{
			writeToDatabase(message);
			DataBaseStatus.setHasData(true);
			System.out.println("Queue full. Wrote to database: " + message);
		}
	}

	private void writeToDatabase(String message) {
		String sql = "INSERT INTO messageQueue (message) VALUES (?)";
		jdbcTemplate.update(sql, message);
	}
}
