package ca.zootron;

import ca.zootron.model.Game;
import ca.zootron.util.BadMapException;
import ca.zootron.util.NoSuchMapException;

public final class Main {

    private Main() {}

    public static void main(String[] args) {
        try {
            Game game = Game.fromMap("classic");
        } catch (NoSuchMapException | BadMapException e) {
            System.err.println("Failed to load map: " + e.getMessage());
        }
    }
}
