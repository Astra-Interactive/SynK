package ru.astrainteractive.synk.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.astrainteractive.astralibs.string.StringDesc

/**
 * All translation stored here
 */
@Serializable
data class PluginTranslation(
    // General
    @SerialName("general.prefix")
    val prefix: StringDesc.Raw = StringDesc.Raw("#18dbd1[EmpireItems]"),
    @SerialName("general.reload")
    val reload: StringDesc.Raw = StringDesc.Raw("#dbbb18Перезагрузка плагина"),
    @SerialName("general.reload_complete")
    val reloadComplete: StringDesc.Raw = StringDesc.Raw("#42f596Перезагрузка успешно завершена"),
    @SerialName("general.no_permission")
    val noPermission: StringDesc.Raw = StringDesc.Raw("#db2c18У вас нет прав!"),
    @SerialName("general.please_wait")
    val pleaseWait: StringDesc.Raw = StringDesc.Raw("#db2c18Пожалуйста, подождите"),
    @SerialName("general.inventory_loss_warn")
    val inventoryLossWarning: StringDesc.Raw = StringDesc.Raw(
        "#db2c18Не отключайтесь! Это может привести к потери инвентаря!!"
    ),
    @SerialName("general.error_in_saving")
    val errorOccurredInSaving: StringDesc.Raw = StringDesc.Raw("#db2c18Произошла ошибка при сохранении инвентаря"),
    @SerialName("general.error_in_loading")
    val errorOccurredInLoading: StringDesc.Raw = StringDesc.Raw("#db2c18Произошла ошибка при загрузке инвентаря"),
    @SerialName("general.on_join_format")
    val onJoinFormat: StringDesc.Raw = StringDesc.Raw("&7[&#0ecf41+&7] %player%"),
    @SerialName("general.on_leave_format")
    val onLeaveFormat: StringDesc.Raw = StringDesc.Raw("&7[&#cf0e0e-&7] %player%"),
    @SerialName("general.message_format")
    val messageFormat: StringDesc.Raw = StringDesc.Raw("#0ecf41%player%: &7%message%"),
    @SerialName("general.message_from_discord_format")
    val fromDiscordMessageFormat: StringDesc.Raw = StringDesc.Raw("#0ecf41%player%: &7%message%"),
    @SerialName("general.player_command")
    val onlyPlayerCommand: StringDesc.Raw = StringDesc.Raw("#db2c18Эта команда только для игроков"),
    @SerialName("general.input_server")
    val inputServerName: StringDesc.Raw = StringDesc.Raw(
        "#db2c18Введите название сервера, например: /syncserver SERVER_SMP"
    ),
)
