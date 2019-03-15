package com.irtimaled.bbor.common.messages;

import com.irtimaled.bbor.common.models.AbstractBoundingBox;

public class RemoveBoundingBox {
    private static final String NAME = "bbor:remove_bounding_box";

    public static PayloadBuilder getPayload(int dimensionId, AbstractBoundingBox key) {
        if (!BoundingBoxSerializer.canSerialize(key)) return null;

        PayloadBuilder builder = PayloadBuilder.clientBound(NAME)
                .writeVarInt(dimensionId);
        BoundingBoxSerializer.serialize(key, builder);
        return builder;
    }
}
