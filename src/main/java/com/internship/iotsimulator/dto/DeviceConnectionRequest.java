package com.internship.iotsimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceConnectionRequest {

    private Long machineId;
    private Long deviceId;
}
