package de.nasiomo.fernvale.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private long windowHandle;
    private int width;
    private int height;
    private String title;
    private boolean[] keys = new boolean[512];

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        init();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        windowHandle = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (windowHandle == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        GLFW.glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (key >= 0 && key < keys.length) {
                keys[key] = action != GLFW.GLFW_RELEASE;
            }
        });

        GLFW.glfwMakeContextCurrent(windowHandle);
        GLFW.glfwSwapInterval(1); // V-Sync
        GL.createCapabilities();
        
        GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    public void show() {
        GLFW.glfwShowWindow(windowHandle);
    }

    public void close() {
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(windowHandle);
    }

    public void pollEvents() {
        GLFW.glfwPollEvents();
    }

    public boolean isKeyPressed(int key) {
        return key >= 0 && key < keys.length && keys[key];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public long getHandle() { return windowHandle; }
}
