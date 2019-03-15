package com.irtimaled.bbor.common.messages;

import com.irtimaled.bbor.common.models.AbstractBoundingBox;

import java.util.Set;

public class AddBoundingBox {
    private static final String NAME = "bbor:add_bounding_box";

    public static PayloadBuilder getPayload(int dimensionId, AbstractBoundingBox key, Set<AbstractBoundingBox> boundingBoxes) {
        if (!BoundingBoxSerializer.canSerialize(key)) return null;

        PayloadBuilder builder = PayloadBuilder.clientBound(NAME)
                .writeVarInt(dimensionId);
        BoundingBoxSerializer.serialize(key, builder);
        if (boundingBoxes != null && boundingBoxes.size() > 1) {
            for (AbstractBoundingBox boundingBox : boundingBoxes) {
                BoundingBoxSerializer.serialize(boundingBox, builder);
            }
        }
        return builder;
    }
}
