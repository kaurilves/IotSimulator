package com.internship.iotsimulator.api;

import com.internship.iotsimulator.dtos.BaseRequest;
import com.internship.iotsimulator.dtos.BaseResponse;
import com.internship.iotsimulator.services.IotSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iotsimulator")
public class IotSimulatorController {

    @Autowired
    private IotSimulatorService iotSimulatorService;


    @PostMapping("/starts_session/{machineId}/{deviceId}")
    public ResponseEntity<BaseResponse> startSession(@RequestBody BaseRequest baseRequest) {
        return ResponseEntity.ok().body(iotSimulatorService.startSession(baseRequest));
    }

    @PostMapping("/end_session/{sessionId}")
    public ResponseEntity<?> endSession(Long sessionId) {
        iotSimulatorService.endSession(sessionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send_metrics/{sessionId}")
    public ResponseEntity<?> sendMetrics(Long sessionId) {
        iotSimulatorService.sendMetrics(sessionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/stop_send_metrics/{sessionId}")
    public ResponseEntity<?> stopSendMetrics(Long sessionId) throws Exception {
        iotSimulatorService.stopSendingMetrics(sessionId);
        return ResponseEntity.ok().build();
    }
}
