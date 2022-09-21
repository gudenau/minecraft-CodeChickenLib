package codechicken.lib.internal.mixin.accessor;

import com.mojang.math.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Vector4f.class)
public interface Vector4fAccessor {
    @Accessor("x") void setX(float value);
    @Accessor("y") void setY(float value);
    @Accessor("z") void setZ(float value);
    @Accessor("w") void setW(float value);
}
