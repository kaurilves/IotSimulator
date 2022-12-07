package com.internship.iotsimulator.config;


import com.internship.iotsimulator.dto.MetricsRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@Slf4j
@Data
public class MetricsSimulator implements Runnable {

    private Boolean isActive = true;
    private Long sessionId;
    private Long intervalInSeconds;

    @Autowired
    private ControlPanelProperties controlPanelProperties;

    @Autowired
    private ConnectionConfig connectionConfig;


    @Override
    public void run() {
        Random random = new Random();
        Integer value = random.nextInt();
        MetricsRequest metricsRequest = new MetricsRequest(value);
        String url = String.format("%s/%s/%s", controlPanelProperties.getControllerPath(), sessionId, controlPanelProperties.getMetricsPath());
        connectionConfig.getRestTemplate().postForObject(url, metricsRequest, Object.class);
        log.info("sending metrics in session# {}", sessionId);
    }

        /* public void run() {
        for (int i = 1; i > 0; i++) {
            try {
                Thread.sleep(intervalInSeconds);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Random random = new Random();
            Integer value = random.nextInt();
            MetricsRequest metricsRequest = new MetricsRequest(value);
            String url = String.format("%s/%s/%s", controlPanelProperties.getControllerPath(), sessionId, controlPanelProperties.getMetricsPath());
            connectionConfig.getRestTemplate().postForObject(url, metricsRequest, Object.class);
            log.info("sending metrics in session# {}", sessionId);
        }
    }
    */


}
