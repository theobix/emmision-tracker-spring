package com.example.emmisiontracker.domain;

import com.example.emmisiontracker.constants.TravelMethod;

public record TravelMethodInfo(TravelMethod name, float emissionPerKilometer) {
}
