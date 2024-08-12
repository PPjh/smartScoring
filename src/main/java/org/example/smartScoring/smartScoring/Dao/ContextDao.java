package org.example.smartScoring.smartScoring.Dao;

import org.example.smartScoring.smartScoring.bean.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ContextDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Context> findAll() {
		String sql = "select * from context";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Context.class));
	}

	public Context findByName(String name) {
		String sql = "select * from context where fileName = ?";
		Object[] params = new Object[]{name};
		return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Context.class));
	}

	public boolean addContext(Context context) {
		String sql = "insert into context(filePath, fileName, result, article) values (?, ?, ?, ?)";
		Object[] params = {
				context.getFilePath(), context.getFileName(),
				context.getResult(), context.getArticle()
		};
		return jdbcTemplate.update(sql, params) > 0;
	}
}
