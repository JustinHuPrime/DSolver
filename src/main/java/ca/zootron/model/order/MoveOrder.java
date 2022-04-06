package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class MoveOrder extends SupportableOrder {
    @NotNull
    public Province destinationProvince;
    @NotNull
    public Location destinationLocation;

    public MoveOrder(@NotNull Province who, @NotNull Province destinationProvince, @NotNull Location destinationLocation) {
        super(who);
        this.destinationProvince = destinationProvince;
        this.destinationLocation = destinationLocation;
    }

    @Override
    public String toString() {
        if (destinationLocation == Location.NORTH_COAST || destinationLocation == Location.SOUTH_COAST) {
            return who + " - " + destinationProvince + " (" + destinationLocation + ")";
        } else {
            return who + " - " + destinationProvince;
        }
    }
}
