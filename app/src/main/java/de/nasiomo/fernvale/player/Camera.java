package de.nasiomo.fernvale.player;

import org.joml.Vector3f;
import org.joml.Matrix4f;

public class Camera {
    private Vector3f position;
    private Vector3f front;
    private Vector3f up;
    private float yaw = -90.0f;
    private float pitch = 0.0f;

    public Camera() {
        this.position = new Vector3f(0, 70, 0);
        this.front = new Vector3f(0, 0, -1);
        this.up = new Vector3f(0, 1, 0);
    }

    public void updateDirection(float deltaYaw, float deltaPitch) {
        yaw += deltaYaw;
        pitch -= deltaPitch;

        if (pitch > 89.0f) pitch = 89.0f;
        if (pitch < -89.0f) pitch = -89.0f;

        Vector3f direction = new Vector3f();
        direction.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        direction.y = (float) Math.sin(Math.toRadians(pitch));
        direction.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        this.front = direction.normalize();
    }

    public void move(Vector3f direction, float distance) {
        Vector3f movement = new Vector3f(direction).mul(distance);
        position.add(movement);
    }

    public Matrix4f getViewMatrix() {
        Vector3f center = new Vector3f(position).add(front);
        return new Matrix4f().lookAt(position, center, up);
    }

    public Vector3f getPosition() { return position; }
    public Vector3f getFront() { return front; }
    public Vector3f getUp() { return up; }
}
