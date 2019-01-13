/* This file is a part of the In-Game JSON Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-json-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import io.github.cottonmc.jsonfactory.gens.Gens
import net.minecraft.server.command.ServerCommandManager
import net.minecraft.server.command.ServerCommandSource

object GenerateMultipleCommand {
    private val sets = mapOf(
        "block" to setOf(Gens.basicBlockModel, Gens.basicBlockState, Gens.basicLootTable, Gens.basicBlockItemModel),
        "slab" to setOf(Gens.Variants.slabBlockModel, Gens.Variants.slabBlockState, Gens.Variants.slabLootTable, Gens.Variants.slabItemModel),
        "stairs" to setOf(Gens.Variants.stairBlockModel, Gens.Variants.stairBlockState, Gens.Variants.stairLootTable, Gens.Variants.stairItemModel),
        "pressure_plate" to setOf(Gens.Variants.pressurePlateBlockModel, Gens.Variants.pressurePlateBlockState, Gens.Variants.pressurePlateLootTable, Gens.Variants.pressurePlateItemModel),
        "button" to setOf(Gens.Variants.buttonBlockModel, Gens.Variants.buttonBlockState, Gens.Variants.buttonLootTable, Gens.Variants.buttonItemModel),
        "fence" to setOf(Gens.Variants.fenceBlockModel, Gens.Variants.fenceBlockState, Gens.Variants.fenceLootTable, Gens.Variants.fenceItemModel),
        "wall" to setOf(Gens.Variants.wallBlockModel, Gens.Variants.wallBlockState, Gens.Variants.wallLootTable, Gens.Variants.wallItemModel),
        "sign" to setOf(Gens.Variants.signBlockModel, Gens.Variants.signBlockState, Gens.Variants.signLootTable, Gens.Variants.signItemModel)
    )

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        val base = ServerCommandManager.literal("generatedataset")

        for ((name, set) in sets) {
            base.then(
                ServerCommandManager.argument(
                    "identifier",
                    IdentifierArgumentType
                ).then(
                    ServerCommandManager.literal(name).executes {
                        GenerateCommand.runAll(it, set)
                        1
                    }
                )
            )
        }

        dispatcher.register(base)
    }
}
