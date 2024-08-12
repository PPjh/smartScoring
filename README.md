# 项目简介

本项目通过文件上传的方式，实现对后缀为 `.wav` 的音频文件进行语音识别，并将识别到的文本内容进行大语言模型评分。
最后连接MySQL将{文件存储地址，文件名，语音识别文本，语言模型返回结果}存储到表smartScoring中

## 模型配置

请按照以下步骤进行配置：

1. 将 `resource` 目录中的 `example-properties` 文件修改为 `properties`。
2. 将目录下的 `javaModel.properties.example` 修改为 `javaModel.properties`，并更新为您自己的模型。

### 模型获取地址

- 千帆大语言模型： [https://qianfan.cloud.baidu.com/](https://qianfan.cloud.baidu.com/)
- Vosk语音识别模型： [https://alphacephei.com/vosk/models](https://alphacephei.com/vosk/models)
