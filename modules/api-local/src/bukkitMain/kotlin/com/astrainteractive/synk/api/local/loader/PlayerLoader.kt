package com.astrainteractive.synk.api.local.loader

import ru.astrainteractive.synk.core.model.PlayerModel

interface PlayerLoader {
    fun loadPlayer(it: PlayerModel)
}
