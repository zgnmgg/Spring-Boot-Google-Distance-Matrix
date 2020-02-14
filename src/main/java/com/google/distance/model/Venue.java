package com.google.distance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venue {

    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private double distance;
}
