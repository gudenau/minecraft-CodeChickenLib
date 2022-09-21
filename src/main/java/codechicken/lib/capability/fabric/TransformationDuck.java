package codechicken.lib.capability.fabric;

import com.mojang.math.Matrix3f;
import org.jetbrains.annotations.NotNull;

public interface TransformationDuck {
    @NotNull Matrix3f getNormalMatrix();
}
