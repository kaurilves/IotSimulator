package com.internship.iotsimulator.services;

import com.internship.iotsimulator.configs.ControlPanelProperties;
import com.internship.iotsimulator.configs.DeviceProperties;
import com.internship.iotsimulator.dtos.BaseRequest;
import com.internship.iotsimulator.dtos.BaseResponse;
import com.internship.iotsimulator.dtos.MetricsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class IotSimulatorService {

    @Autowired
    private DeviceProperties deviceProperties;

    @Autowired
    private ControlPanelProperties controlPanelProperties;

    private static Long sessionId;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public void startSession(Long machineId, Long deviceId) {
        BaseRequest baseRequest = new BaseRequest(deviceProperties);
        String url = String.format("%s/%s/%s", controlPanelProperties.getControllerPath(),
                deviceProperties.getMachineId(),deviceProperties.getDeviceId());
        sessionId = sendPost(url, baseRequest).getSessionId();
    }

    public void endSession() {
        BaseRequest baseRequest = new BaseRequest(deviceProperties);
        String url = String.format("%s/%s", controlPanelProperties.getControllerPath(), sessionId);
        sendPost(url,baseRequest);
        sessionId = null;
    }

    public void sendMetrics(int stats) {
        MetricsRequest request = new MetricsRequest(deviceProperties, stats);
        String url = String.format("%s/%s/%s", controlPanelProperties.getControllerPath(), sessionId, controlPanelProperties.getMetricsPath());
        log.info("SENDING INFO {} {} {}", stats, request, url);
        sendPost(url,request);
    }

    private BaseResponse sendPost(String url, BaseRequest body) {
        return getRestTemplate().postForObject(url, body, BaseResponse.class);
    }
}
