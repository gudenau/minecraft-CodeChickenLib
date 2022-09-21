package codechicken.lib.internal.command.client;

import codechicken.lib.internal.HighlightHandler;
import codechicken.lib.raytracer.RayTracer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import static codechicken.lib.util.LambdaUtils.tryOrNull;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

/**
 * Created by covers1624 on 25/3/22.
 */
public class HighlightCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("ccl")
                .then(literal("highlight")
                        .then(literal("set")
                                .executes(HighlightCommand::setHighlightRayTrace)
                                .then(argument("pos", BlockPosArgument.blockPos())
                                        .executes(HighlightCommand::setHighlightArg)
                                )
                        )
                        .then(literal("clear")
                                .executes(HighlightCommand::clearHighlight)
                        )
                        .then(literal("toggle_depth")
                                .executes(HighlightCommand::toggleDepth)
                        )
                        .then(literal("info")
                                .executes(HighlightCommand::dumpInfo)
                        )
                )
        );
    }

    private static int setHighlightRayTrace(CommandContext<FabricClientCommandSource> ctx) {
        BlockHitResult hitResult = RayTracer.retrace(Minecraft.getInstance().player, 3000, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            ctx.getSource().sendError(Component.literal("Not looking at a block."));
            return 0;
        }
        return setHighlight(ctx, hitResult.getBlockPos());
    }

    private static int setHighlightArg(CommandContext<FabricClientCommandSource> ctx) throws CommandSyntaxException {
        return setHighlight(ctx, BlockPosArgument.getSpawnablePos(ctx, "pos"));
    }

    private static int setHighlight(CommandContext<FabricClientCommandSource> ctx, BlockPos pos) {
        FabricClientCommandSource source = ctx.getSource();
        if (HighlightHandler.highlight == null) {
            source.sendFeedback(Component.literal("Set highlight at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()));
        } else {
            BlockPos prev = HighlightHandler.highlight;
            source.sendFeedback(Component.literal("Moved highlight from " + prev.getX() + ", " + prev.getY() + ", " + prev.getZ() + " to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()));
        }
        HighlightHandler.highlight = pos;
        return 0;
    }

    private static int clearHighlight(CommandContext<FabricClientCommandSource> ctx) {
        FabricClientCommandSource source = ctx.getSource();
        if (HighlightHandler.highlight == null) {
            source.sendError(Component.literal("Highlight not set."));
            return 0;
        }
        HighlightHandler.highlight = null;
        HighlightHandler.useDepth = true;
        source.sendFeedback(Component.literal("Highlight position cleared."));
        return 0;
    }

    private static int toggleDepth(CommandContext<FabricClientCommandSource> ctx) {
        FabricClientCommandSource source = ctx.getSource();
        if (HighlightHandler.highlight == null) {
            source.sendError(Component.literal("Highlight not set."));
            return 0;
        }

        HighlightHandler.useDepth = !HighlightHandler.useDepth;

        if (HighlightHandler.useDepth) {
            source.sendFeedback(Component.literal("Enabled highlight depth."));
        } else {
            source.sendFeedback(Component.literal("Disabled highlight depth."));
        }
        return 0;
    }

    private static int dumpInfo(CommandContext<FabricClientCommandSource> ctx) {
        FabricClientCommandSource source = ctx.getSource();
        BlockPos pos = HighlightHandler.highlight;
        if (HighlightHandler.highlight == null) {
            source.sendError(Component.literal("Highlight not set."));
            return 0;
        }

        ClientLevel level = Minecraft.getInstance().level;
        assert level != null;

        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        BlockEntity tile = level.getBlockEntity(pos);
        StringBuilder builder = new StringBuilder("\nBlock info:\n");
        builder.append("  BlockPos:      ").append(String.format("x:%s, y:%s, z:%s", pos.getX(), pos.getY(), pos.getZ())).append("\n");
        builder.append("  Block Class:   ").append(tryOrNull(() -> block.getClass())).append("\n");
        builder.append("  Registry Name: ").append(tryOrNull(() -> Registry.BLOCK.getKey(block))).append("\n");
        builder.append("  State:         ").append(state).append("\n");
        builder.append("Tile at position\n");
        builder.append("  Tile Class:    ").append(tryOrNull(() -> tile.getClass())).append("\n");
        builder.append("  Tile Id:       ").append(tryOrNull(() -> Registry.BLOCK_ENTITY_TYPE.getKey(tile.getType()))).append("\n");
        builder.append("  Tile NBT:      ").append(tryOrNull(() -> tile.saveWithoutMetadata())).append("\n");
        source.sendFeedback(Component.literal(builder.toString()));

        return 0;
    }
}
