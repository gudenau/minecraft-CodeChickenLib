/*
 * This file is part of CodeChickenLib.
 * Copyright (c) 2018, covers1624, All rights reserved.
 *
 * CodeChickenLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * CodeChickenLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CodeChickenLib. If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package codechicken.lib.model.pipeline.transformers;

import codechicken.lib.internal.mixin.accessor.DirectionAccessor;
import codechicken.lib.model.Quad.Vertex;
import codechicken.lib.model.pipeline.IPipelineElementFactory;
import codechicken.lib.model.pipeline.QuadTransformer;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;

import static net.minecraft.core.Direction.AxisDirection.NEGATIVE;
import static net.minecraft.core.Direction.AxisDirection.POSITIVE;

/**
 * This transformer is a little complicated.
 * Basically a Facade / Cover can use this to 'kick' the edges
 * in of quads to fix z-Fighting in the corners.
 * Use it by specifying the side of the block you are on,
 * the bitmask for where the other Facades / Cover's are,
 * the bounding box of the facade, NOT the hole piece,
 * and the thickness of your Facade / Cover, this is used
 * as the kick amount.
 *
 * @author covers1624
 */
public class QuadCornerKicker extends QuadTransformer {

    // The factory for pipeline creation.
    public static final IPipelineElementFactory<QuadCornerKicker> FACTORY = QuadCornerKicker::new;

    // Simple horizonal lookups.
    public static int[][] horizonals = new int[][] {
            // Around Y axis, NSWE.
            { 2, 3, 4, 5 }, //
            { 2, 3, 4, 5 }, //

            // Around Z axis, DUWE.
            { 0, 1, 4, 5 }, //
            { 0, 1, 4, 5 }, //

            // Around X axis, DUNS.
            { 0, 1, 2, 3 }, //
            { 0, 1, 2, 3 }
    };

    private int mySide;
    private int facadeMask;
    private AABB box;
    private double thickness;

    QuadCornerKicker() {
        super();
    }

    /**
     * Set's the side this Facade / Cover is attached to.
     *
     * @param side The side.
     */
    public void setSide(int side) {
        mySide = side;
    }

    /**
     * Sets the bitmask of Facades / Covers in the blockspace.
     * This is as simple as, mask = (1 << side)
     *
     * @param mask The mask.
     */
    public void setFacadeMask(int mask) {
        facadeMask = mask;
    }

    /**
     * Sets the bounding box of the Facade / Cover,
     * this should be the full box, not just a piece
     * of the hole's 'ring'.
     *
     * @param box The BoundingBox.
     */
    public void setBox(AABB box) {
        this.box = box;
    }

    /**
     * Sets the amount to kick the vertex in by,
     * this is your facades thickness.
     *
     * @param thickness The thickness.
     */
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    @Override
    public boolean transform() {
        int side = quad.orientation.ordinal();
        if (side != mySide && side != (mySide ^ 1)) {
            for (int hoz : horizonals[mySide]) {
                if (side != hoz && side != (hoz ^ 1)) {
                    if ((facadeMask & (1 << hoz)) != 0) {
                        Corner corner = Corner.fromSides(mySide ^ 1, side, hoz);
                        for (Vertex vertex : quad.vertices) {
                            float x = vertex.vec[0];
                            float y = vertex.vec[1];
                            float z = vertex.vec[2];
                            if (epsComp(x, corner.pX(box)) && epsComp(y, corner.pY(box)) && epsComp(z, corner.pZ(box))) {
                                Vec3i vec = DirectionAccessor.getBy3dData()[hoz].getNormal();
                                x -= vec.getX() * thickness;
                                y -= vec.getY() * thickness;
                                z -= vec.getZ() * thickness;
                                vertex.vec[0] = x;
                                vertex.vec[1] = y;
                                vertex.vec[2] = z;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public enum Corner {

        MIN_X_MIN_Y_MIN_Z(NEGATIVE, NEGATIVE, NEGATIVE),
        MIN_X_MIN_Y_MAX_Z(NEGATIVE, NEGATIVE, POSITIVE),
        MIN_X_MAX_Y_MIN_Z(NEGATIVE, POSITIVE, NEGATIVE),
        MIN_X_MAX_Y_MAX_Z(NEGATIVE, POSITIVE, POSITIVE),

        MAX_X_MIN_Y_MIN_Z(POSITIVE, NEGATIVE, NEGATIVE),
        MAX_X_MIN_Y_MAX_Z(POSITIVE, NEGATIVE, POSITIVE),
        MAX_X_MAX_Y_MIN_Z(POSITIVE, POSITIVE, NEGATIVE),
        MAX_X_MAX_Y_MAX_Z(POSITIVE, POSITIVE, POSITIVE);

        private final AxisDirection xAxis;
        private final AxisDirection yAxis;
        private final AxisDirection zAxis;

        private static final int[] sideMask = { 0, 2, 0, 1, 0, 4 };

        Corner(AxisDirection xAxis, AxisDirection yAxis, AxisDirection zAxis) {
            this.xAxis = xAxis;
            this.yAxis = yAxis;
            this.zAxis = zAxis;
        }

        /**
         * Used to find what corner is at the 3 sides.
         * This method assumes you pass in the X axis side, Y axis side, and Z axis side,
         * it will NOT complain about an invalid side, you will just get garbage data.
         * This method also does not care what order the 3 axes are in.
         *
         * @param sideA Side one.
         * @param sideB Side two.
         * @param sideC Side three.
         * @return The corner at the 3 sides.
         */
        public static Corner fromSides(int sideA, int sideB, int sideC) {
            // <3 Chicken-Bones.
            return values()[sideMask[sideA] | sideMask[sideB] | sideMask[sideC]];
        }

        public float pX(AABB box) {
            return (float) (xAxis == NEGATIVE ? box.minX : box.maxX);
        }

        public float pY(AABB box) {
            return (float) (yAxis == NEGATIVE ? box.minY : box.maxY);
        }

        public float pZ(AABB box) {
            return (float) (zAxis == NEGATIVE ? box.minZ : box.maxZ);
        }
    }
}
