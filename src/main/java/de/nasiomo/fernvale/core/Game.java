package de.nasiomo.fernvale.core;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends Canvas implements Runnable {
    
    private static final long serialVersionUID = 1L;
    
    // Fenster-Einstellungen
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Fernvale - Minecraft Clone Pre-Alpha";
    
    private Thread thread;
    private boolean running = false;
    
    // Spieler-Position (in Pixeln)
    private int playerX = 400;
    private int playerY = 300;
    private final int playerSpeed = 8;
    
    // Einfache Tastenerkennung
    private boolean keys[] = new boolean[256];
    
    // Eine kleine 2D-Welt aus Blöcken (0 = Luft, 1 = Gras, 2 = Erde, 3 = Stein)
    private final int BLOCK_SIZE = 32;
    private final int WORLD_WIDTH = 25;
    private final int WORLD_HEIGHT = 19;
    private int[][] worldMap = new int[WORLD_WIDTH][WORLD_HEIGHT];

    public Game() {
        // Fenster-Größe festlegen
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        
        // Generiere eine einfache Minecraft-ähnliche Landschaft (Flachwelt mit Hügeln)
        generateWorld();
        
        // Tastatur-Eingaben registrieren
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() < keys.length) keys[e.getKeyCode()] = true;
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() < keys.length) keys[e.getKeyCode()] = false;
            }
        });
        
        setFocusable(true);
    }
    
    private void generateWorld() {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                if (y > 12) {
                    worldMap[x][y] = 3; // Stein tief unten
                } else if (y > 10) {
                    worldMap[x][y] = 2; // Erde unter der Oberfläche
                } else if (y == 10) {
                    worldMap[x][y] = 1; // Gras-Oberfläche
                } else {
                    worldMap[x][y] = 0; // Luft
                }
            }
        }
    }

    public synchronized void run() {
        // Fenster erstellen
        JFrame frame = new JFrame();
        frame.setTitle(TITLE);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
        running = true;
        
        // Klassischer Game Loop (Spielschleife) für konstante FPS
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            while (delta >= 1) {
                tick(); // Berechnungen (z.B. Bewegung)
                delta--;
            }
            
            render(); // Zeichnen der Grafik
        }
    }
    
    // Updates und Logik (60 Mal pro Sekunde)
    private void tick() {
        if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) playerY -= playerSpeed;
        if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) playerY += playerSpeed;
        if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) playerX -= playerSpeed;
        if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) playerX += playerSpeed;
    }
    
    // Zeichnen des Bildschirms
    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3); // Dreifach-Pufferung gegen Flackern
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        // Hintergrund (Himmel) zeichnen
        g.setColor(new Color(135, 206, 235));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        // Minecraft-Blöcke rendern
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                int blockType = worldMap[x][y];
                if (blockType == 1) {
                    g.setColor(new Color(34, 139, 34)); // Gras-Grün
                } else if (blockType == 2) {
                    g.setColor(new Color(139, 69, 19));  // Erd-Braun
                } else if (blockType == 3) {
                    g.setColor(Color.GRAY);              // Stein-Grau
                }
                
                if (blockType != 0) {
                    g.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.BLACK); // Block-Kanten zeichnen
                    g.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
        
        // Spieler zeichnen (Ein rotes Viereck als Platzhalter für Steve)
        g.setColor(Color.RED);
        g.fillRect(playerX, playerY, 24, 48);
        g.setColor(Color.BLACK);
        g.drawRect(playerX, playerY, 24, 48);
        
        g.dispose();
        bs.show();
    }
}
