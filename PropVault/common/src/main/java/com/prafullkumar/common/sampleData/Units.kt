package com.prafullkumar.common.sampleData

import com.prafullkumar.common.models.PropertyUnit



enum class PropertyStatus(
    val status: String,
) {
    AVAILABLE("Available"),
    SOLD("Sold"),
    RESERVED("Reserved")
}