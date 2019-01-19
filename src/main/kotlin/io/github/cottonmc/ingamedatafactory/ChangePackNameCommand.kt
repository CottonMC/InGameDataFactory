/* This file is a part of the In-Game Data Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-data-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.cottonmc.clientcommands.ArgumentBuilders
import io.github.cottonmc.clientcommands.Feedback
import net.minecraft.server.command.CommandSource
import net.minecraft.text.TranslatableTextComponent

object ChangePackNameCommand : Command {
    override fun register(dispatcher: CommandDispatcher<CommandSource>) {
        dispatcher.register(
            ArgumentBuilders.literal("changedataoutputpath").then(
                ArgumentBuilders.argument(
                    "name",
                    StringArgumentType.string()
                ).executes {
                    val oldName = IngameDataFactory.outputPath
                    val newName = StringArgumentType.getString(it, "name")
                    IngameDataFactory.outputPath = newName
                    Feedback.sendFeedback(TranslatableTextComponent("command.igdf.changedataoutputpath.changed", oldName, newName))
                    1
                }
            )
        )
    }
}
