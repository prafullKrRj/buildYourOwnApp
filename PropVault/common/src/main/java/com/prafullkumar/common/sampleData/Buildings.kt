package com.prafullkumar.common.sampleData

import com.prafullkumar.common.models.Building
import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.DealStatus
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.models.PaymentStatus
import com.prafullkumar.common.models.PropertyUnit


val buildingsSample = listOf(
    Building(
        "550e8400-e29b-41d4-a716-446655440001",
        "Tower A",
        "550e8400-e29b-41d4-a716-446655440000",
        "Skyline Heights",
        2,
        units = listOf(
            "f47ac10b-58cc-4372-a567-0e02b2c3d479", "f47ac10b-58cc-4372-a567-0e02b2c3d480"
        )
    ), Building(
        "550e8400-e29b-41d4-a716-446655440002",
        "Tower B",
        "550e8400-e29b-41d4-a716-446655440000",
        "Skyline Heights",
        1,
        units = listOf("f47ac10b-58cc-4372-a567-0e02b2c3d481")
    ), Building(
        "6ba7b810-9dad-11d1-80b4-00c04fd430c1",
        "Valley View 1",
        "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
        "Tech Valley Towers",
        1,
        units = listOf("f47ac10b-58cc-4372-a567-0e02b2c3d482")
    ), Building(
        "6ba7b810-9dad-11d1-80b4-00c04fd430c2",
        "Valley View 2",
        "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
        "Tech Valley Towers",
        1,
        units = listOf("f47ac10b-58cc-4372-a567-0e02b2c3d483")
    ), Building(
        "6ba7b810-9dad-11d1-80b4-00c04fd430c3",
        "Tech Residency",
        "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
        "Tech Valley Towers",
        1,
        units = listOf("f47ac10b-58cc-4372-a567-0e02b2c3d484")
    ), Building(
        "7c9e6679-7425-40de-944b-e07fc1f90a01",
        "Grande West",
        "7c9e6679-7425-40de-944b-e07fc1f90ae7",
        "Capital Grande",
        1,
        units = listOf("f47ac10b-58cc-4372-a567-0e02b2c3d485")
    ), Building(
        "7c9e6679-7425-40de-944b-e07fc1f90a02",
        "Grande East",
        "7c9e6679-7425-40de-944b-e07fc1f90ae7",
        "Capital Grande",
        1,
        units = listOf("f47ac10b-58cc-4372-a567-0e02b2c3d486")
    )
)

