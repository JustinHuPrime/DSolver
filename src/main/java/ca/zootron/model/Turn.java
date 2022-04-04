package ca.zootron.model;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class Turn {
    public int turn;
    @NotNull
    public Phase phase;

    public Turn(int turn, @NotNull Phase phase) {
        this.turn = turn;
        this.phase = phase;
    }

    public @NotNull Turn next() {
        if (this.phase == Phase.WINTER_BUILD) {
            return new Turn(this.turn + 1, Phase.SPRING_MOVE);
        } else {
            return new Turn(this.turn, Phase.values()[this.phase.ordinal() + 1]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Turn turn1 = (Turn) o;
        return turn == turn1.turn && phase == turn1.phase;
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, phase);
    }

    public enum Phase {
        SPRING_MOVE,
        SPRING_RETREAT,
        FALL_MOVE,
        FALL_RETREAT,
        WINTER_BUILD
    }
}
