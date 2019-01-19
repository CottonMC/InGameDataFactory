/* This file is a part of the In-Game Data Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-data-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.server.command.CommandSource

interface Command {
    fun register(dispatcher: CommandDispatcher<CommandSource>)
}
