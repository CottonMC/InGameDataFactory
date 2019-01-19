/* This file is a part of the In-Game Data Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-data-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import io.github.cottonmc.clientcommands.ArgumentBuilders
import io.github.cottonmc.clientcommands.Feedback
import net.minecraft.server.command.CommandSource
import net.minecraft.text.StringTextComponent
import net.minecraft.text.TranslatableTextComponent

object IGDFHelpCommand : Command {
    override fun register(dispatcher: CommandDispatcher<CommandSource>) {
        dispatcher.register(ArgumentBuilders.literal("igdfhelp").executes {
            Feedback.sendFeedback(TranslatableTextComponent("command.igdf.help.title"))
            Feedback.sendFeedback(StringTextComponent("/generatedata: ").append(TranslatableTextComponent("command.igdf.help.generatedata")))
            Feedback.sendFeedback(StringTextComponent("/generatedataset: ").append(TranslatableTextComponent("command.igdf.help.generatedataset")))
            Feedback.sendFeedback(StringTextComponent("/generatepackmcmeta: ").append(TranslatableTextComponent("command.igdf.help.generatepackmcmeta")))
            Feedback.sendFeedback(StringTextComponent("/changedataoutputpath: ").append(TranslatableTextComponent("command.igdf.help.changedataoutputpath")))
            1
        })
    }
}
