package de.nasiomo.fernvale.world;

public class Block {
    private BlockType type;
    private int x, y, z;

    public Block(BlockType type) {
        this.type = type;
    }

    public Block(BlockType type, int x, int y, int z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }

    public boolean isSolid() {
        return type.isSolid();
    }
}
