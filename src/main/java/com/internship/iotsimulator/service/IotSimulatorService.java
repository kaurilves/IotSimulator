package com.internship.iotsimulator.service;

import com.internship.iotsimulator.config.AsyncConfiguration;
import com.internship.iotsimulator.config.ConnectionConfig;
import com.internship.iotsimulator.config.ControlPanelProperties;
import com.internship.iotsimulator.config.MetricsSimulator;
import com.internship.iotsimulator.dto.DeviceConnectionRequest;
import com.internship.iotsimulator.dto.DeviceConnectionResponse;
import com.internship.iotsimulator.dto.MetricsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.*;



@Service
@Slf4j
public class IotSimulatorService {


    @Autowired
    private ControlPanelProperties controlPanelProperties;

    private final Map<Long, MetricsSimulator> tasks = new HashMap<>();

    @Autowired
    private AsyncConfiguration asyncConfiguration;

    @Autowired
    private ConnectionConfig connectionConfig;

    @Autowired
    private MetricsSimulator metricsSimulator;

    public DeviceConnectionResponse startSession(DeviceConnectionRequest deviceConnectionRequest) {
        String url = String.format("%s/%s/%s", controlPanelProperties.getControllerPath(), deviceConnectionRequest
                .getMachineId(), deviceConnectionRequest.getDeviceId());
        DeviceConnectionResponse deviceConnectionResponse = new DeviceConnectionResponse();
        deviceConnectionResponse.setSessionId(connectionConfig.getRestTemplate()
                .postForObject(url, deviceConnectionRequest, DeviceConnectionResponse.class).getSessionId());
        return deviceConnectionResponse;
    }

    public void endSession(Long sessionId) {
        String url = String.format("%s/%s", controlPanelProperties.getControllerPath(), sessionId);
        connectionConfig.getRestTemplate().postForObject(url, null, DeviceConnectionResponse.class);
        // stopSendingMetrics(sessionId);
    }

    // Sending metrics using ExecutorService and ScheduledThreadPoolExecutor
  /*
 public BaseResponse sendMetrics(Long sessionId, Integer intervalInSeconds) {
        Runnable task = () -> {
                Random random = new Random();
                Integer value = random.nextInt();
                MetricsRequest metricsRequest = new MetricsRequest(value);
                String url = String.format("%s/%s/%s", controlPanelProperties.getControllerPath(), sessionId, controlPanelProperties.getMetricsPath());
                sendPost(url, metricsRequest);
            };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        executor.scheduleAtFixedRate(task, 0, intervalInSeconds, TimeUnit.SECONDS);
        tasks.put(sessionId, executor);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSessionId(sessionId);
        return baseResponse;
    } */
    @Async("asyncScheduler")
    public void sendMetrics(Long sessionId, Long intervalInSeconds) {
            metricsSimulator.setSessionId(sessionId);
            metricsSimulator.setIntervalInSeconds(intervalInSeconds);
            tasks.put(sessionId, metricsSimulator);
            metricsSimulator.run();

            // asyncConfiguration.taskScheduler().scheduleAtFixedRate(metricsSimulator, intervalInSeconds);
        }
    }


    public void stopSendingMetrics(Long sessionId) {
        tasks.get(sessionId).setIsActive(false);
        log.info("Metric send for session#{} stopped! ", sessionId);
    }

    /*
        private DeviceConnectionResponse sendPost(String url, DeviceConnectionRequest body) {
            return getRestTemplate().postForObject(url, body, DeviceConnectionResponse.class);
        }
    */
    @PreDestroy
    private void stopAllSessionsFromSendingMetrics() {
        //   for (Map.Entry<Long, AsyncConfiguration> task : tasks.entrySet()) {
        //     log.info("Metric send for session#{} stopped! ", task.getKey());  // WRONG!!!
        //      tasks.get(task.getKey()).taskScheduler().shutdown();
    }
}
