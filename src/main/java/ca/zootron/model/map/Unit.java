package ca.zootron.model.map;

import ca.zootron.model.map.Province.Location;
import java.util.Objects;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Unit unit = (Unit) o;
        return owner.equals(unit.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner);
    }

    @Override
    public String toString() {
        return (location == Location.LAND ? "Army" : "Fleet") + " owned by " + owner;
    }
}