val propertyUnits = listOf(
    // Tower A units
    PropertyUnit(
        id = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
        name = "1A",
        unitCode = "TA-1A",
        developmentId = "550e8400-e29b-41d4-a716-446655440000",
        developmentName = "Skyline Heights",
        buildingId = "550e8400-e29b-41d4-a716-446655440001",
        buildingName = "Tower A",
        unitType = "2BHK",
        price = 250000.0,
        status = PropertyStatus.AVAILABLE.status,
        createdTime = "2024-01-15T10:00:00Z",
        deals = listOf("550e8400-e29b-41d4-a716-446655440011")
    ), PropertyUnit(
        id = "f47ac10b-58cc-4372-a567-0e02b2c3d480",
        name = "2A",
        unitCode = "TA-2A",
        developmentId = "550e8400-e29b-41d4-a716-446655440000",
        developmentName = "Skyline Heights",
        buildingId = "550e8400-e29b-41d4-a716-446655440001",
        buildingName = "Tower A",
        unitType = "3BHK",
        price = 350000.0,
        status = PropertyStatus.SOLD.status,
        createdTime = "2024-01-15T10:00:00Z",
        deals = listOf("550e8400-e29b-41d4-a716-446655440012")
    ),

    // Tower B units
    PropertyUnit(
        id = "f47ac10b-58cc-4372-a567-0e02b2c3d481",
        name = "1B",
        unitCode = "TB-1B",
        developmentId = "550e8400-e29b-41d4-a716-446655440000",
        developmentName = "Skyline Heights",
        buildingId = "550e8400-e29b-41d4-a716-446655440002",
        buildingName = "Tower B",
        unitType = "2BHK",
        price = 260000.0,
        status = PropertyStatus.AVAILABLE.status,
        createdTime = "2024-01-15T10:00:00Z"
    ),

    // Valley View 1 units
    PropertyUnit(
        id = "f47ac10b-58cc-4372-a567-0e02b2c3d482",
        name = "101",
        unitCode = "VV1-101",
        developmentId = "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
        developmentName = "Tech Valley Towers",
        buildingId = "6ba7b810-9dad-11d1-80b4-00c04fd430c1",
        buildingName = "Valley View 1",
        unitType = "1BHK",
        price = 150000.0,
        status = PropertyStatus.RESERVED.status,
        createdTime = "2024-01-15T10:00:00Z"
    ),

    // Valley View 2 units
    PropertyUnit(
        id = "f47ac10b-58cc-4372-a567-0e02b2c3d483",
        name = "201",
        unitCode = "VV2-201",
        developmentId = "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
        developmentName = "Tech Valley Towers",
        buildingId = "6ba7b810-9dad-11d1-80b4-00c04fd430c2",
        buildingName = "Valley View 2",
        unitType = "2BHK",
        price = 200000.0,
        status = PropertyStatus.AVAILABLE.status,
        createdTime = "2024-01-15T10:00:00Z"
    ),

    // Tech Residency units
    PropertyUnit(
        id = "f47ac10b-58cc-4372-a567-0e02b2c3d484",
        name = "TR-1",
        unitCode = "TR-001",
        developmentId = "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
        developmentName = "Tech Valley Towers",
        buildingId = "6ba7b810-9dad-11d1-80b4-00c04fd430c3",
        buildingName = "Tech Residency",
        unitType = "3BHK",
        price = 400000.0,
        status = PropertyStatus.AVAILABLE.status,
        createdTime = "2024-01-15T10:00:00Z",
        deals = listOf("550e8400-e29b-41d4-a716-446655440013")
    ),

    // Grande West units
    PropertyUnit(
        id = "f47ac10b-58cc-4372-a567-0e02b2c3d485",
        name = "GW-1",
        unitCode = "GW-001",
        developmentId = "7c9e6679-7425-40de-944b-e07fc1f90ae7",
        developmentName = "Capital Grande",
        buildingId = "7c9e6679-7425-40de-944b-e07fc1f90a01",
        buildingName = "Grande West",
        unitType = "4BHK",
        price = 500000.0,
        status = PropertyStatus.AVAILABLE.status,
        createdTime = "2024-01-15T10:00:00Z"
    ),

    // Grande East units
    PropertyUnit(
        id = "f47ac10b-58cc-4372-a567-0e02b2c3d486",
        name = "GE-1",
        unitCode = "GE-001",
        developmentId = "7c9e6679-7425-40de-944b-e07fc1f90ae7",
        developmentName = "Capital Grande",
        buildingId = "7c9e6679-7425-40de-944b-e07fc1f90a02",
        buildingName = "Grande East",
        unitType = "3BHK",
        price = 450000.0,
        status = PropertyStatus.SOLD.status,
        createdTime = "2024-01-15T10:00:00Z",
        deals = listOf("550e8400-e29b-41d4-a716-446655440014")
    )
)

