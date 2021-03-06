package ca.zootron.model.order.move;

import ca.zootron.model.map.Province;
import ca.zootron.model.order.Order;
import ca.zootron.util.IllegalOrderException;
import org.jetbrains.annotations.NotNull;

public abstract class MovePhaseOrder extends Order {

    /**
     * Generalized move phase order to some province
     *
     * <ul>
     *     <li>Province must contain a unit</li>
     * </ul>
     */
    public MovePhaseOrder(@NotNull Province who) {
        super(who);

        if (who.unit == null) {
            throw new IllegalOrderException("can't issue a move phase order to an empty province");
        }
    }
}
