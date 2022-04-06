package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public final class HoldOrder extends SupportableOrder {

    public HoldOrder(@NotNull Province who) {
        super(who);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return who + " holds";
    }
}
