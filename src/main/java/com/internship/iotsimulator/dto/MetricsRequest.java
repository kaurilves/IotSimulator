package com.internship.iotsimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsRequest extends DeviceConnectionRequest implements Serializable {

    private Integer value;
}
