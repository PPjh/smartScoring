package org.example.smartScoring.smartScoring.midModel.myMessageQue;

import lombok.Getter;
import org.example.smartScoring.smartScoring.Dao.ContextDao;
import org.example.smartScoring.smartScoring.Server.ContextList;
import org.example.smartScoring.smartScoring.bean.Context;
import org.example.smartScoring.smartScoring.model.bridge.InputAndOutPut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@Component
public class Consumer implements Runnable {
	@Autowired
	private BlockingQueue<String> messageQueue;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private ContextList contextList = ContextList.getContextList();
	@Autowired
	private ContextDao contextDao;

	/**
	 * 进行文件分析并获取识别文章及分析结果
	 *
	 * @param filePath
	 */
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

	@Override
	public void run() {
		try {
			while (true) {
				if (DataBaseStatus.hasData() && messageQueue.isEmpty()) {
					restoreMessagesFromDatabase();
				}
				String message = messageQueue.take(); // 阻塞直到队列有元素
				processFileInBackground(message);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 当队列为空时，将队列长度的数据插入到队列中，并更新数据库是否为空的状态
	 */
	private void restoreMessagesFromDatabase() {
		// 使用 ORDER BY 确保 id 小的排在前面
		String sql = "SELECT id, message FROM messageQueue ORDER BY id ASC LIMIT 10"; // 获取最多10条消息
		List<Message> messages = jdbcTemplate.query(sql, (rs, rowNum) ->
				new Message(rs.getInt("id"),
						rs.getString("message")));

		for (Message message : messages) {
			messageQueue.offer(message.getMessage());
		}

		// 从数据库中删除已恢复的消息
		for (Message message : messages) {
			deleteFromDatabase(message.getId());
		}

		// 更新数据库状态
		if (dataBaseIsEmpty()) DataBaseStatus.setHasData(false);
	}

	/**
	 * 判断MySQL数据库中表是否为空
	 * @return 如果为空返回true
	 */
	private boolean dataBaseIsEmpty() {
		String sql = "SELECT count(*) FROM messageQueue";
		return jdbcTemplate.queryForObject(sql, Integer.class) == 0;
	}

	/**
	 * 根据id删除数据
	 * @param id 消息主键
	 */
	private void deleteFromDatabase(int id) {
		String sql = "DELETE FROM messageQueue WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Getter
	private static class Message {
		private final int id;
		private final String message;

		public Message(int id, String message) {
			this.id = id;
			this.message = message;
		}

	}
}
