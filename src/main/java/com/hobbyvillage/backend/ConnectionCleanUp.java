package com.hobbyvillage.backend;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
public class ConnectionCleanUp {

	@Autowired
	private DataSource dataSource;

	@EventListener
	public void connectionCleanUp(ContextClosedEvent event) {
		if (dataSource instanceof HikariDataSource) {
			((HikariDataSource) dataSource).close();
		}
	}

}
