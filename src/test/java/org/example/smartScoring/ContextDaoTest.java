package org.example.smartScoring;

import org.example.smartScoring.smartScoring.Dao.ContextDao;
import org.example.smartScoring.smartScoring.SmartScoringApplication;
import org.example.smartScoring.smartScoring.bean.Context;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SmartScoringApplication.class)
@Transactional // 确保每个测试后回滚事务，保持数据一致性
@Rollback // 测试完成后回滚所有操作
public class ContextDaoTest {

	@Autowired
	private ContextDao contextDao;

	@Test
	public void testAddContext() {
		Context context = new Context("path/to/file", "你好", "result", "article");
		boolean result = contextDao.addContext(context);
		assertTrue(result); // 验证添加是否成功
	}

	@Test
	public void testFindAll() {
		List<Context> contexts = contextDao.findAll();
		assertNotNull(contexts); // 验证返回的列表不为空
	}

	@Test
	public void testFindByName() {
		// 先插入一个测试数据
		contextDao.addContext(new Context("path/to/file", "testFile", "result", "article"));

		Context context = contextDao.findByName("testFile");
		assertNotNull(context); // 验证根据名称查找返回的结果不为空
		assertEquals("testFile", context.getFileName()); // 验证文件名是否匹配
	}
}
