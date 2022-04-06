package ca.zootron.model.map;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Province {
    @NotNull
    public final String name;
    @NotNull
    public final Map<@NotNull Location, @NotNull List<@NotNull ProvinceLocation>> adjacencies;
    @Nullable
    public Unit unit;
    @Nullable
    public final SupplyCenter supplyCenter;

    public Province(@NotNull String name, @NotNull Map<@NotNull Location, List<@NotNull ProvinceLocation>> adjacencies, @Nullable Unit unit, @Nullable SupplyCenter supplyCenter) {
        this.name = name;
        this.adjacencies = adjacencies;
        this.unit = unit;
        this.supplyCenter = supplyCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Province province = (Province) o;
        return name.equals(province.name) && adjacencies.equals(province.adjacencies) && Objects.equals(unit, province.unit) && Objects.equals(supplyCenter, province.supplyCenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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

    public static record ProvinceLocation(@NotNull Province province, @NotNull Location location) {

    }
}
