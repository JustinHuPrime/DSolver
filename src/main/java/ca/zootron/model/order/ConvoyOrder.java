package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public final class ConvoyOrder extends SupportableOrder {

    @NotNull
    public final Province from;
    @NotNull
    public final Province to;

    public ConvoyOrder(@NotNull Province who, @NotNull Province from, @NotNull Province to) {
        super(who);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return who + " convoys " + from + " - " + to;
    }
}
