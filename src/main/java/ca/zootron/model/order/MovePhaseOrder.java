package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public abstract class MovePhaseOrder extends Order {

    public MovePhaseOrder(@NotNull Province who) {
        super(who);
    }
}
