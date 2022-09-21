package codechicken.lib.internal.mixin.accessor.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(BlockRenderDispatcher.class)
public interface BlockRenderDispatcherAccessor {
    @Accessor BlockEntityWithoutLevelRenderer getBlockEntityRenderer();
    
    @Accessor ModelBlockRenderer getModelRenderer();
    @Mutable @Accessor void setModelRenderer(ModelBlockRenderer value);
    
    @Accessor LiquidBlockRenderer getLiquidBlockRenderer();
    @Mutable @Accessor void setLiquidBlockRenderer(LiquidBlockRenderer value);
}
