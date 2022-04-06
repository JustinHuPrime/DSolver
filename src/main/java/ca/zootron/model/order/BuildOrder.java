package ca.zootron.model.order;

import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import org.jetbrains.annotations.NotNull;

public final class BuildOrder extends Order {
    @NotNull
    public Location where;

    public BuildOrder(@NotNull Province who, @NotNull Location where) {
        super(who);
        this.where = where;
    }
}
