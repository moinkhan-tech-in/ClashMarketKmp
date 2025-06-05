package com.clash.market.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull

@Serializable(with = MembersFieldSerializer::class)
sealed class MembersField {
    data class Count(val total: Int) : MembersField()
    data class List(val members: kotlin.collections.List<Player>) : MembersField()
}

object MembersFieldSerializer : KSerializer<MembersField> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("MembersField")

    override fun deserialize(decoder: Decoder): MembersField {
        val input = decoder as? JsonDecoder ?: error("Expected JsonDecoder")
        val element = input.decodeJsonElement()

        return when {
            element is JsonPrimitive && element.isString.not() && element.intOrNull != null-> {
                MembersField.Count(element.int)
            }
            element is JsonArray -> {
                val list = element.map { input.json.decodeFromJsonElement(Player.serializer(), it) }
                MembersField.List(list)
            }
            else -> error("Unsupported members field format")
        }
    }

    override fun serialize(encoder: Encoder, value: MembersField) {
        when (value) {
            is MembersField.Count -> encoder.encodeInt(value.total)
            is MembersField.List -> encoder.encodeSerializableValue(
                ListSerializer(Player.serializer()),
                value.members
            )
        }
    }
}