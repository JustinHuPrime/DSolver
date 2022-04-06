package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import ca.zootron.util.Pair;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class MoveOrder extends Order {
    @NotNull
    public Pair<@NotNull Province, @NotNull Location> destination;

    public MoveOrder(@NotNull Province who, @NotNull Pair<@NotNull Province, @NotNull Location> destination) {
        super(who);
        this.destination = destination;
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
        return destination.equals(moveOrder.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), destination);
    }
}
