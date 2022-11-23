package com.internship.iotsimulator;

import com.internship.iotsimulator.services.IotSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class IotSimulatorApplication {

	@Autowired
	private IotSimulatorService service;
	public static void main(String[] args) {
		SpringApplication.run(IotSimulatorApplication.class, args);
	}

}
