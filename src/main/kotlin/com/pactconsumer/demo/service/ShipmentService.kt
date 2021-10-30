package com.pactconsumer.demo.service

import com.pactconsumer.demo.event.listner.model.OrderReceivedEvent

interface ShipmentService {
    fun processOrderReceivedEvent(orderReceivedEvent: OrderReceivedEvent)
}
