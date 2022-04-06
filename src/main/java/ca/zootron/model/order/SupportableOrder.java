package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public abstract class SupportableOrder extends Order {

    public SupportableOrder(@NotNull Province who) {
        super(who);
    }
}
