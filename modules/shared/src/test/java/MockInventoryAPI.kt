import com.astrainteractive.synk.api.local.LocalInventoryApi
import ru.astrainteractive.synk.core.model.PlayerDTO
import java.io.File

internal object MockInventoryAPI : LocalInventoryApi<Unit> {
    override fun savePlayer(player: PlayerDTO, type: LocalInventoryApi.TYPE) {
        Unit
    }

    override fun loadPlayerSaves(player: PlayerDTO): List<File> {
        return emptyList()
    }

    override fun readPlayerInventorySave(player: PlayerDTO, file: File): List<Unit> {
        return emptyList()
    }

    override fun readPlayerEnderChestSave(player: PlayerDTO, file: File): List<Unit> {
        return emptyList()
    }
}
