package codechicken.lib.internal.mixin.accessor.client;

import com.mojang.blaze3d.shaders.Program;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(Program.class)
public interface ProgramAccessor {
    @Invoker("<init>") static Program init(Program.Type type, int id, String string) { throw new AssertionError(); }
}
