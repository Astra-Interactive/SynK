package com.astrainteractive.synk.api.local

import com.astrainteractive.synk.models.dto.PlayerDTO
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
    fun savePlayer(player: PlayerDTO, type: TYPE)

    /**
     * Loade previous player saves
     */
    fun loadPlayerSaves(player: PlayerDTO): List<File>

    /**
     * Read player inventory save
     */
    fun readPlayerInventorySave(player: PlayerDTO, file: File): List<DATA>

    /**
     * Read player enderchest save
     */
    fun readPlayerEnderChestSave(player: PlayerDTO, file: File): List<DATA>
}
