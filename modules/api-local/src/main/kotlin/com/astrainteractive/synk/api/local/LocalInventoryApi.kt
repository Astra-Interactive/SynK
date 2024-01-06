package com.astrainteractive.synk.api.local

import ru.astrainteractive.synk.core.model.PlayerModel
import java.io.File

interface LocalInventoryApi<DATA> {
    /**
     * Type of save
     */
    enum class TYPE {
        /**
         * Player leave the server
         */
        EXIT,

        /**
         * Player entered a server
         */
        ENTER,

        /**
         * When save all executed
         */
        SAVE_ALL,

        /**
         * Player died
         */
        DEATH
    }

    /**
     * Save player inventory
     */
    fun savePlayer(playerModel: PlayerModel, type: TYPE)

    /**
     * Loade previous player saves
     */
    fun loadPlayerSaves(playerModel: PlayerModel): List<File>

    /**
     * Read player inventory save
     */
    fun readPlayerInventorySave(playerModel: PlayerModel, file: File): List<DATA>

    /**
     * Read player enderchest save
     */
    fun readPlayerEnderChestSave(playerModel: PlayerModel, file: File): List<DATA>
}
