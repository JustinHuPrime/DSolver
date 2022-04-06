package ca.zootron.model.order.build;

import ca.zootron.model.map.Province;
import ca.zootron.model.order.Order;
import org.jetbrains.annotations.NotNull;

public abstract class BuildPhaseOrder extends Order {

    /**
     * Generalized build phase order
     *
     * <ul>
     *     <li>No general restrictions</li>
     * </ul>
     */
    public BuildPhaseOrder(@NotNull Province who) {
        super(who);
    }
}
