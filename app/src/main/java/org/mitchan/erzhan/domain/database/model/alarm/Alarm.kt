package org.mitchan.erzhan.domain.database.model.alarm

import kotlinx.serialization.Serializable
import org.mitchan.erzhan.domain.serializers.UUIDSerializer
import org.mitchan.erzhan.domain.serializers.LocalTimeSerializer
import org.mitchan.erzhan.ui.model.Trait
import java.time.LocalTime
import java.util.UUID

@Serializable
data class Alarm(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID? = null,
    @Serializable(with = LocalTimeSerializer::class)
    val time: LocalTime = LocalTime.now(),
    val enabled: Boolean = true,
    val trait: Trait = Trait(),
)
