package ca.zootron.model.order;

import ca.zootron.model.map.Country;
import ca.zootron.model.map.Province;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class ConvoyOrder extends SupportableOrder {

    @NotNull
    public Province from;
    @NotNull
    public Province to;

    public ConvoyOrder(@NotNull Province who, @NotNull Province from, @NotNull Province to) {
        super(who);
        this.from = from;
        this.to = to;
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
        ConvoyOrder that = (ConvoyOrder) o;
        return from.equals(that.from) && to.equals(that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from, to);
    }

    @Override
    public String toString() {
        return who + " convoys " + from + " - " + to;
    }
}
