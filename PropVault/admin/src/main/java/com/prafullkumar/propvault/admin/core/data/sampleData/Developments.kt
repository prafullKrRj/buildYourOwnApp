package com.prafullkumar.propvault.admin.core.data.sampleData

import com.prafullkumar.propvault.admin.core.domain.model.Development



val developments = listOf(
    Development(
        id = 1,
        name = "Skyline Heights",
        category = "Residential",
        website = "https://skylineheights.com",
        city = "Mumbai",
        attorneyFirmName = "Singh & Co.",
        brokerageFeeCommission = 3,
        status = "Active",
        startingPrice = 8500000.0,
        numberOfBuildings = 2,
        buildings = listOf(101, 102)
    ),
    Development(
        id = 2,
        name = "Tech Valley Towers",
        category = "Residential",
        website = "https://techvalley.com",
        city = "Bangalore",
        attorneyFirmName = "Nair Legal Associates",
        brokerageFeeCommission = 2,
        status = "Pre-Launch",
        startingPrice = 6500000.0,
        numberOfBuildings = 3,
        buildings = listOf(201, 202, 203)
    ),
    Development(
        id = 3,
        name = "Capital Grande",
        category = "Mixed Use",
        website = "https://capitalgrande.com",
        city = "Delhi",
        attorneyFirmName = "Verma & Verma",
        brokerageFeeCommission = 4,
        status = "Under Construction",
        startingPrice = 7200000.0,
        numberOfBuildings = 2,
        buildings = listOf(301, 302)
    )
)