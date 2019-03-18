package com.irtimaled.bbor.common;

import com.irtimaled.bbor.common.models.BoundingBox;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BoundingBoxCache {
    private Map<BoundingBox, Set<BoundingBox>> cache = new ConcurrentHashMap<>();

    public Map<BoundingBox, Set<BoundingBox>> getBoundingBoxes() {
        return cache;
    }

    void clear() {
        cache.clear();
    }

    public boolean isCached(BoundingBox key) {
        return cache.containsKey(key);
    }

    public void addBoundingBoxes(BoundingBox key, Set<BoundingBox> boundingBoxes) {
        cache.put(key, boundingBoxes);
    }

    public void addBoundingBox(BoundingBox key) {
        if(isCached(key)) return;

        Set<BoundingBox> boundingBoxes = new HashSet<>();
        boundingBoxes.add(key);
        addBoundingBoxes(key, boundingBoxes);
    }

    void removeBoundingBox(BoundingBox key) {
        cache.remove(key);
    }
}
