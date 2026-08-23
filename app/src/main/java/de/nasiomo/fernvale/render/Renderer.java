package de.nasiomo.fernvale.render;

import org.lwjgl.opengl.GL11;
import de.nasiomo.fernvale.world.World;
import de.nasiomo.fernvale.player.Player;

public class Renderer {
    private Shader shader;

    public Renderer() {
        // Initialize shaders when rendering is ready
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(World world, Player player) {
        // Render world chunks
        // This will be implemented when we have chunk rendering
        GL11.glClearColor(0.53f, 0.81f, 0.92f, 1.0f); // Sky blue
    }

    public void cleanup() {
        if (shader != null) {
            shader.delete();
        }
    }
}
