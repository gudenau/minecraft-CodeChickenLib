package codechicken.lib.internal.mixin.accessor.client;

import com.mojang.blaze3d.shaders.Uniform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ShaderInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(ShaderInstance.class)
public interface ShaderInstanceAccessor {
    @Accessor List<Uniform> getUniforms();
}
