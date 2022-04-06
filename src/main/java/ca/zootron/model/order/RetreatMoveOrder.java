package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import ca.zootron.util.IllegalOrderException;
import org.jetbrains.annotations.NotNull;

public final class RetreatMoveOrder extends Order {

    @NotNull
    public final Province.ProvinceLocation destination;

    /**
     * Retreat a disloged unit from a province
     *
     * Destination must be reachable from current location
     */
    public RetreatMoveOrder(@NotNull Province who, @NotNull Province.ProvinceLocation destination) {
        super(who);
        this.destination = destination;

        assert who.dislodgedUnit != null;
        if (!(who.adjacencies.get(who.dislodgedUnit.location).contains(destination))) {
            throw new IllegalOrderException("can't issue a retreat-move order with an unreachable destination");
        }
    }

    @Override
    public String toString() {
        if (destination.location() == Location.NORTH_COAST || destination.location() == Location.SOUTH_COAST) {
            return who + " - " + destination.province() + " (" + destination.location() + ")";
        } else {
            return who + " - " + destination.province();
        }
    }
}
