package ca.zootron.model.order;

import ca.zootron.model.map.Country;
import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import org.jetbrains.annotations.NotNull;

public final class BuildOrder extends Order {

    @NotNull
    public Location where;

    public BuildOrder(@NotNull Province who, @NotNull Location where) {
        super(who);
        this.where = where;
    }

    @Override
    public Country getIssuer() {
        return who.supplyCenter != null ? who.supplyCenter.controller : null;
    }

    @Override
    public String toString() {
        switch (where) {
            case LAND -> {
                return "build army " + who;
            }
            case THE_COAST -> {
                return "build fleet " + who;
            }
            case NORTH_COAST -> {
                return "build fleet " + who + "(north coast)";
            }
            case SOUTH_COAST -> {
                return "build fleet " + who + "(south coast)";
            }
        }

        throw new AssertionError("invalid Location enum");
    }
}
