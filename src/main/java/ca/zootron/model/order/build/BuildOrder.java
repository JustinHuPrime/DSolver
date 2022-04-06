package ca.zootron.model.order.build;

import ca.zootron.model.map.Country;
import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import ca.zootron.util.IllegalOrderException;
import org.jetbrains.annotations.NotNull;

public final class BuildOrder extends BuildPhaseOrder {

    @NotNull
    public final Location where;

    /**
     * Build a unit in a supply center at some location
     *
     * <ul>
     *     <li>SC must be owned but not occupied by a unit</li>
     *     <li>Location must be a valid location for the province</li>
     * </ul>
     */
    public BuildOrder(@NotNull Province who, @NotNull Location where) {
        super(who);
        this.where = where;

        if (who.supplyCenter == null) {
            throw new IllegalOrderException("can't issue a build order to a province without a supply center");
        } else if (who.unit != null) {
            throw new IllegalOrderException("can't issue a build order to an occupied supply center");
        } else if (!who.adjacencies.containsKey(where)) {
            throw new IllegalOrderException("can't issue a build order with an invalid location for the province");
        }
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
