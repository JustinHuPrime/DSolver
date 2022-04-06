package ca.zootron.model.map;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Province {

    @NotNull
    public final String name;
    @NotNull
    public final Map<@NotNull Location, @NotNull Set<@NotNull ProvinceLocation>> adjacencies;
    @Nullable
    public final SupplyCenter supplyCenter;
    @Nullable
    public Unit unit;
    @Nullable
    public Unit dislodgedUnit;

    public Province(@NotNull String name, @NotNull Map<@NotNull Location, Set<@NotNull ProvinceLocation>> adjacencies, @Nullable Unit unit, @Nullable SupplyCenter supplyCenter) {
        this.name = name;
        this.adjacencies = adjacencies;
        this.unit = unit;
        this.dislodgedUnit = null;
        this.supplyCenter = supplyCenter;
    }

    @Override
    public String toString() {
        return name;
    }

    public enum Location {
        LAND,
        THE_COAST,
        NORTH_COAST,
        SOUTH_COAST;

        @Override
        public String toString() {
            switch (this) {
                case LAND -> {
                    return "land";
                }
                case THE_COAST -> {
                    return "the water";
                }
                case NORTH_COAST -> {
                    return "north coast";
                }
                case SOUTH_COAST -> {
                    return "south coast";
                }
            }

            throw new AssertionError("invalid Location enum");
        }
    }

    public static class SupplyCenter {

        @Nullable
        public final Country originalController;
        @Nullable
        public Country controller;

        public SupplyCenter(@Nullable Country controller) {
            this.controller = this.originalController = controller;
        }
    }

    public static record ProvinceLocation(@NotNull Province province, @NotNull Location location) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ProvinceLocation that = (ProvinceLocation) o;
            return province.equals(that.province) && location == that.location;
        }

        @Override
        public int hashCode() {
            return Objects.hash(province, location);
        }
    }
}
