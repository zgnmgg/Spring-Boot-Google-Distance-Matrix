package com.google.distance.controller;

import com.google.distance.model.DistanceRequest;
import com.google.distance.model.DistanceRequestRowsElements;
import com.google.distance.model.Venue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class DistanceController {

    @Value("${distance.matrix.api.key}")
    private String distanceMatrixApiKey;
    @Value("${distance.matrix.core.url}")
    private String coreUrl;
    @Value("${distance.matrix.mode}")
    private String mode;
    @Value("${distance.matrix.units}")
    private String units;

    @RequestMapping("/distance")
    @ResponseBody
    public List<Venue> getDistance() {
        List<Venue> venues = this.getVenues();

        if (venues.size() < 100) {
            final RestTemplate restTemplate = new RestTemplate();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(coreUrl)
                .queryParam("origins", "22.061218,22.013580")
                .queryParam("destinations", venues.stream().map(temp ->
                        temp.getLatitude() + "," + temp.getLongitude()
                ).collect(Collectors.joining("|")))
                .queryParam("key", distanceMatrixApiKey)
                .queryParam("mode", mode)
                .queryParam("units", units);

            HttpEntity<DistanceRequest> response = restTemplate.getForEntity(builder.build().encode().toUri(), DistanceRequest.class);
            if(response.getBody().getStatus().equals("OK")) {

                List<DistanceRequestRowsElements> distanceRequestRowsElements = response.getBody().getRows().get(0).getElements();

                IntStream.range(0, venues.size())
                    .forEach(idx -> venues.get(idx).setDistance(convertDistanceToDouble(distanceRequestRowsElements.get(idx).getDistance().getText())));
            }
        }

        return venues;
    }

    public List<Venue> getVenues() {

        //Add demo venues
        List<Venue> venues = new ArrayList<>();
        venues.add(new Venue(1, "Name 0", 23.061218, 29.013581, 0));
        venues.add(new Venue(1, "Name 0", 25.042615013410945, 21.993251320817194, 0));
        venues.add(new Venue(1, "Name 0", 24.042615013410945, 22.993251320817194, 0));
        return venues;
    }

    public double convertDistanceToDouble(String value) {

        return Double.parseDouble(value.split(" ")[0]);
    }
}