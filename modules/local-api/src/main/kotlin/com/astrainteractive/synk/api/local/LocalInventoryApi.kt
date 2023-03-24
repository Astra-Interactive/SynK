package com.astrainteractive.synk.api.local

import java.io.File

interface LocalInventoryApi<PLAYER, ITEM_STACK> {
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
    fun savePlayer(player: PLAYER, type: TYPE)

    /**
     * Loade previous player saves
     */
    fun loadPlayerSaves(player: PLAYER): List<File>

    /**
     * Read player inventory save
     */
    fun readPlayerInventorySave(player: PLAYER, file: File): List<ITEM_STACK>

    /**
     * Read player enderchest save
     */
    fun readPlayerEnderChestSave(player: PLAYER, file: File): List<ITEM_STACK>
}
