package de.nasiomo.fernvale.render;

import org.lwjgl.opengl.GL20;

public class Shader {
    private int programID;

    public Shader(String vertexSource, String fragmentSource) {
        int vertexID = compileShader(vertexSource, GL20.GL_VERTEX_SHADER);
        int fragmentID = compileShader(fragmentSource, GL20.GL_FRAGMENT_SHADER);
        
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);
        GL20.glLinkProgram(programID);
        
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == 0) {
            System.err.println("Shader link error: " + GL20.glGetProgramInfoLog(programID));
        }
        
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
    }

    private int compileShader(String source, int type) {
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, source);
        GL20.glCompileShader(shaderID);
        
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0) {
            System.err.println("Shader compile error: " + GL20.glGetShaderInfoLog(shaderID));
        }
        
        return shaderID;
    }

    public void use() {
        GL20.glUseProgram(programID);
    }

    public void delete() {
        GL20.glDeleteProgram(programID);
    }

    public int getID() {
        return programID;
    }
}
