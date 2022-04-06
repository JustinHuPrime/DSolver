package ca.zootron.model.order.move;

import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import ca.zootron.util.IllegalOrderException;
import org.jetbrains.annotations.NotNull;

public final class ConvoyOrder extends MovePhaseOrder {

    @NotNull
    public final Province from;
    @NotNull
    public final Province to;

    /**
     * Convoy an army between two provinces
     *
     * <ul>
     *     <li>Source province must contain a unit on land</li>
     *     <li>Both source and destination must be distinct</li>
     *     <li>Both source and destination provinces must be coastal</li>
     * </ul>
     */
    public ConvoyOrder(@NotNull Province who, @NotNull Province from, @NotNull Province to) {
        super(who);
        this.from = from;
        this.to = to;

        if (!(from.unit != null && from.unit.location == Location.LAND)) {
            throw new IllegalOrderException("can't issue a convoy order without a unit on land waiting to be convoyed");
        } else if (from == to) {
            throw new IllegalOrderException("can't issue a convoy order to and from the same province");
        } else if (!from.isCoastal()) {
            throw new IllegalOrderException("can't issue a convoy order unless convoying from a coastal province");
        } else if (!to.isCoastal()) {
            throw new IllegalOrderException("can't issue a convoy order unless convoying to a coastal province");
        }
    }

    @Override
    public String toString() {
        return who + " convoys " + from + " - " + to;
    }
}
