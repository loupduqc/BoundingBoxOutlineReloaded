package com.irtimaled.bbor.common;

import com.irtimaled.bbor.common.events.VillageRemoved;
import com.irtimaled.bbor.common.models.BoundingBoxVillage;
import com.irtimaled.bbor.common.models.Coords;
import net.minecraft.server.v1_13_R2.PersistentVillage;
import net.minecraft.server.v1_13_R2.Village;
import net.minecraft.server.v1_13_R2.VillageDoor;
import net.minecraft.server.v1_13_R2.WorldServer;

import java.util.*;

class VillageProcessor {
    private final BoundingBoxCache boundingBoxCache;

    private Map<Integer, BoundingBoxVillage> villageCache = new HashMap<>();
    private int dimensionId;

    VillageProcessor(int dimensionId, BoundingBoxCache boundingBoxCache) {
        this.dimensionId = dimensionId;
        this.boundingBoxCache = boundingBoxCache;
    }

    void process(WorldServer world) {
        Map<Integer, BoundingBoxVillage> oldVillages = new HashMap<>(villageCache);
        Map<Integer, BoundingBoxVillage> newVillages = new HashMap<>();
        PersistentVillage villageCollection = world.af();
        for (Village village : villageCollection.getVillages()) {
            int villageId = village.hashCode();
            BoundingBoxVillage newVillage = oldVillages.get(villageId);
            if (areEquivalent(village, newVillage)) {
                oldVillages.remove(villageId);
            } else {
                newVillage = buildBoundingBox(village);
                boundingBoxCache.addBoundingBox(newVillage);
            }
            newVillages.put(villageId, newVillage);
        }
        for (BoundingBoxVillage village : oldVillages.values()) {
            boundingBoxCache.removeBoundingBox(village);
            EventBus.publish(new VillageRemoved(dimensionId, village));
        }
        villageCache = newVillages;
    }

    private static Set<Coords> getDoorsFromVillage(Village village) {
        Set<Coords> doors = new HashSet<>();
        List<VillageDoor> doorInfoList = village.f();
        for (VillageDoor doorInfo : doorInfoList) {
            doors.add(new Coords(doorInfo.d()));
        }
        return doors;
    }

    private boolean areEquivalent(Village village, BoundingBoxVillage newVillage) {
        if (newVillage == null) return false;
        Coords center = new Coords(village.a());
        int radius = village.b();
        Set<Coords> doors = getDoorsFromVillage(village);
        boolean spawnsIronGolems = VillageHelper.shouldSpawnIronGolems(village.e(), doors.size());
        int villageHash = VillageHelper.computeHash(center, radius, spawnsIronGolems, doors);
        return newVillage.getVillageHash() == villageHash;
    }

    private BoundingBoxVillage buildBoundingBox(Village village) {
        Coords center = new Coords(village.a());
        int radius = village.b();
        Set<Coords> doors = getDoorsFromVillage(village);
        return BoundingBoxVillage.from(center, radius, village.hashCode(), village.e(), doors);
    }

    void clear() {
        villageCache.clear();
    }
}
