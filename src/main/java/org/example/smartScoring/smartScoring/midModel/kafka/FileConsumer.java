/*
package org.example.smartScoring.smartScoring.midModel.kafka;

import org.example.smartScoring.smartScoring.Dao.ContextDao;
import org.example.smartScoring.smartScoring.Server.ContextList;
import org.example.smartScoring.smartScoring.bean.Context;
import org.example.smartScoring.smartScoring.model.bridge.InputAndOutPut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FileConsumer {
	private ContextList contextList = ContextList.getContextList();
	@Autowired
	private ContextDao contextDao;

	*/
/**
	 * 进行文件分析并获取识别文章及分析结果
	 *
	 * @param filePath
	 *//*

	public void processFileInBackground(String filePath) {
		try {
			String fileName = filePath
					.substring(filePath.lastIndexOf("/") + 1);
			fileName = fileName.substring(fileName.indexOf("_") + 1);
			// 读取文件并进行分析
			InputAndOutPut inputAndOutPut = new InputAndOutPut(filePath);
			String article = inputAndOutPut.getArticle();
			String result = inputAndOutPut.getResult();
			Context context = new Context(
					filePath,fileName,result,article
			);
			contextDao.addContext(context);
			// 将分析结果保存到共享数据结构或数据库
			contextList.addText(article, result);
			System.out.println("完成输出");
		} catch (Exception e) {
			// 处理异常
			e.printStackTrace();
		}
	}

	@KafkaListener(topics = "file-upload", groupId = "file-group")
	public void consume(String filePathS) {
		processFileInBackground(filePathS);
	}
}*/
