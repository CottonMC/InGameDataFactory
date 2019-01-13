/* This file is a part of the In-Game JSON Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-json-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.server.command.ServerCommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.StringTextComponent

object ChangePackNameCommand {
    @JvmStatic
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            ServerCommandManager.literal("changedataoutputpath").then(
                ServerCommandManager.argument(
                    "name",
                    StringArgumentType.string()
                ).executes {
                    val oldName = IngameDataFactory.outputPath
                    val newName = StringArgumentType.getString(it, "name")
                    IngameDataFactory.outputPath = newName
                    it.source.sendFeedback(StringTextComponent("Changed pack name from $oldName to $newName"), true)
                    1
                }
            )
        )
    }
}
