package com.prafullkumar.propvault.admin.core.data.sampleData

import com.prafullkumar.propvault.admin.core.domain.model.Building

val buildings = listOf(
    Building(101, "Tower A", 1, "Skyline Heights", 6, units = listOf(1, 2, 3, 4, 5, 6)),
    Building(102, "Tower B", 1, "Skyline Heights", 5, units = listOf(7, 8, 9, 10, 11)),

    Building(201, "Valley View 1", 2, "Tech Valley Towers", 4, units = listOf(12, 13, 14, 15)),
    Building(
        202,
        "Valley View 2",
        2,
        "Tech Valley Towers",
        5,
        units = listOf(16, 17, 18, 19, 20)
    ),
    Building(203, "Tech Residency", 2, "Tech Valley Towers", 3, units = listOf(21, 22, 23)),

    Building(301, "Grande West", 3, "Capital Grande", 6, units = listOf(24, 25, 26, 27, 28, 29)),
    Building(302, "Grande East", 3, "Capital Grande", 4, units = listOf(30, 31, 32, 33))
)