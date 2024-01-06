package com.astrainteractive.synk.api.remote.entity

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

/**
 * Игрок
 */
internal object PlayerTable : IntIdTable() {
    val minecraftUUID: Column<String> = varchar("minecraft_name", 128).uniqueIndex()
    val experience: Column<Int> = integer("experience")
    val health: Column<Double> = double("health")
    val foodLevel: Column<Int> = integer("foodLevel")
    val lastServerName: Column<String> = varchar("last_server_name", 128)
    val items: Column<String> = text("items")
    val enderChestItems: Column<String> = text("ender_chest")
    val effects: Column<String> = text("effects")
}

internal class PlayerDAO(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, PlayerDAO>(PlayerTable)

    var minecraftUUID by PlayerTable.minecraftUUID
    var experience by PlayerTable.experience
    var health by PlayerTable.health
    var foodLevel by PlayerTable.foodLevel
    var lastServerName by PlayerTable.lastServerName
    var items by PlayerTable.items
    var enderChestItems by PlayerTable.enderChestItems
    var effects by PlayerTable.effects
}
