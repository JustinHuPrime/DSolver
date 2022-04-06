package ca.zootron.model.order.retreat;

import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public final class RetreatDisbandOrder extends RetreatPhaseOrder {

    /**
     * Disband a dislodged unit
     *
     * <ul>
     *     <li>No specific validity requirements</li>
     * </ul>
     */
    public RetreatDisbandOrder(@NotNull Province who) {
        super(who);
    }

    @Override
    public String toString() {
        return "disband " + who;
    }
}
