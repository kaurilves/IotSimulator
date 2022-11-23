package com.internship.iotsimulator.api;

import com.internship.iotsimulator.dtos.BaseRequest;
import com.internship.iotsimulator.dtos.BaseResponse;
import com.internship.iotsimulator.dtos.MetricsRequest;
import com.internship.iotsimulator.services.IotSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iotsimulator")
public class IotSimulatorController {

    @Autowired
    private IotSimulatorService iotSimulatorService;

    @PostMapping("/{machineId}/{deviceId}")
    public ResponseEntity<BaseResponse> startSession(Long machineId, Long deviceId) {
        iotSimulatorService.startSession(machineId, deviceId);
       return
    }

    public void endSession(Long sessionId) {
        BaseRequest baseRequest = new BaseRequest();
        String url = String.format("%s/%s", controlPanelProperties.getControllerPath(), sessionId);
        sendPost(url,baseRequest);
        sessionId = null;
    }

    public void sendMetrics(Integer value) {
        MetricsRequest request = new MetricsRequest(deviceProperties, stats);
        String url = String.format("%s/%s/%s", controlPanelProperties.getControllerPath(), sessionId, controlPanelProperties.getMetricsPath());
        log.info("SENDING INFO {} {} {}", value, request, url);
        sendPost(url,request);
    }

}
