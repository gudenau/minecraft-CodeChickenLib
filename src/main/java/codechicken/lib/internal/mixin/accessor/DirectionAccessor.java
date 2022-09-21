package codechicken.lib.internal.mixin.accessor;

import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Direction.class)
public interface DirectionAccessor {
    @Accessor("BY_3D_DATA") static Direction[] getBy3dData() { throw new AssertionError(); }
}
