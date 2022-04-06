package ca.zootron.model.order;

import ca.zootron.model.map.Country;
import ca.zootron.model.map.Province;
import ca.zootron.util.IllegalOrderException;
import org.jetbrains.annotations.NotNull;

public abstract class RetreatPhaseOrder extends Order {


    /**
     * Generalized retreat phase order to some province
     *
     * <ul>
     *     <li>Province must contain a dislodged unit</li>
     * </ul>
     */
    public RetreatPhaseOrder(@NotNull Province who) {
        super(who);

        if (who.dislodgedUnit == null) {
            throw new IllegalOrderException("can't issue a retreat phase order to a province without a dislodged unit");
        }
    }

    @Override
    public Country getIssuer() {
        return who.dislodgedUnit != null ? who.dislodgedUnit.unit().owner : null;
    }
}
