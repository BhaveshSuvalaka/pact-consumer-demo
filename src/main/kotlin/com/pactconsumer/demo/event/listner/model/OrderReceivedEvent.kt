package com.pactconsumer.demo.event.listner.model

import java.time.LocalDate
import java.util.UUID

data class OrderReceivedEvent(
    val orderUuid: UUID,
    val products: List<UUID>,
    val paidAmount: Double,
    val purchasedBy: String,
    val purchasedDate: LocalDate
)
