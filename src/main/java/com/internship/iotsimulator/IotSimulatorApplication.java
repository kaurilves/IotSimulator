package com.internship.iotsimulator;

import com.internship.iotsimulator.service.IotSimulatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IotSimulatorApplication {


	public static void main(String[] args) {
		SpringApplication.run(IotSimulatorApplication.class, args);

		IotSimulatorService iotSimulatorService1 = new IotSimulatorService();
	}
}
