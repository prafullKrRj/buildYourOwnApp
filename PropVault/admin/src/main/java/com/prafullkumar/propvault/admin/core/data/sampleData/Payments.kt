package com.prafullkumar.propvault.admin.core.data.sampleData

import com.prafullkumar.propvault.admin.core.domain.model.Payment
import com.prafullkumar.propvault.admin.core.domain.model.PaymentStatus


val payments = listOf(
    Payment(
        id = 1,
        dealId = 1,
        amount = 100000.0,
        totalPrice = 300000.0,
        depositedAmount = 100000.0,
        dueAmount = 200000.0,
        paymentDate = "2024-01-15",
        status = PaymentStatus.COMPLETED,
        remarks = "Down payment"
    ),
    Payment(
        id = 2,
        dealId = 1,
        amount = 200000.0,
        totalPrice = 300000.0,
        depositedAmount = 300000.0,
        dueAmount = 0.0,
        paymentDate = "2024-02-15",
        status = PaymentStatus.COMPLETED,
        remarks = "Final payment"
    ),

    // Payments for Deal 2 (Unit 101-E)
    Payment(
        id = 3,
        dealId = 2,
        amount = 2000000.0,
        totalPrice = 9800000.0,
        depositedAmount = 2000000.0,
        dueAmount = 7800000.0,
        paymentDate = "2024-02-01",
        status = PaymentStatus.COMPLETED,
        remarks = "Initial payment"
    ),
    Payment(
        id = 4,
        dealId = 2,
        amount = 7800000.0,
        totalPrice = 9800000.0,
        depositedAmount = 9800000.0,
        dueAmount = 0.0,
        paymentDate = "2024-03-15",
        status = PaymentStatus.COMPLETED,
        remarks = "Full payment"
    ),

    // Payments for Deal 3 (Unit 102-A)
    Payment(
        id = 5,
        dealId = 3,
        amount = 4550000.0,
        totalPrice = 9100000.0,
        depositedAmount = 4550000.0,
        dueAmount = 4550000.0,
        paymentDate = "2024-01-20",
        status = PaymentStatus.COMPLETED,
        remarks = "50% payment"
    ),
    Payment(
        id = 6,
        dealId = 3,
        amount = 4550000.0,
        totalPrice = 9100000.0,
        depositedAmount = 9100000.0,
        dueAmount = 0.0,
        paymentDate = "2024-03-01",
        status = PaymentStatus.COMPLETED,
        remarks = "Final installment"
    ),

    // Payments for Deal 4 (Unit 201-B)
    Payment(
        id = 7,
        dealId = 4,
        amount = 95000.0,
        totalPrice = 95000.0,
        depositedAmount = 95000.0,
        dueAmount = 0.0,
        paymentDate = "2024-02-10",
        status = PaymentStatus.COMPLETED,
        remarks = "Full payment"
    ),

    // Payments for Deal 5 (Unit 202-B)
    Payment(
        id = 8,
        dealId = 5,
        amount = 165000.0,
        totalPrice = 165000.0,
        depositedAmount = 165000.0,
        dueAmount = 0.0,
        paymentDate = "2024-02-15",
        status = PaymentStatus.COMPLETED,
        remarks = "Cash payment"
    ),

    // Payments for Deal 6 (Unit 202-E)
    Payment(
        id = 9,
        dealId = 6,
        amount = 145000.0,
        totalPrice = 290000.0,
        depositedAmount = 145000.0,
        dueAmount = 145000.0,
        paymentDate = "2024-02-20",
        status = PaymentStatus.COMPLETED,
        remarks = "First installment"
    ),
    Payment(
        id = 10,
        dealId = 6,
        amount = 145000.0,
        totalPrice = 290000.0,
        depositedAmount = 290000.0,
        dueAmount = 0.0,
        paymentDate = "2024-04-05",
        status = PaymentStatus.COMPLETED,
        remarks = "Second installment"
    ),

    // Payments for Deal 7 (Unit 203-B)
    Payment(
        id = 11,
        dealId = 7,
        amount = 140000.0,
        totalPrice = 140000.0,
        depositedAmount = 140000.0,
        dueAmount = 0.0,
        paymentDate = "2024-02-25",
        status = PaymentStatus.COMPLETED,
        remarks = "Complete payment"
    ),

    // Payments for Deal 8 (Unit 301-B)
    Payment(
        id = 12,
        dealId = 8,
        amount = 4100000.0,
        totalPrice = 8200000.0,
        depositedAmount = 4100000.0,
        dueAmount = 4100000.0,
        paymentDate = "2024-03-01",
        status = PaymentStatus.COMPLETED,
        remarks = "First half"
    ),
    Payment(
        id = 13,
        dealId = 8,
        amount = 4100000.0,
        totalPrice = 8200000.0,
        depositedAmount = 8200000.0,
        dueAmount = 0.0,
        paymentDate = "2024-04-15",
        status = PaymentStatus.COMPLETED,
        remarks = "Second half"
    ),

    // Payments for Deal 9 (Unit 302-A)
    Payment(
        id = 14,
        dealId = 9,
        amount = 2700000.0,
        totalPrice = 8100000.0,
        depositedAmount = 2700000.0,
        dueAmount = 5400000.0,
        paymentDate = "2024-03-05",
        status = PaymentStatus.COMPLETED,
        remarks = "First installment"
    ),
    Payment(
        id = 15,
        dealId = 9,
        amount = 5400000.0,
        totalPrice = 8100000.0,
        depositedAmount = 8100000.0,
        dueAmount = 0.0,
        paymentDate = "2024-04-20",
        status = PaymentStatus.COMPLETED,
        remarks = "Final installment"
    )
)