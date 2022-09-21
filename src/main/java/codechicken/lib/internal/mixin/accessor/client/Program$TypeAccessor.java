package codechicken.lib.internal.mixin.accessor.client;

import com.mojang.blaze3d.shaders.Program;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(Program.Type.class)
public interface Program$TypeAccessor {
    @Invoker int invokeGetGlType();
}
