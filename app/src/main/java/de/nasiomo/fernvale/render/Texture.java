package de.nasiomo.fernvale.render;

import org.lwjgl.stb.STBImage;
import org.lwjgl.opengl.GL11;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

public class Texture {
    private int textureID;
    private int width;
    private int height;

    public Texture(String resourcePath) {
        loadTexture(resourcePath);
    }

    private void loadTexture(String resourcePath) {
        try {
            var inputStream = Texture.class.getResourceAsStream("/" + resourcePath);
            if (inputStream == null) {
                System.err.println("Warning: Texture not found: " + resourcePath);
                createPlaceholder();
                return;
            }

            byte[] imageData = inputStream.readAllBytes();
            ByteBuffer imageBuffer = BufferUtils.createByteBuffer(imageData.length);
            imageBuffer.put(imageData).flip();

            IntBuffer widthBuf = BufferUtils.createIntBuffer(1);
            IntBuffer heightBuf = BufferUtils.createIntBuffer(1);
            IntBuffer channelsBuf = BufferUtils.createIntBuffer(1);

            ByteBuffer pixels = STBImage.stbi_load_from_memory(imageBuffer, widthBuf, heightBuf, channelsBuf, 4);
            if (pixels == null) {
                System.err.println("Failed to load image: " + STBImage.stbi_failure_reason());
                createPlaceholder();
                return;
            }

            width = widthBuf.get(0);
            height = heightBuf.get(0);

            textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, 
                              GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);

            STBImage.stbi_image_free(pixels);
            System.out.println("Loaded texture: " + resourcePath + " (" + width + "x" + height + ")");
        } catch (Exception e) {
            System.err.println("Error loading texture: " + resourcePath);
            e.printStackTrace();
            createPlaceholder();
        }
    }

    private void createPlaceholder() {
        width = 16;
        height = 16;
        ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
        
        // Create magenta checkerboard pattern
        for (int i = 0; i < width * height; i++) {
            if (((i / width) + (i % width)) % 2 == 0) {
                pixels.put((byte) 255).put((byte) 0).put((byte) 255).put((byte) 255); // Magenta
            } else {
                pixels.put((byte) 0).put((byte) 0).put((byte) 0).put((byte) 255); // Black
            }
        }
        pixels.flip();

        textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, 
                          GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
    }

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }

    public void delete() {
        GL11.glDeleteTextures(textureID);
    }

    public int getID() { return textureID; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
