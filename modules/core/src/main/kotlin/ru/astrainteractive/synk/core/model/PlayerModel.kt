package ru.astrainteractive.synk.core.model

import ru.astrainteractive.astralibs.encoding.model.EncodedObject
import java.util.UUID

data class PlayerModel(
    val minecraftUUID: UUID,
    val totalExperience: Int,
    val health: Double,
    val foodLevel: Int,
    val lastServerName: String,
    val items: EncodedObject.Base64,
    val enderChestItems: EncodedObject.Base64,
    val effects: EncodedObject.Base64
)
