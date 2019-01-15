/* This file is a part of the In-Game JSON Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-json-factory
 */
package io.github.cottonmc.ingamedatafactory

import io.github.cottonmc.clientcommands.ClientCommands
import net.fabricmc.api.ModInitializer

object IngameDataFactory : ModInitializer {
    internal var outputPath = "ingame-data-factory"

    override fun onInitialize() {
        ClientCommands.registerCommand(GenerateCommand::register)
        ClientCommands.registerCommand(GenerateMultipleCommand::register)
        ClientCommands.registerCommand(GeneratePackMcmetaCommand::register)
        ClientCommands.registerCommand(ChangePackNameCommand::register)
    }
}
