package codechicken.lib.internal.mixin.accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(MenuType.class)
public interface MenuTypeAccessor<T extends AbstractContainerMenu> {
    @Accessor default MenuType.MenuSupplier<T> getConstructor() { throw new AssertionError(); }
}
