package ca.zootron.model.map;

import ca.zootron.util.Pair;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class Province {
    public final String name;
    public final String abbreviaton;
    public final Map<Location, List<Pair<Province, Location>>> adjacencies;
    @Nullable
    public Pair<Unit, Location> unit;
    @Nullable
    public final SupplyCenter supplyCenter;

    public Province(String name, String abbreviaton, Map<Location, List<Pair<Province, Location>>> adjacencies, @Nullable Pair<Unit, Location> unit, @Nullable SupplyCenter supplyCenter) {
        this.name = name;
        this.abbreviaton = abbreviaton;
        this.adjacencies = adjacencies;
        this.unit = unit;
        this.supplyCenter = supplyCenter;
    }

    public static class SupplyCenter {
        @Nullable
        public Country controller;

        public SupplyCenter(@Nullable Country controller) {
            this.controller = controller;
        }
    }

    public enum Location {
        LAND,
        THE_COAST,
        NORTH_COAST,
        SOUTH_COAST
    }
}
