package codechicken.lib.extend;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class BlockPosArgument extends net.minecraft.commands.arguments.coordinates.BlockPosArgument {

    public static BlockPos getSpawnablePosE(CommandContext<FabricClientCommandSource> commandContext, String string) throws CommandSyntaxException {
        BlockPos blockPos = ((Coordinates)commandContext.getArgument(string, Coordinates.class)).getBlockPos((CommandSourceStack)commandContext.getSource());
        if (!Level.isInSpawnableBounds(blockPos)) {
            throw ERROR_OUT_OF_BOUNDS.create();
        } else {
            return blockPos;
        }
    }
}
