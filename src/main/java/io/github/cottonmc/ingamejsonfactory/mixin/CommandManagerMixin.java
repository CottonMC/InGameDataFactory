/* This file is a part of the In-Game JSON Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-json-factory
 */
package io.github.cottonmc.ingamejsonfactory.mixin;

import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.ingamejsonfactory.ChangePackNameCommand;
import io.github.cottonmc.ingamejsonfactory.GenerateCommand;
import io.github.cottonmc.ingamejsonfactory.GenerateMultipleCommand;
import io.github.cottonmc.ingamejsonfactory.GeneratePackMcmetaCommand;
import net.minecraft.server.command.ServerCommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommandManager.class)
public class CommandManagerMixin {
	@Shadow @Final private CommandDispatcher<ServerCommandSource> dispatcher;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onConstruct(boolean onDedicatedServer, CallbackInfo info) {
		if (!onDedicatedServer) {
			GenerateCommand.register(dispatcher);
			GenerateMultipleCommand.register(dispatcher);
			GeneratePackMcmetaCommand.register(dispatcher);
			ChangePackNameCommand.register(dispatcher);
		}
	}
}
