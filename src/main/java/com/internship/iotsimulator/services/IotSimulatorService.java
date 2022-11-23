package com.internship.iotsimulator.services;

import com.internship.iotsimulator.configs.ControlPanelProperties;
import com.internship.iotsimulator.dtos.BaseRequest;
import com.internship.iotsimulator.dtos.BaseResponse;
import com.internship.iotsimulator.dtos.MetricsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PreDestroy;
import java.util.*;


@Service
@Slf4j
public class IotSimulatorService {


    @Autowired
    private ControlPanelProperties controlPanelProperties;

    private static Map<Long, Timer> tasks = new HashMap<Long, Timer>();

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public BaseResponse startSession(BaseRequest baseRequest) {
        String url = String.format("%s/%s/%s", controlPanelProperties.getControllerPath(),
                baseRequest.getMachineId(), baseRequest.getDeviceId());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSessionId(sendPost(url, baseRequest).getSessionId());
        return baseResponse;
    }

    public void endSession(Long sessionId){
        BaseRequest baseRequest = new BaseRequest();
        String url = String.format("%s/%s", controlPanelProperties.getControllerPath(), sessionId);
        sendPost(url, baseRequest);
        stopSendingMetrics(sessionId);
    }

    public void sendMetrics(Long sessionId) {

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                Integer value = random.nextInt();
                MetricsRequest metricsRequest = new MetricsRequest(value);
                String url = String.format("%s/%s/%s", controlPanelProperties.getControllerPath(), sessionId, controlPanelProperties.getMetricsPath());
                sendPost(url, metricsRequest);
            }
        };
        timer.scheduleAtFixedRate(timerTask, 10000, 10000);
        tasks.put(sessionId, timer);
    }

    public void stopSendingMetrics(Long sessionId) {
        log.info("Metric send for session#{} stopped! ", sessionId);
        tasks.get(sessionId).cancel();
    }


    private BaseResponse sendPost(String url, BaseRequest body) {
        return getRestTemplate().postForObject(url, body, BaseResponse.class);
    }

    @PreDestroy
    public void stopAllSessionsFromSendingMetrics(){
        for (Map.Entry<Long,Timer> task : tasks.entrySet()){
            log.info("Metric send for session#{} stopped! ", task.getKey());
            tasks.get(task.getKey()).cancel();
        }
    }
}
