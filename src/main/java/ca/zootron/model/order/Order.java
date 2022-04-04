package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public abstract class Order {
    @NotNull
    public Province who;

    public Order(@NotNull Province who) {
        this.who = who;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return who.equals(order.who);
    }

    @Override
    public int hashCode() {
        return Objects.hash(who);
    }
}
