package com.pactconsumer.demo.event.listner

import com.pactconsumer.demo.event.listner.model.OrderReceivedEvent
import com.pactconsumer.demo.service.ShipmentService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify
import java.time.LocalDate
import java.util.UUID

class EventProcessorTest {
    private val shipmentService = Mockito.mock(ShipmentService::class.java)
    private val eventProcessor = EventProcessor(shipmentService)
    @Test
    fun `it should process Order Received event`(){
        val products = listOf<UUID>(UUID.randomUUID())
        val orderReceivedEvent = OrderReceivedEvent(UUID.randomUUID(), products,  250.00, "username", LocalDate.now())
        eventProcessor.process(orderReceivedEvent)
        verify(shipmentService).processOrderReceivedEvent(orderReceivedEvent)
    }
}