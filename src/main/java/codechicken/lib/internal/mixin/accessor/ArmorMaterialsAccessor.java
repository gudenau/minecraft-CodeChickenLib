package codechicken.lib.internal.mixin.accessor;

import net.minecraft.world.item.ArmorMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArmorMaterials.class)
public interface ArmorMaterialsAccessor {
    @Accessor("HEALTH_PER_SLOT") static int[] getHealthPerSlot() { return new int[4]; /* Makes Idea happy. */ }
}
