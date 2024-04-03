package com.example.emmisiontracker.domain.travel;

import com.example.emmisiontracker.constants.TravelMethod;

public record TravelMethodInfo(TravelMethod name, double emissionPerKilometer) {
}
