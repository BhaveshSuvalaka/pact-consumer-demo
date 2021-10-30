package com.pactconsumer.demo.event.listner

import com.pactconsumer.demo.event.listner.model.OrderReceivedEvent
import com.pactconsumer.demo.service.ShipmentService

/**
 *  Any messaging interface can be bind here, this is out of scope for this
 *  example
 */
class EventProcessor(private val shipmentService: ShipmentService) {
    fun process(orderReceivedEvent: OrderReceivedEvent) {
        shipmentService.processOrderReceivedEvent(orderReceivedEvent);
    }
}
