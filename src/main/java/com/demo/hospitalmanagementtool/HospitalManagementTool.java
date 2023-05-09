package com.demo.hospitalmanagementtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })public class HospitalManagementTool {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementTool.class, args);
	}

}
