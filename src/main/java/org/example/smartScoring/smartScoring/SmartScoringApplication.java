package org.example.smartScoring.smartScoring;

import org.example.smartScoring.smartScoring.midModel.myMessageQue.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartScoringApplication implements CommandLineRunner{
    @Autowired
    private Consumer consumer;

    public static void main(String[] args) {
        SpringApplication.run(SmartScoringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(consumer).start();
    }
}
