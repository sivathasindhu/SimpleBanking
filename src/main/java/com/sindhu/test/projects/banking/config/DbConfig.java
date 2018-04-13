package com.sindhu.test.projects.banking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DbConfig {
	
	@Bean(name="jdbcTemplate")
	public JdbcTemplate getJdbcTemplate() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL)
				.addScript("db/sql/ddl.sql")
				.addScript("db/sql/dml.sql")
				.build();
		return new JdbcTemplate(db);
	}

//	@Bean(value="mySQLJdbcTemplate")
//    public JdbcTemplate mySQLdataSource()
//    {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/myDatabaseName");
//        dataSource.setUsername("sindhu");
//        dataSource.setPassword("password");
//        return new JdbcTemplate(dataSource);
//    } 
}

