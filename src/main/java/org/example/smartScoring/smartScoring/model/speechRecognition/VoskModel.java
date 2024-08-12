package org.example.smartScoring.smartScoring.model.speechRecognition;

import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * vosk模型加载
 * @author zhou
 */
public class VoskModel {

    /**
     * 3. 使用 volatile 保证线程安全
     * 禁止指令重排
     * 保证可见性
     * 不保证原子性
     */
    private static volatile VoskModel instance;

    private static final ResourceBundle bundle = ResourceBundle.getBundle("properties.javaModel");

    private Model voskModel;

    public Model getVoskModel() {
        return voskModel;
    }

    /**
     * 1.私有构造函数
     */
    private VoskModel() {
        System.out.println("SingleLazyPattern实例化了");

         String modelStr = bundle.getString("VOSK_MODEL_PATH");
        try {
            voskModel = new Model(modelStr);
            LibVosk.setLogLevel(LogLevel.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2.通过静态方法获取一个唯一实例
     * DCL 双重检查锁定 （Double-CheckedLocking）
     * 在多线程情况下保持⾼性能
     */
    public static VoskModel getInstance() {
        if (instance == null) {
            synchronized (VoskModel.class) {
                if (instance == null) {
                    // 1. 分配内存空间 2、执行构造方法，初始化对象 3、把这个对象指向这个空间
                    instance = new VoskModel();
                }
            }
        }
        return instance;
    }

    /**
     * 多线程测试加载
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(VoskModel::getInstance).start();
        }
    }
}

