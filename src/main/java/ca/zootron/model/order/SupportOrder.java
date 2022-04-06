package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import ca.zootron.util.IllegalOrderException;
import org.jetbrains.annotations.NotNull;

public final class SupportOrder extends MovePhaseOrder {

    @NotNull
    public final MovePhaseOrder order;

    /**
     * Support an order
     *
     * If supporting a move: destination province must be ordinarily reachable
     * Otherwise: target unit's province must be ordinarily reachable
     */
    public SupportOrder(@NotNull Province who, @NotNull MovePhaseOrder order) {
        super(who);
        this.order = order;

        assert who.unit != null;
        if (order instanceof MoveOrder moveOrder) {
            if (who.adjacencies.get(who.unit.location).stream().noneMatch(provinceLocation -> provinceLocation.province() == ((MoveOrder) order).destination.province())) {
                throw new IllegalOrderException("can't support a move order when the supported unit's destination province is unreachable");
            }
        } else {
            if (who.adjacencies.get(who.unit.location).stream().noneMatch(provinceLocation -> provinceLocation.province() == order.who)) {
                throw new IllegalOrderException("can't support a non-move order when the supported unit's province is unreachable");
            }
        }
    }

    @Override
    public String toString() {
        if (order instanceof MoveOrder moveOrder) {
            return who + " supports " + moveOrder.who + " - " + moveOrder.destination.province();
        } else {
            return who + " supports " + order.who;
        }
    }
}
