package com.seumanoel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackagingResult {

    private String orderId;
    private List<BoxWithProducts> boxesUsed = new ArrayList<>();
}