package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SupportOrder extends Order {
    @Nullable
    public Order order;

    public SupportOrder(@NotNull Province who, @Nullable Order order) {
        super(who);
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SupportOrder that = (SupportOrder) o;
        return Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), order);
    }
}
