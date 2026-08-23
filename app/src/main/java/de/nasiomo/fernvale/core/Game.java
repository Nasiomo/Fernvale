package de.nasiomo.fernvale.core;

import de.nasiomo.fernvale.menu.MainMenu;

public class Game {
    private Window window;
    private MainMenu mainMenu;
    private boolean running = false;

    public Game() {
        // Create window with 1280x720 resolution
        window = new Window(1280, 720, "Fernvale - Minecraft Clone");
        mainMenu = new MainMenu();
    }

    public void run() {
        running = true;

        // Game loop
        while (window.isOpen() && running) {
            update();
            render();
        }

        cleanup();
    }

    private void update() {
        mainMenu.update();
    }

    private void render() {
        window.clear();
        mainMenu.render();
        window.update();
    }

    private void cleanup() {
        window.close();
    }

    public Window getWindow() {
        return window;
    }

    public void stop() {
        running = false;
    }
}
