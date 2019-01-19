/* This file is a part of the In-Game Data Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-data-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import io.github.cottonmc.clientcommands.ArgumentBuilders
import io.github.cottonmc.clientcommands.Feedback
import io.github.cottonmc.jsonfactory.data.Identifier
import io.github.cottonmc.jsonfactory.gens.ContentGenerator
import io.github.cottonmc.jsonfactory.gens.Gens
import net.fabricmc.loader.FabricLoader
import net.minecraft.server.command.CommandSource
import net.minecraft.text.TranslatableTextComponent
import java.io.File
import java.nio.file.Files

object GenerateCommand : Command {
    val FILE_ALREADY_EXISTS = DynamicCommandExceptionType {
        TranslatableTextComponent("command.igdf.generatedata.file_already_exists", it)
    }

    private val values = mapOf(
        "block_model" to Gens.basicBlockModel,
        "blockstates" to Gens.basicBlockState,
        "block_item_model" to Gens.basicBlockItemModel,
        "item_model" to Gens.basicItemModel,
        "loot_table" to Gens.basicLootTable,
        "placeholder_block_texture" to Gens.placeholderTextureBlock,
        "placeholder_item_texture" to Gens.placeholderTextureItem
    )

    override fun register(dispatcher: CommandDispatcher<CommandSource>) {
        dispatcher.register(ArgumentBuilders.literal("generatedata").then(
            ArgumentBuilders.argument(
                "identifier",
                IdentifierArgumentType
            ).apply {
                for ((name, gen) in values) {
                    then(
                        ArgumentBuilders.literal(name).executes {
                            run(it, gen)
                            1
                        }
                    )
                }
            }
        ))
    }

    fun runAll(context: CommandContext<CommandSource>, gens: Iterable<ContentGenerator>) {
        for (gen in gens)
            run(context, gen)
    }

    private fun run(context: CommandContext<CommandSource>, gen: ContentGenerator) {
        val id = context.getArgument("identifier", Identifier::class.java)
        val packDir = File(FabricLoader.INSTANCE.gameDirectory, "resourcepacks/${IngameDataFactory.outputPath}")
        Files.createDirectories(packDir.toPath())

        gen.generate(id).forEach {
            val root = gen.resourceRoot.path
            val sep = File.separatorChar
            val namespace = id.namespace
            val directory = gen.path
            val fileName = id.path
            val extension = gen.extension
            val s = if (it.suffix.isEmpty()) "" else "_${it.suffix}"

            val fullName = "$root$sep$namespace$sep$directory$sep$fileName$s.$extension"

            val file = File(
                packDir, fullName
            )

            if (!file.exists()) {
                Files.createDirectories(file.parentFile.toPath())
                it.writeToFile(file)
                Feedback.sendFeedback(TranslatableTextComponent("command.igdf.generatedata.generated", file.toRelativeString(packDir)))
            } else {
                throw FILE_ALREADY_EXISTS.create(file.toRelativeString(FabricLoader.INSTANCE.gameDirectory))
            }
        }
    }
}
