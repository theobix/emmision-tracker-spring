package com.example.Emmision.Tracker.domain;

import com.example.Emmision.Tracker.constants.TravelMethod;

public record TravelMethodInfo(TravelMethod name, float emissionPerKilometer) {
}
