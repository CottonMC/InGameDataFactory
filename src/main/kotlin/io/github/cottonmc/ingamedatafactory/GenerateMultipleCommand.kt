/* This file is a part of the In-Game JSON Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-json-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import io.github.cottonmc.clientcommands.ArgumentBuilders
import io.github.cottonmc.jsonfactory.gens.Gens
import net.minecraft.server.command.CommandSource

object GenerateMultipleCommand {
    private val sets = mapOf(
        "block" to setOf(Gens.basicBlockModel, Gens.basicBlockState, Gens.basicLootTable, Gens.basicBlockItemModel),
        "slab" to Gens.Variants.allSlabs,
        "stairs" to Gens.Variants.allStairs,
        "button" to Gens.Variants.allButtons,
        "fence" to Gens.Variants.allFences,
        "wall" to Gens.Variants.allWalls,
        "sign" to Gens.Variants.allSigns,
        "fence_gate" to Gens.Variants.allFenceGates,
        "trapdoor" to Gens.Variants.allTrapdoors,
        "door" to Gens.Variants.allDoors
    )

    fun register(dispatcher: CommandDispatcher<CommandSource>) {
        val base = ArgumentBuilders.literal("generatedataset")

        for ((name, set) in sets) {
            base.then(
                ArgumentBuilders.argument(
                    "identifier",
                    IdentifierArgumentType
                ).then(
                    ArgumentBuilders.literal(name).executes {
                        GenerateCommand.runAll(it, set)
                        1
                    }
                )
            )
        }

        dispatcher.register(base)
    }
}
