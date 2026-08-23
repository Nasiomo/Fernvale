package de.nasiomo.fernvale.world;

public enum BlockType {
    GRASS("textures/blocks/Grass_Block.png"),
    DIRT("textures/blocks/Dirt_Block.png"),
    STONE("textures/blocks/Stone_Block.png"),
    AIR(null);

    private final String texturePath;

    BlockType(String texturePath) {
        this.texturePath = texturePath;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public boolean isSolid() {
        return this != AIR;
    }
}
