package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import ca.zootron.util.IllegalOrderException;
import org.jetbrains.annotations.NotNull;

public final class DisbandOrder extends BuildPhaseOrder {

    /**
     * Disband a unit from some province
     *
     * Province must contain a unit
     */
    public DisbandOrder(@NotNull Province who) {
        super(who);

        if (who.unit == null) {
            throw new IllegalOrderException("can't issue a disband order without a unit to disband");
        }
    }

    @Override
    public String toString() {
        return "disband " + who;
    }
}
