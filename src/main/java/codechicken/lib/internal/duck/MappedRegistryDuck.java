package codechicken.lib.internal.duck;

import net.minecraft.core.Holder;

public interface MappedRegistryDuck<T> {
    Holder<T> getDelegateOrThrow(T value);
    Holder<T> getHolderOrThrow(T value);
}
