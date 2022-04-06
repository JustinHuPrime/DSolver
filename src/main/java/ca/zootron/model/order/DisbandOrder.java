package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public final class DisbandOrder extends Order {

    public DisbandOrder(@NotNull Province who) {
        super(who);
    }

    @Override
    public String toString() {
        return "disband " + who;
    }
}
