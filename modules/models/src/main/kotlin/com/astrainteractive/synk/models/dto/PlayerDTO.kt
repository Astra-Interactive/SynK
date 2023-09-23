package com.astrainteractive.synk.models.dto

import ru.astrainteractive.astralibs.encoding.IO
import java.util.UUID

data class PlayerDTO(
    val minecraftUUID: UUID,
    val totalExperience: Int,
    val health: Double,
    val foodLevel: Int,
    val lastServerName: String,
    val items: IO.Base64,
    val enderChestItems: IO.Base64,
    val effects: IO.Base64
)
