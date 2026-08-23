package de.nasiomo.fernvale.world;

import de.nasiomo.fernvale.render.Mesh;

public class Chunk {
    private static final int SIZE = 16;
    private static final int HEIGHT = 256;

    private Block[][][] blocks;
    private int chunkX, chunkZ;
    private Mesh mesh;
    private boolean meshDirty = true;

    public Chunk(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.blocks = new Block[SIZE][HEIGHT][SIZE];
        generateTerrain();
    }

    private void generateTerrain() {
        // Simple flat terrain generation
        for (int x = 0; x < SIZE; x++) {
            for (int z = 0; z < SIZE; z++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (y < 64) {
                        blocks[x][y][z] = new Block(BlockType.STONE, x, y, z);
                    } else if (y < 65) {
                        blocks[x][y][z] = new Block(BlockType.DIRT, x, y, z);
                    } else if (y < 66) {
                        blocks[x][y][z] = new Block(BlockType.GRASS, x, y, z);
                    } else {
                        blocks[x][y][z] = new Block(BlockType.AIR, x, y, z);
                    }
                }
            }
        }
        meshDirty = true;
    }

    public Block getBlock(int x, int y, int z) {
        if (x < 0 || x >= SIZE || y < 0 || y >= HEIGHT || z < 0 || z >= SIZE) {
            return new Block(BlockType.AIR);
        }
        return blocks[x][y][z];
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        if (x >= 0 && x < SIZE && y >= 0 && y < HEIGHT && z >= 0 && z < SIZE) {
            blocks[x][y][z] = new Block(type, x, y, z);
            meshDirty = true;
        }
    }

    public void bakeGeometry() {
        if (!meshDirty) return;
        
        // TODO: Generate mesh from blocks
        // For now, just mark as clean
        meshDirty = false;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public int getChunkX() { return chunkX; }
    public int getChunkZ() { return chunkZ; }
    public boolean isMeshDirty() { return meshDirty; }
}
