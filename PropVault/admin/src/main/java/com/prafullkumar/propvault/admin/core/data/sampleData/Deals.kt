package com.prafullkumar.propvault.admin.core.data.sampleData

import com.prafullkumar.propvault.admin.core.domain.model.Deal
import com.prafullkumar.propvault.admin.core.domain.model.DealStatus


val dealsSample = listOf(
    // Unit 101-B (id: 2)
    Deal(
        id = 1,
        unitId = 2,
        customerName = "John Doe",
        customerPhone = "+1234567890",
        customerEmail = "john.doe@email.com",
        totalPrice = 300000.0,
        dealDate = "2024-01-15",
        closingDate = "2024-02-15",
        assignedAgent = "Sarah Johnson",
        status = DealStatus.COMPLETED,
        remarks = "Smooth transaction"
    ),
    // Unit 101-E (id: 5)
    Deal(
        id = 2,
        unitId = 5,
        customerName = "Jane Smith",
        customerPhone = "+1987654321",
        customerEmail = "jane.smith@email.com",
        totalPrice = 9800000.0,
        dealDate = "2024-02-01",
        closingDate = "2024-03-15",
        assignedAgent = "Michael Brown",
        status = DealStatus.COMPLETED,
        remarks = "Premium unit sale"
    ),
    // Unit 102-A (id: 7)
    Deal(
        id = 3,
        unitId = 7,
        customerName = "Robert Wilson",
        customerPhone = "+1122334455",
        customerEmail = "robert.w@email.com",
        totalPrice = 9100000.0,
        dealDate = "2024-01-20",
        closingDate = "2024-03-01",
        assignedAgent = "Emma Davis",
        status = DealStatus.COMPLETED,
        remarks = "Corporate purchase"
    ),
    // Unit 201-B (id: 13)
    Deal(
        id = 4,
        unitId = 13,
        customerName = "Mary Johnson",
        customerPhone = "+1567890123",
        customerEmail = "mary.j@email.com",
        totalPrice = 95000.0,
        dealDate = "2024-02-10",
        closingDate = "2024-03-20",
        assignedAgent = "David Clark",
        status = DealStatus.COMPLETED,
        remarks = null
    ),
    // Unit 202-B (id: 17)
    Deal(
        id = 5,
        unitId = 17,
        customerName = "James Anderson",
        customerPhone = "+1678901234",
        customerEmail = "james.a@email.com",
        totalPrice = 165000.0,
        dealDate = "2024-02-15",
        closingDate = "2024-03-30",
        assignedAgent = "Lisa Wang",
        status = DealStatus.COMPLETED,
        remarks = "Cash payment"
    ),
    // Unit 202-E (id: 20)
    Deal(
        id = 6,
        unitId = 20,
        customerName = "Patricia Brown",
        customerPhone = "+1789012345",
        customerEmail = "pat.b@email.com",
        totalPrice = 290000.0,
        dealDate = "2024-02-20",
        closingDate = "2024-04-05",
        assignedAgent = "Tom Harris",
        status = DealStatus.COMPLETED,
        remarks = "Investment purchase"
    ),
    // Unit 203-B (id: 22)
    Deal(
        id = 7,
        unitId = 22,
        customerName = "Michael Lee",
        customerPhone = "+1890123456",
        customerEmail = "michael.l@email.com",
        totalPrice = 140000.0,
        dealDate = "2024-02-25",
        closingDate = "2024-04-10",
        assignedAgent = "Sarah Johnson",
        status = DealStatus.COMPLETED,
        remarks = null
    ),
    // Unit 301-B (id: 25)
    Deal(
        id = 8,
        unitId = 25,
        customerName = "Elizabeth Taylor",
        customerPhone = "+1901234567",
        customerEmail = "elizabeth.t@email.com",
        totalPrice = 8200000.0,
        dealDate = "2024-03-01",
        closingDate = "2024-04-15",
        assignedAgent = "Michael Brown",
        status = DealStatus.COMPLETED,
        remarks = "Luxury unit sale"
    ),
    // Unit 302-A (id: 30)
    Deal(
        id = 9,
        unitId = 30,
        customerName = "William Davis",
        customerPhone = "+1012345678",
        customerEmail = "william.d@email.com",
        totalPrice = 8100000.0,
        dealDate = "2024-03-05",
        closingDate = "2024-04-20",
        assignedAgent = "Emma Davis",
        status = DealStatus.COMPLETED,
        remarks = "Premium location"
    )
)
