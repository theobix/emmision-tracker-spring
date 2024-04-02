package com.example.Emmision.Tracker.domain;

import java.time.OffsetDateTime;
import java.util.List;

public record StatsGroup(OffsetDateTime[] labels, List<Travel[]> travelGroups) {
}
