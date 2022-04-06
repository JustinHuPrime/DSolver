package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import ca.zootron.util.IllegalOrderException;
import org.jetbrains.annotations.NotNull;

public final class MoveOrder extends MovePhaseOrder {

    @NotNull
    public final Province.ProvinceLocation destination;

    /**
     * Move a unit between two provinces
     *
     * Must be possible by adjacencies (implicitly forbids moves to the same place)
     */
    public MoveOrder(@NotNull Province who, @NotNull Province.ProvinceLocation destination) {
        super(who);
        this.destination = destination;

        assert who.unit != null;
        if (!((who.adjacencies.get(who.unit.location).contains(destination)) ||
              (who.isCoastal() && who.unit.location == Location.LAND && destination.province().isCoastal() && destination.location() == Location.LAND))) {
            throw new IllegalOrderException("can't issue a move order with an unreachable (even by convoy) destination");
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
