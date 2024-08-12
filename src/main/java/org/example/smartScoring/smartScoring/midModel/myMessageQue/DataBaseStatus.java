package org.example.smartScoring.smartScoring.midModel.myMessageQue;

public class DataBaseStatus {
	private static volatile boolean havaData = false; // 保证线程安全

	public static boolean hasData() {
		return havaData;
	}

	public static synchronized void setHasData(boolean hasData) { // 使用同步方法
		havaData = hasData;
	}
}
