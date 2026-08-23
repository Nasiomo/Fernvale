package de.nasiomo.fernvale.core;

import de.nasiomo.fernvale.world.World;
import de.nasiomo.fernvale.player.Player;
import de.nasiomo.fernvale.render.Renderer;

public class Game {
    private Window window;
    private Renderer renderer;
    private World world;
    private Player player;
    private Time time;
    private boolean running = true;

    public Game() {
        this.time = new Time();
    }

    public void run() {
        try {
            initialize();
            gameLoop();
        } finally {
            cleanup();
        }
    }

    private void initialize() {
        System.out.println("Initializing Fernvale...");
        
        window = new Window(1280, 720, "Fernvale - Minecraft Clone");
        window.show();
        
        renderer = new Renderer();
        world = new World();
        player = new Player();
        
        System.out.println("Game initialized successfully!");
    }

    private void gameLoop() {
        System.out.println("Starting game loop...");
        
        while (running && !window.shouldClose()) {
            time.update();
            
            // Update
            update(time.getDeltaTime());
            
            // Render
            renderer.clear();
            renderer.render(world, player);
            window.swapBuffers();
            window.pollEvents();
        }
    }

    private void update(float deltaTime) {
        // Handle input
        if (window.isKeyPressed(256)) { // ESC key
            running = false;
        }
        
        player.update(deltaTime, window);
    }

    private void cleanup() {
        System.out.println("Shutting down...");
        if (renderer != null) renderer.cleanup();
        if (window != null) window.close();
    }
}
