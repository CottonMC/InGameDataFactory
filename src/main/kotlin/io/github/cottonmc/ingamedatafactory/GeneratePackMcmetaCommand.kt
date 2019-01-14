/* This file is a part of the In-Game JSON Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-json-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.loader.FabricLoader
import net.minecraft.server.command.ServerCommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.TranslatableTextComponent
import java.io.File
import java.nio.file.Files

object GeneratePackMcmetaCommand {
    @JvmStatic
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            ServerCommandManager.literal("generatepackmcmeta").executes {
                val file = File(FabricLoader.INSTANCE.gameDirectory, "resourcepacks/${IngameDataFactory.outputPath}/pack.mcmeta")
                if (file.exists())
                    throw GenerateCommand.FILE_ALREADY_EXISTS.create(file.toRelativeString(FabricLoader.INSTANCE.gameDirectory))

                Files.createDirectories(file.parentFile.toPath())
                file.writeText("""{ "pack": { "pack_format": 4, "description": "Automatically generated." } }""")
                it.source.sendFeedback(TranslatableTextComponent("command.igdf.generatedata.generated", "pack.mcmeta"), true)
                1
            }
        )
    }
}
