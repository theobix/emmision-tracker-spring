package com.example.Emmision.Tracker;

import java.time.OffsetDateTime;
import java.util.List;

public record StatsGroup(OffsetDateTime[] labels, List<Travel[]> travelGroups) {
}
