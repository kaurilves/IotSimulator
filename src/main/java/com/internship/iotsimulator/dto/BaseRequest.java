package com.internship.iotsimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest {

    private Long machineId;
    private Long deviceId;
}
