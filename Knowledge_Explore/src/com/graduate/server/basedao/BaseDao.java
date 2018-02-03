package com.graduate.server.basedao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

@SuppressWarnings("all")
public class BaseDao extends JdbcDaoSupport{
	@Resource(name = "jdbcTemplate")
	protected void setSuperSessionFactory(JdbcTemplate  jdbctemplate ) {
		super.setJdbcTemplate(jdbctemplate);
	}
}
