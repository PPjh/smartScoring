package org.example.smartScoring.smartScoring.model.speechRecognition;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;

public class Jave2Util {

	/**
	 * @param src      来源文件路径
	 * @param target   目标文件路径
	 * @param offset   设置起始偏移量(秒)
	 * @param duration 设置切片的音频长度(秒)
	 * @throws EncoderException
	 */
	public static void cut(String src, String target, Float offset, Float duration) throws EncoderException {

		File targetFile = new File(target);
		if (targetFile.exists()) {
			targetFile.delete();
		}

		File srcFile = new File(src);
		MultimediaObject srcMultiObj = new MultimediaObject(srcFile);
		MultimediaInfo srcMediaInfo = srcMultiObj.getInfo();

		Encoder encoder = new Encoder();

		EncodingAttributes encodingAttributes = new EncodingAttributes();
		//设置起始偏移量(秒)
		encodingAttributes.setOffset(offset);
		//设置切片的音频长度(秒)
		encodingAttributes.setDuration(duration);
		// 输入格式
		encodingAttributes.setInputFormat("wav");

		//设置音频属性
		AudioAttributes audio = new AudioAttributes();
		audio.setBitRate(srcMediaInfo.getAudio().getBitRate());
		//audio.setSamplingRate(srcMediaInfo.getAudio().getSamplingRate());
		// 转换为16KHZ 满足vosk识别的标准
		audio.setSamplingRate(16000);
		audio.setChannels(srcMediaInfo.getAudio().getChannels());
		//如果截取的时候，希望同步调整编码，可以设置不同的编码
		encodingAttributes.setAudioAttributes(audio);
		//写文件
		encoder.encode(srcMultiObj, new File(target), encodingAttributes);
	}

	public static void main(String[] args) throws EncoderException {

	}

}

