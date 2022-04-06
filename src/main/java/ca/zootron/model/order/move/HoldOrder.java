package ca.zootron.model.order.move;

import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public final class HoldOrder extends MovePhaseOrder {

    /**
     * Unit stays in place
     *
     * <ul>
     *     <li>No specific validity requirements</li>
     * </ul>
     */
    public HoldOrder(@NotNull Province who) {
        super(who);
    }

    @Override
    public String toString() {
        return who + " holds";
    }
}
