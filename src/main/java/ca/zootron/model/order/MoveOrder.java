package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class MoveOrder extends Order {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MoveOrder moveOrder = (MoveOrder) o;
        return destinationProvince.equals(moveOrder.destinationProvince) && destinationLocation == moveOrder.destinationLocation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), destinationProvince, destinationLocation);
    }
}
