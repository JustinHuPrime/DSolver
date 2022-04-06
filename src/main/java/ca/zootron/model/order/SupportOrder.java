package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SupportOrder extends SupportableOrder {
    @Nullable
    public SupportableOrder order;

    public SupportOrder(@NotNull Province who, @Nullable SupportableOrder order) {
        super(who);
        this.order = order;
    }

    @Override
    public String toString() {
        if (order instanceof MoveOrder moveOrder) {
            return who + " supports " + moveOrder.who + " - " + moveOrder.destinationProvince;
        } else {
            assert order != null;
            return who + " supports " + order.who;
        }
    }
}
