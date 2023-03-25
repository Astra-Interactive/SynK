package com.astrainteractive.synk.models.dto

import ru.astrainteractive.astralibs.utils.encoding.Serializer
import java.util.UUID

data class PlayerDTO(
    val minecraftUUID: UUID,
    val totalExperience: Int,
    val health: Double,
    val foodLevel: Int,
    val lastServerName: String,
    val items: Serializer.Wrapper.Base64,
    val enderChestItems: Serializer.Wrapper.Base64,
    val effects: Serializer.Wrapper.Base64
)