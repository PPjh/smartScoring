package org.example.smartScoring.smartScoring.midModel.myMessageQue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class AppConfig {
	@Bean
	public BlockingQueue<String> messageQueue() {
		return new LinkedBlockingQueue<>(10); // 设置队列大小
	}
}