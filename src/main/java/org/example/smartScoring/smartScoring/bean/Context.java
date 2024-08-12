package org.example.smartScoring.smartScoring.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //包含get set toString
@AllArgsConstructor //有参构造器set
@NoArgsConstructor //无参构造器get
public class Context {
	private String filePath; // 文件路径
	private String fileName; // 文件名
	private String result;    // 处理结果
	private String article;   // 文章内容或其他相关信息
}

/*public class Context {
	private String filePath; // 文件路径
	private String fileName; // 文件名
	private String result;    // 处理结果
	private String article;   // 文章内容或其他相关信息

	// 私有构造函数，确保只能通过 Builder 创建实例
	public Context(Builder builder) {
		this.filePath = builder.filePath;
		this.fileName = builder.fileName;
		this.result = builder.result;
		this.article = builder.article;
	}

	public Context() {
	}


	// 静态 Builder 类
	public static class Builder {
		private String filePath;
		private String fileName;
		private String result;
		private String article;

		// 设置 filePath
		public Builder filePath(String filePath) {
			this.filePath = filePath;
			return this;
		}

		// 设置 fileName
		public Builder fileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		// 设置 result
		public Builder result(String result) {
			this.result = result;
			return this;
		}

		// 设置 article
		public Builder article(String article) {
			this.article = article;
			return this;
		}

		// 构建 Context 实例
		public Context build() {
			return new Context(this);
		}
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	// Getter 方法
	public String getFilePath() {
		return filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public String getResult() {
		return result;
	}

	public String getArticle() {
		return article;
	}

}*/
