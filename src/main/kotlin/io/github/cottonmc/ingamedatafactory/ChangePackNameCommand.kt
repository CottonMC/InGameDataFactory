/* This file is a part of the In-Game JSON Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-json-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.cottonmc.clientcommands.ArgumentBuilders
import io.github.cottonmc.clientcommands.ClientCommands
import net.minecraft.server.command.CommandSource
import net.minecraft.text.TranslatableTextComponent

object ChangePackNameCommand {
    fun register(dispatcher: CommandDispatcher<CommandSource>) {
        dispatcher.register(
            ArgumentBuilders.literal("changedataoutputpath").then(
                ArgumentBuilders.argument(
                    "name",
                    StringArgumentType.string()
                ).executes {
                    val oldName = IngameDataFactory.outputPath
                    val newName = StringArgumentType.getString(it, "name")
                    IngameDataFactory.outputPath = newName
                    ClientCommands.sendFeedback(TranslatableTextComponent("command.igdf.changedataoutputpath.changed", oldName, newName))
                    1
                }
            )
        )
    }
}
