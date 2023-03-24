package com.astrainteractive.synk.api.messaging.models

data class BungeeMessageData(
    val action: String,
    val message: List<String>,
)