package com.internship.iotsimulator.api;

import com.internship.iotsimulator.dto.BaseRequest;
import com.internship.iotsimulator.dto.BaseResponse;
import com.internship.iotsimulator.service.IotSimulatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseEntity<BaseResponse> endSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok().body(iotSimulatorService.endSession(sessionId));
    }

    @PostMapping("/send_metrics/{sessionId}/{intervalInSeconds}")
    public ResponseEntity<BaseResponse> sendMetrics(@PathVariable Long sessionId,  @PathVariable Integer intervalInSeconds) {
        return ResponseEntity.ok().body(iotSimulatorService.sendMetrics(sessionId, intervalInSeconds));
    }

    @PostMapping("/stop_send_metrics/{sessionId}")
    public ResponseEntity<BaseResponse> stopSendMetrics(Long sessionId) throws Exception {
        return ResponseEntity.ok().body(iotSimulatorService.stopSendingMetrics(sessionId));

    }
}
