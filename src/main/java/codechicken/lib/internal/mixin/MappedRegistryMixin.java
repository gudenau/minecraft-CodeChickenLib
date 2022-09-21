package codechicken.lib.internal.mixin;

import codechicken.lib.internal.duck.MappedRegistryDuck;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.Optional;

@Mixin(MappedRegistry.class)
public abstract class MappedRegistryMixin<T> extends WritableRegistry<T> implements MappedRegistryDuck<T> {
    @Shadow @Final private Map<T, Holder.Reference<T>> byValue;
    
    private MappedRegistryMixin() {
        super(null, null);
    }
    
    @Override
    public Holder<T> getDelegateOrThrow(T value) {
        //TODO what does this want?
        return getHolderOrThrow(value);
    }
    
    @Override
    public Holder<T> getHolderOrThrow(T value) {
        return Optional.ofNullable(byValue.get(value)).orElseThrow(() -> new IllegalStateException("Missing value in " + key() + ": " + value));
    }
}
