/* This file is a part of the In-Game Data Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-data-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.github.cottonmc.clientcommands.ArgumentBuilders
import io.github.cottonmc.clientcommands.Feedback
import net.minecraft.server.command.CommandSource
import net.minecraft.text.StringTextComponent
import net.minecraft.text.TranslatableTextComponent

object IGDFHelpCommand : Command {
    override fun register(root: LiteralArgumentBuilder<CommandSource>) {
        root.then(ArgumentBuilders.literal("help").executes {
            Feedback.sendFeedback(TranslatableTextComponent("command.igdf.help.title"))
            Feedback.sendFeedback(StringTextComponent("/igdf help: ").append(TranslatableTextComponent("command.igdf.help.help")))
            Feedback.sendFeedback(StringTextComponent("/igdf generate: ").append(TranslatableTextComponent("command.igdf.help.generate")))
            Feedback.sendFeedback(StringTextComponent("/igdf generateset: ").append(TranslatableTextComponent("command.igdf.help.generateset")))
            Feedback.sendFeedback(StringTextComponent("/igdf packmcmeta: ").append(TranslatableTextComponent("command.igdf.help.packmcmeta")))
            Feedback.sendFeedback(StringTextComponent("/igdf changeoutputpath: ").append(TranslatableTextComponent("command.igdf.help.changeoutputpath")))
            1
        })
    }
}
