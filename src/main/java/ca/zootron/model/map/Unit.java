package ca.zootron.model.map;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class Unit {
    @NotNull
    public final Country owner;

    public Unit(@NotNull Country owner) {
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
}
