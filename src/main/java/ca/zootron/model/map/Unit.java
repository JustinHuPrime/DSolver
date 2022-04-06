package ca.zootron.model.map;

import ca.zootron.model.map.Province.Location;
import org.jetbrains.annotations.NotNull;

public final class Unit {

    @NotNull
    public final Country owner;
    @NotNull
    public Province.Location location;

    public Unit(@NotNull Province.Location location, @NotNull Country owner) {
        this.location = location;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return (location == Location.LAND ? "Army" : "Fleet") + " owned by " + owner;
    }
}
