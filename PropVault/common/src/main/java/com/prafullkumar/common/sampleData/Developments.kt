package com.prafullkumar.common.sampleData

import com.prafullkumar.common.models.Development


val developmentsSample = listOf(
    Development(
        id = "550e8400-e29b-41d4-a716-446655440000",
        name = "Skyline Heights",
        category = "Residential",
        website = "https://skylineheights.com",
        city = "Mumbai",
        attorneyFirmName = "Singh & Co.",
        brokerageFeeCommission = 3,
        status = "Active",
        startingPrice = 8500000.0,
        numberOfBuildings = 2,
        buildings = listOf(
            "550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002"
        )
    ), Development(
        id = "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
        name = "Tech Valley Towers",
        category = "Residential",
        website = "https://techvalley.com",
        city = "Bangalore",
        attorneyFirmName = "Nair Legal Associates",
        brokerageFeeCommission = 2,
        status = "Pre-Launch",
        startingPrice = 6500000.0,
        numberOfBuildings = 3,
        buildings = listOf(
            "6ba7b810-9dad-11d1-80b4-00c04fd430c1",
            "6ba7b810-9dad-11d1-80b4-00c04fd430c2",
            "6ba7b810-9dad-11d1-80b4-00c04fd430c3"
        )
    ), Development(
        id = "7c9e6679-7425-40de-944b-e07fc1f90ae7",
        name = "Capital Grande",
        category = "Mixed Use",
        website = "https://capitalgrande.com",
        city = "Delhi",
        attorneyFirmName = "Verma & Verma",
        brokerageFeeCommission = 4,
        status = "Under Construction",
        startingPrice = 7200000.0,
        numberOfBuildings = 2,
        buildings = listOf(
            "7c9e6679-7425-40de-944b-e07fc1f90a01", "7c9e6679-7425-40de-944b-e07fc1f90a02"
        )
    )
)