/* This file is a part of the In-Game JSON Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-json-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import io.github.cottonmc.jsonfactory.data.Identifier
import io.github.cottonmc.jsonfactory.gens.ContentGenerator
import io.github.cottonmc.jsonfactory.gens.Gens
import net.fabricmc.loader.FabricLoader
import net.minecraft.server.command.ServerCommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.StringTextComponent
import java.io.File
import java.nio.file.Files

object GenerateCommand {
    val FILE_ALREADY_EXISTS = DynamicCommandExceptionType {
        LiteralMessage("File $it already exists")
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

    @JvmStatic
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(ServerCommandManager.literal("generatedata").then(
            ServerCommandManager.argument(
                "identifier",
                IdentifierArgumentType
            ).apply {
                for ((name, gen) in values) {
                    then(
                        ServerCommandManager.literal(name).executes {
                            run(it, gen)
                            1
                        }
                    )
                }
            }
        ))
    }

    fun runAll(context: CommandContext<ServerCommandSource>, gens: Iterable<ContentGenerator>) {
        for (gen in gens)
            run(context, gen)
    }

    private fun run(context: CommandContext<ServerCommandSource>, gen: ContentGenerator = Gens.basicBlockModel) {
        val id = context.getArgument("identifier", Identifier::class.java)
        val packDir = File(FabricLoader.INSTANCE.gameDirectory, "resourcepacks/${IngameDataFactory.packName}")
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
                context.source.sendFeedback(StringTextComponent("Generated " + file.toRelativeString(packDir)), true)
            } else {
                throw FILE_ALREADY_EXISTS.create(file.toRelativeString(FabricLoader.INSTANCE.gameDirectory))
            }
        }
    }
}
