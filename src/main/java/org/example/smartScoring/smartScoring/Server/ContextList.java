package org.example.smartScoring.smartScoring.Server;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContextList {
	private final List<String> leftText = new ArrayList<>();
	private final List<String> rightText = new ArrayList<>();
	private static ContextList contextList = null;

	// 私有构造函数
	private ContextList() {
	}

	// 懒加载单例模式
	public static synchronized ContextList getContextList() {
		if (contextList == null) {
			contextList = new ContextList();
		}
		return contextList;
	}

	public synchronized void addText(String leftS, String  rightS) {
		this.leftText.add(leftS);
		this.rightText.add(rightS);
		System.out.println(rightS + " " + leftS + "\n被添加到输出栏中");
	}

	public List<String> getRightText() {
		return new ArrayList<>(rightText); // 返回副本以保护原始数据
	}

	public List<String> getLeftText() {
		return new ArrayList<>(leftText); // 返回副本以保护原始数据
	}
}

