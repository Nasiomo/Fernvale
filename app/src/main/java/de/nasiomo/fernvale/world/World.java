package de.nasiomo.fernvale.world;

import java.util.HashMap;
import java.util.Map;

public class World {
    private Map<String, Chunk> chunks = new HashMap<>();
    private static final int RENDER_DISTANCE = 8;

    public World() {
        generateInitialChunks();
    }

    private void generateInitialChunks() {
        // Generate a small initial world
        for (int x = -RENDER_DISTANCE; x <= RENDER_DISTANCE; x++) {
            for (int z = -RENDER_DISTANCE; z <= RENDER_DISTANCE; z++) {
                loadChunk(x, z);
            }
        }
    }

    public Chunk loadChunk(int chunkX, int chunkZ) {
        String key = chunkX + "," + chunkZ;
        if (!chunks.containsKey(key)) {
            chunks.put(key, new Chunk(chunkX, chunkZ));
        }
        return chunks.get(key);
    }

    public Chunk getChunk(int chunkX, int chunkZ) {
        return chunks.get(chunkX + "," + chunkZ);
    }

    public Block getBlock(int x, int y, int z) {
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        int localX = x & 15;
        int localZ = z & 15;
        
        Chunk chunk = getChunk(chunkX, chunkZ);
        if (chunk == null) {
            return new Block(BlockType.AIR);
        }
        return chunk.getBlock(localX, y, localZ);
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        int localX = x & 15;
        int localZ = z & 15;
        
        Chunk chunk = loadChunk(chunkX, chunkZ);
        chunk.setBlock(localX, y, localZ, type);
    }

    public java.util.Collection<Chunk> getLoadedChunks() {
        return chunks.values();
    }
}
