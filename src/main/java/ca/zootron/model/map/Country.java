package ca.zootron.model.map;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Country {
    @NotNull
    public final String name;

    public Country(@NotNull String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
