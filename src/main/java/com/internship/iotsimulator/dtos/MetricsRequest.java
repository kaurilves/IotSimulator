package com.internship.iotsimulator.dtos;

import com.internship.iotsimulator.configs.DeviceProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsRequest extends BaseRequest implements Serializable {
    private Integer value;

    public MetricsRequest(DeviceProperties properties, int value) {
        super(properties);
        this.value = value;
    }
}
