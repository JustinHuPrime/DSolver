package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import org.jetbrains.annotations.NotNull;

public final class RetreatMoveOrder extends Order {

    @NotNull
    public final Province.ProvinceLocation destination;

    public RetreatMoveOrder(@NotNull Province who, @NotNull Province.ProvinceLocation destination) {
        super(who);
        this.destination = destination;
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
