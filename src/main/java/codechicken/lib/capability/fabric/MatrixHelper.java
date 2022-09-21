package codechicken.lib.capability.fabric;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;

public interface MatrixHelper {
    static void multiplyBackwards(Matrix4f thiz, Matrix4f other) {
        var copy = other.copy();
        copy.multiply(thiz);
        thiz.load(copy);
    }
    
    static void multiplyBackwards(Matrix3f thiz, Matrix3f other) {
        var copy = other.copy();
        copy.mul(thiz);
        thiz.load(copy);
    }
}
