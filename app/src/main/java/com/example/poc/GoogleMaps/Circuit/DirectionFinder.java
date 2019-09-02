package com.example.poc.GoogleMaps.Circuit;

import java.util.List;

public interface DirectionFinder {
    void onDirectionFinderStart();

    void onDirectionFinderSuccess(List<Route> routes);
}
