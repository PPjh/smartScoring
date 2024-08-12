package org.example.smartScoring.smartScoring.controller;

/**
 * @author pjh
 * @version 1.0
 * @description: TODO
 * @date 2024/6/22 下午10:51
 */


import org.example.smartScoring.smartScoring.Server.ContextList;
/*import org.example.smartScoring.smartScoring.midModel.kafka.FileProducer;*/
import org.example.smartScoring.smartScoring.midModel.myMessageQue.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class MyController {
/*	@Autowired
	FileProducer fileProducer = new FileProducer();*/
	@Autowired
	Producer producer;


	//向文件上传页面跳转
	@GetMapping("/toUpload")
	public String toUpload() {
		return "upload";
	}

	//文件上传管理
	@PostMapping("/uploadFile")
	public String uploadFile(MultipartFile[] fileUpload, Model model) throws IOException, InterruptedException {
		//默认文件上传成功，并返回状态信息
		model.addAttribute("uploadStatus", "上传成功！");
		for (MultipartFile file : fileUpload) {
			//获取文件名及后缀名
			String fileName = file.getOriginalFilename();
			//重新生成文件名（根据具体情况生成对应文件名）
			//指定上传文件本地存储目录，不存在则需要提前创建
			// windows
			String dirPath = "E:/file/";
			// linux
//			String dirPath = "/root/smartScoring/";
			File filePath = new File(dirPath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			String filePathStr = dirPath + UUID.randomUUID() + "_" + fileName;
			file.transferTo(new File(filePathStr));
			// 发送文件路径到 Kafka
//			fileProducer.sendFilePath(filePathStr);
			// 发送给自定义消息队列
			producer.produce(filePathStr);
		}
		//携带上传状态信息回调到文件上传页面
		return "upload";
	}


	@GetMapping("/toShowScore")
	public String toShow() {
		return "showScore";
	}

	@GetMapping("/show")
	@ResponseBody
	public Map<String, List<String>> getTexts() throws EncoderException {
		ContextList contextList = ContextList.getContextList();
		Map<String, List<String>> response = new HashMap<>();
		response.put("leftTexts", contextList.getLeftText());
		response.put("rightTexts", contextList.getRightText());
		return response;
	}
}