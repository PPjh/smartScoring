package org.example.smartScoring.smartScoring.model.bridge;

import org.example.smartScoring.smartScoring.model.speechRecognition.VoiceUtil;
import org.example.smartScoring.smartScoring.model.Scoring.LongConversation;
import ws.schild.jave.EncoderException;

import java.io.IOException;

/**
 * @author pjh
 * @version 1.0
 * @description: 输入语音文本智能评分后输出结果
 * @date 2024/6/22 下午10:14
 */

public class InputAndOutPut {
	private String inputFile;
	private String article;
	private String result;


	/**
	 * 输入音频文件并调用run方法
	 *
	 * @param inputFile 音频文件
	 * @throws EncoderException
	 * @throws IOException
	 */
	public InputAndOutPut(String inputFile) throws EncoderException, IOException {
		this.inputFile = inputFile;
		run();
	}

	/**
	 * 连接语音识别与智能评分模块
	 *
	 * @throws EncoderException
	 * @throws IOException
	 */
	public void run() throws EncoderException, IOException {
		this.article = VoiceUtil.ASR(inputFile);
		this.result = LongConversation.scoring(article);
	}

	public String getArticle() {
		return article;
	}

	public String getResult() {
		return result;
	}

	public static void main(String[] args) throws EncoderException, IOException {
		String inputFile = "C:\\Users\\pjh\\Desktop\\20240617_185426.wav";
		String article = VoiceUtil.ASR(inputFile);
		System.out.println(article);
		String result = LongConversation.scoring(article);
		System.out.println(result);

		for (int i = 0; i < 10; i++) {
			String userId = String.valueOf(i);
			String userName = String.valueOf('a' + i);
		}
	}
}
