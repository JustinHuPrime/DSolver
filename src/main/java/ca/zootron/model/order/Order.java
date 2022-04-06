package ca.zootron.model.order;

import ca.zootron.model.map.Country;
import ca.zootron.model.map.Province;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public abstract class Order {

    @NotNull
    public final Province who;
    @NotNull
    public OrderState state;

    public Order(@NotNull Province who) {
        this.who = who;
        this.state = OrderState.ISSUED;
    }

    public Country getIssuer(boolean retreating) {
        if (retreating) {
            return who.dislodgedUnit != null ? who.dislodgedUnit.owner : null;
        } else {
            return who.unit != null ? who.unit.owner : null;
        }
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

    @Override
    public abstract String toString();

    public enum OrderState {
        ISSUED,
        FAILED,
        SUCCEEDED
    }
}
