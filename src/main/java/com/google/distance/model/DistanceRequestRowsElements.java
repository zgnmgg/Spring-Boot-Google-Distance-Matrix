package com.google.distance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistanceRequestRowsElements {

    private DistanceRequestRowsElementsDistance distance;
    private DistanceRequestRowsElementsDuration duration;
    private String status;

}