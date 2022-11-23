package com.internship.iotsimulator.dtos;

import com.internship.iotsimulator.configs.DeviceProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest {

    private Long machineId;
    private Long deviceId;

    public BaseRequest(DeviceProperties properties) {
        this.machineId = properties.getMachineId();
        this.deviceId = properties.getDeviceId();
    }

}
