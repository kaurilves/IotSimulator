package com.internship.iotsimulator.api;

import com.internship.iotsimulator.dto.DeviceConnectionRequest;
import com.internship.iotsimulator.dto.DeviceConnectionResponse;
import com.internship.iotsimulator.service.IotSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/iotsimulator")
public class IotSimulatorController {

    @Autowired
    private IotSimulatorService iotSimulatorService;

    @PostMapping("/starts_session/{machineId}/{deviceId}")
    public ResponseEntity<DeviceConnectionResponse> startSession(@RequestBody DeviceConnectionRequest deviceConnectionRequest) {
        return ResponseEntity.ok().body(iotSimulatorService.startSession(deviceConnectionRequest));
    }

    @PostMapping("/end_session/{sessionId}")
    public ResponseEntity<DeviceConnectionResponse> endSession(@PathVariable Long sessionId) {
        iotSimulatorService.endSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/send_metrics/{sessionId}/{intervalInSeconds}")
    public ResponseEntity<DeviceConnectionResponse> sendMetrics(@PathVariable Long sessionId, @PathVariable Long intervalInSeconds) {
        iotSimulatorService.sendMetrics(sessionId, intervalInSeconds);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/stop_send_metrics/{sessionId}")
    public ResponseEntity<DeviceConnectionResponse> stopSendMetrics(Long sessionId) throws Exception {
        iotSimulatorService.stopSendingMetrics(sessionId);
        return ResponseEntity.noContent().build();
    }
}
