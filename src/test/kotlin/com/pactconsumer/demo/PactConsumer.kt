package com.pactconsumer.demo

import au.com.dius.pact.consumer.MessagePactBuilder
import au.com.dius.pact.consumer.dsl.DslPart
import au.com.dius.pact.consumer.dsl.newJsonObject
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.consumer.junit5.ProviderType
import au.com.dius.pact.core.model.annotations.Pact
import au.com.dius.pact.core.model.messaging.Message
import au.com.dius.pact.core.model.messaging.MessagePact
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.pactconsumer.demo.event.listner.model.OrderReceivedEvent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.function.Consumer

@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "order-service", port = "8080", providerType = ProviderType.ASYNCH)
class PactConsumer {

    @Pact(consumer = "shipment-service")
    fun validOrderReceivedEvent(builder: MessagePactBuilder): MessagePact {
        val body = expectedBodyForOrderReceivedEvent()
        return builder.expectsToReceive("OrderReceivedEvent").withContent(body).toPact()
    }

    @Test
    @PactTestFor(pactMethod = "validOrderReceivedEvent")
    fun `test validOrderReceivedEvent`(messages: List<Message>) {
        Assertions.assertNotEquals(messages.size, 0)
        validateMessage(messages[0], OrderReceivedEvent::class.java)
    }

    private fun <T> validateMessage(message: Message, t: Class<T>) {
        val mapper = ObjectMapper().findAndRegisterModules()
        val messageObj = mapper.readValue(String(message.contentsAsBytes()), t)
        Assertions.assertNotNull(messageObj)
    }

    private fun expectedBodyForOrderReceivedEvent(): DslPart =
        newJsonObject {
            this.uuid("orderUuid")
                .array("products", Consumer { it.uuid() })
                .numberType("paidAmount")
                .stringType("purchasedBy")
                .datetime("purchasedDate","yyyy-MM-dd")
        }
}