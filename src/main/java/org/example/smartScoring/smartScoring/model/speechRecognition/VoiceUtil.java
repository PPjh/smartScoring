package org.example.smartScoring.smartScoring.model.speechRecognition;

import org.springframework.stereotype.Component;
import org.vosk.Model;
import org.vosk.Recognizer;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.AudioInfo;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class VoiceUtil {

    public static void main(String[] args) throws EncoderException {
        String wavFilePath = "C:\\Users\\pjh\\Desktop\\20240617_185426.wav";
        // 秒
        long cutDuration = 20;
        String waveForm = acceptWaveForm(wavFilePath, cutDuration);
        System.out.println(waveForm);
    }

	public static String ASR(String inputFile) throws EncoderException {
		return acceptWaveForm(inputFile, 20);
	}

/**
     * 对Wav格式音频文件进行语音识别翻译
     *
     * @param wavFilePath
     * @param cutDuration
     * @return
     * @throws EncoderException
     */

    private static String acceptWaveForm(String wavFilePath, long cutDuration) throws EncoderException {
        // 判断视频的长度
        long startTime = System.currentTimeMillis();
        MultimediaObject multimediaObject = new MultimediaObject(new File(wavFilePath));
        MultimediaInfo info = multimediaObject.getInfo();
        // 时长/毫秒
        long duration = info.getDuration();
        AudioInfo audio = info.getAudio();
        // 通道数
        int channels = audio.getChannels();
        // 秒
        // 获取切片个数
        long offset = 0;
        long forNum = (duration / 1000) / cutDuration;
        if (duration % (cutDuration * 1000) > 0) {
            forNum = forNum + 1;
        }
        // 进行切块处理
        List<String> strings = cutWavFile(wavFilePath, cutDuration, offset, forNum);
        // 循环进行翻译
        StringBuilder result = new StringBuilder();
        for (String string : strings) {
            File f = new File(string);
            result.append(VoiceUtil.getRecognizerResult(f, channels));
        }
        long endTime = System.currentTimeMillis();
        String msg = "耗时：" + (endTime - startTime) + "ms";
        System.out.println(msg);
        return result.toString();
    }

/**
     * 对wav进行切块处理
     *
     * @param wavFilePath 处理的wav文件路径
     * @param cutDuration 切割的固定长度/秒
     * @param offset      设置起始偏移量(秒)
     * @param forNum      切块的次数
     * @return
     * @throws EncoderException
     */

    private static List<String> cutWavFile(String wavFilePath, long cutDuration, long offset, long forNum)
            throws EncoderException {
        UUID uuid = UUID.randomUUID();
        // 大文件切割为固定时长的小文件
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < forNum; i++) {
            //windows
//            String target = "C:\\Users\\pjh\\Desktop\\temp\\" + uuid + "\\" + i + ".wav";
            //linux
            String target = "/tmp/" + uuid + "/" + i + ".wav";
            Float offsetF = Float.valueOf(String.valueOf(offset));
            Float cutDurationF = Float.valueOf(String.valueOf(cutDuration));
            Jave2Util.cut(wavFilePath, target, offsetF, cutDurationF);
            offset = offset + cutDuration;
            strings.add(target);
        }
        return strings;
    }


/**
     * 进行翻译
     *
     * @param f
     * @param channels
     */

    public static String getRecognizerResult(File f, int channels) {
        StringBuilder result = new StringBuilder();
        Model voskModel = VoskModel.getInstance().getVoskModel();
        // 采样率为音频采样率的声道倍数
        System.out.println("加载完毕，开始分析");
        try (
                Recognizer recognizer = new Recognizer(voskModel, 16000 * channels);
                InputStream ais = Files.newInputStream(f.toPath())
        ) {
            int nbytes;
            byte[] b = new byte[4096];
            while ((nbytes = ais.read(b)) >= 0) {
                if (recognizer.acceptWaveForm(b, nbytes)) {
                    // 返回语音识别结果
                    result.append(getResult(recognizer.getResult()));
                }
            }
            // 返回语音识别结果。和结果一样，但不要等待沉默。
            // 你通常在流的最后调用它来获得音频的最后部分。它刷新功能管道，以便处理所有剩余的音频块。
            result.append(getResult(recognizer.getFinalResult()));
            System.out.println("识别结果：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


/**
     * 获取返回结果
     *
     * @param result
     * @return
     */

    private static String getResult(String result) {
        VoskResult voskResult = JacksonMapperUtils.json2pojo(result, VoskResult.class);
        return Optional.ofNullable(voskResult).map(VoskResult::getText).orElse("");
    }

}

