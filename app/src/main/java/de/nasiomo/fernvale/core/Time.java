package de.nasiomo.fernvale.core;

public class Time {
    private long lastTime;
    private float deltaTime;

    public Time() {
        this.lastTime = System.nanoTime();
    }

    public void update() {
        long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastTime) / 1_000_000_000.0f;
        lastTime = currentTime;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public float getCurrentTime() {
        return lastTime / 1_000_000_000.0f;
    }
}
