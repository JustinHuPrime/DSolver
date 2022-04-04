package ca.zootron.model.map;

import ca.zootron.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Province {
    @NotNull
    public final String name;
    @NotNull
    public final Map<@NotNull Location, @NotNull List<@NotNull Pair<@NotNull Province, @NotNull Location>>> adjacencies;
    @Nullable
    public Pair<@NotNull Unit, @NotNull Location> unit;
    @Nullable
    public final SupplyCenter supplyCenter;

    public Province(@NotNull String name, @NotNull Map<@NotNull Location, List<@NotNull Pair<@NotNull Province, @NotNull Location>>> adjacencies, @Nullable Pair<@NotNull Unit, @NotNull Location> unit, @Nullable SupplyCenter supplyCenter) {
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
}
