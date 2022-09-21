package codechicken.lib.internal.mixin;

import codechicken.lib.capability.fabric.TransformationDuck;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Transformation.class)
public abstract class TransformationMixin implements TransformationDuck {
    @Shadow @Final private Matrix4f matrix;
    
    @Unique private Matrix3f codechickenlib$normalTransform = null;
    
    @Override
    @NotNull
    public Matrix3f getNormalMatrix() {
        if (codechickenlib$normalTransform == null) {
            codechickenlib$normalTransform = new Matrix3f(matrix);
            codechickenlib$normalTransform.invert();
            codechickenlib$normalTransform.transpose();
        }
        
        return codechickenlib$normalTransform;
    }
}
