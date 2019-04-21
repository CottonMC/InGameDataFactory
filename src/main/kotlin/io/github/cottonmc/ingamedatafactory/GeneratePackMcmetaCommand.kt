/* This file is a part of the In-Game Data Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-data-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.github.cottonmc.clientcommands.ArgumentBuilders
import io.github.cottonmc.clientcommands.Feedback
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.command.CommandSource
import net.minecraft.text.TranslatableTextComponent
import java.io.File
import java.nio.file.Files

object GeneratePackMcmetaCommand : Command {
    override fun register(root: LiteralArgumentBuilder<CommandSource>) {
        root.then(
            ArgumentBuilders.literal("packmcmeta").executes {
                val file = File(FabricLoader.getInstance().gameDirectory, "resourcepacks/${IngameDataFactory.outputPath}/pack.mcmeta")
                if (file.exists())
                    throw GenerateCommand.FILE_ALREADY_EXISTS.create(file.toRelativeString(FabricLoader.getInstance().gameDirectory))

                Files.createDirectories(file.parentFile.toPath())
                file.writeText("""{ "pack": { "pack_format": 4, "description": "Automatically generated." } }""")
                Feedback.sendFeedback(TranslatableTextComponent("command.igdf.generate.generated", "pack.mcmeta"))
                1
            }
        )
    }
}
