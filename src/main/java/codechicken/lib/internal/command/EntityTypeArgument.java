package codechicken.lib.internal.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

import static codechicken.lib.CodeChickenLib.MOD_ID;
import static codechicken.lib.internal.mixin.accessor.ResourceKeyArgumentAccessor.invokeGetRegistryKeyType;

/**
 * Created by covers1624 on 17/9/20.
 */
public class EntityTypeArgument {

    private static final DynamicCommandExceptionType MISSING = new DynamicCommandExceptionType(p ->
            Component.translatable(MOD_ID + ":argument.entity_type.invalid", p)
    );

    public static ResourceKeyArgument<EntityType<?>> entityType() {
        return ResourceKeyArgument.key(Registry.ENTITY_TYPE_REGISTRY);
    }

    public static Holder<EntityType<?>> getEntityType(CommandContext<CommandSourceStack> stack, String name) throws CommandSyntaxException {
        return invokeGetRegistryKeyType(stack, name, Registry.ENTITY_TYPE_REGISTRY, MISSING);
    }
}
