package codechicken.lib.internal.mixin.accessor.client;

import com.mojang.blaze3d.vertex.DefaultedVertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(DefaultedVertexConsumer.class)
public interface DefaultedVertexConsumerAccessor {
    @Accessor boolean getDefaultColorSet();
}
