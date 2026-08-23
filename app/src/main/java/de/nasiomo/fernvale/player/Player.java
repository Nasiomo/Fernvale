package de.nasiomo.fernvale.player;

import org.joml.Vector3f;
import de.nasiomo.fernvale.core.Window;

public class Player {
    private Camera camera;
    private Vector3f velocity;
    private float speed = 20.0f;
    private static final int W_KEY = 87;
    private static final int A_KEY = 65;
    private static final int S_KEY = 83;
    private static final int D_KEY = 68;
    private static final int SPACE_KEY = 32;
    private static final int SHIFT_KEY = 340;

    public Player() {
        this.camera = new Camera();
        this.velocity = new Vector3f();
    }

    public void update(float deltaTime, Window window) {
        // Handle input
        Vector3f movement = new Vector3f();
        
        if (window.isKeyPressed(W_KEY)) {
            movement.add(camera.getFront().x * speed * deltaTime, 0, camera.getFront().z * speed * deltaTime);
        }
        if (window.isKeyPressed(S_KEY)) {
            movement.sub(camera.getFront().x * speed * deltaTime, 0, camera.getFront().z * speed * deltaTime);
        }
        if (window.isKeyPressed(A_KEY)) {
            Vector3f left = new Vector3f(camera.getFront()).cross(camera.getUp()).normalize();
            movement.sub(left.x * speed * deltaTime, 0, left.z * speed * deltaTime);
        }
        if (window.isKeyPressed(D_KEY)) {
            Vector3f right = new Vector3f(camera.getFront()).cross(camera.getUp()).normalize();
            movement.add(right.x * speed * deltaTime, 0, right.z * speed * deltaTime);
        }
        if (window.isKeyPressed(SPACE_KEY)) {
            movement.add(0, speed * deltaTime, 0);
        }
        if (window.isKeyPressed(SHIFT_KEY)) {
            movement.sub(0, speed * deltaTime, 0);
        }

        camera.move(movement.normalize(0.01f), 1.0f);
    }

    public Camera getCamera() {
        return camera;
    }
}
