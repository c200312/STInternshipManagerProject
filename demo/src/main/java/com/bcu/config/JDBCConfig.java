package com.bcu.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("jdbc.properties")
public class JDBCConfig {

//	jdbc.driverClassName=com.mysql.cj.jdbc.Driver
//	 driverClassName = new Driver()
	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;
	
	@Bean
	public DataSource getDataSource(){
		  BoneCPDataSource bcpds=new BoneCPDataSource(); 
	      bcpds.setDriverClass(driverClassName); 
	      bcpds.setJdbcUrl(url); 
	      bcpds.setUsername(username); 
	      bcpds.setPassword(password); 
	      bcpds.setAcquireIncrement(1); 
	      bcpds.setMinConnectionsPerPartition(2); 
	      bcpds.setMaxConnectionsPerPartition(20); 
	      bcpds.setPartitionCount(2); 
		  return bcpds;
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