val dealsSample = listOf(
    Deal(
        id = "550e8400-e29b-41d4-a716-446655440011",
        unitId = "f47ac10b-58cc-4372-a567-0e02b2c3d479",  // Unit 1A in Tower A
        customerName = "John Smith",
        customerPhone = "+1234567890",
        customerEmail = "john.smith@email.com",
        totalPrice = 250000.0,
        dealDate = "2024-02-01",
        closingDate = "2024-03-01",
        assignedAgent = "Sarah Johnson",
        status = DealStatus.COMPLETED,
        remarks = "Cash payment",
        payments = listOf(
            "550e8400-e29b-41d4-a716-446655440021", "550e8400-e29b-41d4-a716-446655440022"
        )
    ), Deal(
        id = "550e8400-e29b-41d4-a716-446655440012",
        unitId = "f47ac10b-58cc-4372-a567-0e02b2c3d480",  // Unit 2A in Tower A
        customerName = "Emma Wilson",
        customerPhone = "+1987654321",
        customerEmail = "emma.wilson@email.com",
        totalPrice = 350000.0,
        dealDate = "2024-02-15",
        closingDate = "2024-03-15",
        assignedAgent = "Michael Brown",
        status = DealStatus.COMPLETED,
        remarks = "Premium unit",
        payments = listOf("550e8400-e29b-41d4-a716-446655440023")
    ), Deal(
        id = "550e8400-e29b-41d4-a716-446655440013",
        unitId = "f47ac10b-58cc-4372-a567-0e02b2c3d484",  // TR-1 in Tech Residency
        customerName = "Robert Davis",
        customerPhone = "+1122334455",
        customerEmail = "robert.davis@email.com",
        totalPrice = 400000.0,
        dealDate = "2024-03-01",
        closingDate = "2024-04-01",
        assignedAgent = "Lisa Wang",
        status = DealStatus.COMPLETED,
        remarks = "Financing approved",
        payments = listOf(
            "550e8400-e29b-41d4-a716-446655440024", "550e8400-e29b-41d4-a716-446655440025"
        )
    ), Deal(
        id = "550e8400-e29b-41d4-a716-446655440014",
        unitId = "f47ac10b-58cc-4372-a567-0e02b2c3d486",  // GE-1 in Grande East
        customerName = "Patricia Brown",
        customerPhone = "+1567890123",
        customerEmail = "patricia.brown@email.com",
        totalPrice = 450000.0,
        dealDate = "2024-03-15",
        closingDate = "2024-04-15",
        assignedAgent = "David Clark",
        status = DealStatus.COMPLETED,
        remarks = "Investment property",
        payments = listOf("550e8400-e29b-41d4-a716-446655440026")
    )
)


val paymentsSample = listOf(
    Payment(
        id = "550e8400-e29b-41d4-a716-446655440021",
        dealId = "550e8400-e29b-41d4-a716-446655440011",
        unitId = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
        amount = 125000.0,
        totalPrice = 250000.0,
        depositedAmount = 125000.0,
        dueAmount = 125000.0,
        paymentDate = "2024-02-01",
        status = PaymentStatus.COMPLETED,
        remarks = "First installment"
    ), Payment(
        id = "550e8400-e29b-41d4-a716-446655440022",
        dealId = "550e8400-e29b-41d4-a716-446655440011",
        unitId = "f47ac10b-58cc-4372-a567-0e02b2c3d479",
        amount = 125000.0,
        totalPrice = 250000.0,
        depositedAmount = 250000.0,
        dueAmount = 0.0,
        paymentDate = "2024-02-15",
        status = PaymentStatus.COMPLETED,
        remarks = "Final payment"
    ), Payment(
        id = "550e8400-e29b-41d4-a716-446655440023",
        dealId = "550e8400-e29b-41d4-a716-446655440012",
        unitId = "f47ac10b-58cc-4372-a567-0e02b2c3d480",
        amount = 350000.0,
        totalPrice = 350000.0,
        depositedAmount = 350000.0,
        dueAmount = 0.0,
        paymentDate = "2024-02-15",
        status = PaymentStatus.COMPLETED,
        remarks = "Full payment"
    ), Payment(
        id = "550e8400-e29b-41d4-a716-446655440024",
        dealId = "550e8400-e29b-41d4-a716-446655440013",
        unitId = "f47ac10b-58cc-4372-a567-0e02b2c3d484",
        amount = 200000.0,
        totalPrice = 400000.0,
        depositedAmount = 200000.0,
        dueAmount = 200000.0,
        paymentDate = "2024-03-01",
        status = PaymentStatus.COMPLETED,
        remarks = "Down payment"
    ), Payment(
        id = "550e8400-e29b-41d4-a716-446655440025",
        dealId = "550e8400-e29b-41d4-a716-446655440013",
        unitId = "f47ac10b-58cc-4372-a567-0e02b2c3d484",
        amount = 200000.0,
        totalPrice = 400000.0,
        depositedAmount = 400000.0,
        dueAmount = 0.0,
        paymentDate = "2024-03-15",
        status = PaymentStatus.COMPLETED,
        remarks = "Final payment"
    ), Payment(
        id = "550e8400-e29b-41d4-a716-446655440026",
        dealId = "550e8400-e29b-41d4-a716-446655440014",
        unitId = "f47ac10b-58cc-4372-a567-0e02b2c3d486",
        amount = 450000.0,
        totalPrice = 450000.0,
        depositedAmount = 450000.0,
        dueAmount = 0.0,
        paymentDate = "2024-03-15",
        status = PaymentStatus.COMPLETED,
        remarks = "Single payment"
    )
)
