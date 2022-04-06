package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public abstract class BuildPhaseOrder extends Order {

    public BuildPhaseOrder(@NotNull Province who) {
        super(who);
    }
}
