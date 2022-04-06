package ca.zootron.model.order;

import ca.zootron.model.map.Country;
import ca.zootron.model.map.Province;
import org.jetbrains.annotations.NotNull;

public abstract class RetreatPhaseOrder extends Order {


    public RetreatPhaseOrder(@NotNull Province who) {
        super(who);
    }

    @Override
    public Country getIssuer() {
        return who.dislodgedUnit != null ? who.dislodgedUnit.owner : null;
    }
}
