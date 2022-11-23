package com.internship.iotsimulator.configs;

import com.internship.iotsimulator.services.IotSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

@Configuration
@EnableScheduling
public class MetricsScheduler {

    @Autowired
    private IotSimulatorService iotSimulatorService;

    @Scheduled(cron = "*/10 * * * * *")
    public void sendData(){
        Random random = new Random();
        int stats = random.nextInt();
        iotSimulatorService.sendMetrics(stats);
    }



}
