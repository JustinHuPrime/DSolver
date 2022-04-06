package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public final class HoldOrder extends MovePhaseOrder {

    public HoldOrder(@NotNull Province who) {
        super(who);
    }

    @Override
    public String toString() {
        return who + " holds";
    }
}
