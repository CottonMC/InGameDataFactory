/* This file is a part of the In-Game Data Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-data-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.github.cottonmc.clientcommands.ArgumentBuilders
import io.github.cottonmc.clientcommands.Feedback
import net.minecraft.server.command.CommandSource
import net.minecraft.text.TranslatableTextComponent

object ChangePackNameCommand : Command {
    override fun register(root: LiteralArgumentBuilder<CommandSource>) {
        root.then(
            ArgumentBuilders.literal("changeoutputpath").then(
                ArgumentBuilders.argument(
                    "name",
                    StringArgumentType.string()
                ).executes {
                    val oldName = IngameDataFactory.outputPath
                    val newName = StringArgumentType.getString(it, "name")
                    IngameDataFactory.outputPath = newName
                    Feedback.sendFeedback(TranslatableTextComponent("command.igdf.changeoutputpath.changed", oldName, newName))
                    1
                }
            )
        )
    }
}
