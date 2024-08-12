package org.example.smartScoring;

import org.example.smartScoring.smartScoring.SmartScoringApplication;
import org.example.smartScoring.smartScoring.model.Scoring.chartWithAi;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Scanner;

@SpringBootTest(classes = SmartScoringApplication.class)
@Transactional // 确保每个测试后回滚事务，保持数据一致性
@Rollback // 测试完成后回滚所有操作
public class LongConversationTest {
	@Test
	public void M() throws IOException {
		chartWithAi c = new chartWithAi();
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入需要询问的内容:");
		while (true) {
			String input = sc.nextLine();
			if (input.equals("exit")) break;
			System.out.println(c.Chart(input));
		}
	}
}
