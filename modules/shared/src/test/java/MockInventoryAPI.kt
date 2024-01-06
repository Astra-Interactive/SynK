import com.astrainteractive.synk.api.local.LocalInventoryApi
import ru.astrainteractive.synk.core.model.PlayerModel
import java.io.File

internal object MockInventoryAPI : LocalInventoryApi<Unit> {
    override fun savePlayer(playerModel: PlayerModel, type: LocalInventoryApi.TYPE) {
        Unit
    }

    override fun loadPlayerSaves(playerModel: PlayerModel): List<File> {
        return emptyList()
    }

    override fun readPlayerInventorySave(playerModel: PlayerModel, file: File): List<Unit> {
        return emptyList()
    }

    override fun readPlayerEnderChestSave(playerModel: PlayerModel, file: File): List<Unit> {
        return emptyList()
    }
}
