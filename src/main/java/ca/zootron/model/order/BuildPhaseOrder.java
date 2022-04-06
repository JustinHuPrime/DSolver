package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public abstract class BuildPhaseOrder extends Order {

    /**
     * Generalized build phase order
     *
     * No general restrictions
     */
    public BuildPhaseOrder(@NotNull Province who) {
        super(who);
    }
}
