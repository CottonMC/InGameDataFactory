/* This file is a part of the In-Game Data Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-data-factory
 */
package io.github.cottonmc.ingamedatafactory

import io.github.cottonmc.clientcommands.ClientCommands
import net.fabricmc.api.ClientModInitializer

object IngameDataFactory : ClientModInitializer {
    internal var outputPath = "ingame-data-factory"
    private val commands = listOf(
        GenerateCommand,
        GenerateMultipleCommand,
        GeneratePackMcmetaCommand,
        ChangePackNameCommand,
        IGDFHelpCommand
    )

    override fun onInitializeClient() {
        commands.forEach { ClientCommands.registerCommand(it::register) }
    }
}
