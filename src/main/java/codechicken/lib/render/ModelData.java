package codechicken.lib.render;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class ModelData {
    public static final ModelData EMPTY = new ModelData(Map.of());
    
    private final Map<ModelProperty<?>, Object> properties;
    
    private ModelData(Map<ModelProperty<?>, Object> properties) {
        this.properties = Map.copyOf(properties);
    }
    
    @NotNull
    @Contract("-> new")
    public static Builder builder() {
        return new Builder(Map.of());
    }
    
    @NotNull
    @Contract("-> new")
    public Builder derive() {
        return new Builder(properties);
    }
    
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T get(@NotNull ModelProperty<T> property) {
        return (T) properties.get(property);
    }
    
    @NotNull
    public Set<ModelProperty<?>> getProperties() {
        return Collections.unmodifiableSet(properties.keySet());
    }
    
    public boolean has(@NotNull ModelProperty<?> property) {
        return properties.containsKey(property);
    }
    
    public static final class Builder {
        private final Map<ModelProperty<?>, Object> properties;
        
        private Builder(Map<ModelProperty<?>, Object> properties) {
            this.properties = new HashMap<>(properties);
        }
    
        @NotNull
        @Contract("-> new")
        public ModelData build() {
            return new ModelData(properties);
        }
        
        @NotNull
        @Contract("_, _ -> this")
        public <T> Builder with(@NotNull ModelProperty<T> property, @NotNull T value) {
            properties.put(property, value);
            return this;
        }
    }
    
    public static final class ModelProperty<T> {
        private final Predicate<T> predicate;
        
        public ModelProperty() {
            this((value) -> false);
        }
        
        public ModelProperty(@NotNull Predicate<T> predicate) {
            this.predicate = predicate;
        }
        
        public boolean test(T value) {
            return predicate.test(value);
        }
    }
}
