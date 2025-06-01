package com.seumanoel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Box {

    private String id;
    private String name;
    private Double height;
    private Double width;
    private Double length;

    public Double getVolume() {
        return height * width * length;
    }
}
