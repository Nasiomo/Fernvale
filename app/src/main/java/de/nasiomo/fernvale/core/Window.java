package de.nasiomo.fernvale.core;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private long windowHandle;
    private int width;
    private int height;
    private String title;
    private boolean shouldClose = false;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        init();
    }

    private void init() {
        // Initialize GLFW
        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }

        // Create window
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        windowHandle = glfwCreateWindow(width, height, title, 0, 0);
        if (windowHandle == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        glfwSwapInterval(1); // Enable V-Sync

        // Create GL capabilities
        GL.createCapabilities();

        // Set clear color to gray
        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        // Set viewport
        glViewport(0, 0, width, height);

        // Set close callback
        glfwSetWindowCloseCallback(windowHandle, window -> shouldClose = true);
    }

    public void update() {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public boolean isOpen() {
        return !shouldClose && !glfwWindowShouldClose(windowHandle);
    }

    public void close() {
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
    }

    public long getHandle() {
        return windowHandle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
