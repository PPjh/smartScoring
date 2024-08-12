package org.example.smartScoring.smartScoring.model.Scoring;

import java.io.IOException;
import java.util.Scanner;

/**
 * 实现连续对话，当输入exit时结束对话
 */
public class LongConversation {

	public static void main(String[] args) throws IOException {
		chartWithAi c = new chartWithAi();
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入需要询问的内容:");
		while (true) {
			String input = sc.nextLine();
			if (input.equals("exit")) break;
			System.out.println(c.Chart(input));
		}
	}

	/**
	 * 获取评分
	 * @param input 等待评分的文本
	 * @return 大语言模型的评分及评价
	 * @throws IOException
	 */
	public static String scoring(String input) throws IOException {
		chartWithAi c = new chartWithAi();
		String head = "我将输入一段文章给你，你按文章的深度及创新程度给我一个100分制的评分，并以: 评分 + 原因的格式返回给我";
		return c.Chart(head + input);
	}
}