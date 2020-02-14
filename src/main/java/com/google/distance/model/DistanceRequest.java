package com.google.distance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistanceRequest {

    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<DistanceRequestRows> rows;
    private String status;
}