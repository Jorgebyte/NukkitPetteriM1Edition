package cn.nukkit.block;

import cn.nukkit.Server;
import cn.nukkit.entity.EntityUtils;
import cn.nukkit.entity.mob.EntitySilverfish;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.EntityEventPacket;

public class BlockMonsterEgg extends BlockSolidMeta {
    public static final int STONE = 0;
    public static final int COBBLESTONE = 1;
    public static final int STONE_BRICK = 2;
    public static final int MOSSY_BRICK = 3;
    public static final int CRACKED_BRICK = 4;
    public static final int CHISELED_BRICK = 5;

    public BlockMonsterEgg() {
        this(0);
    }

    public BlockMonsterEgg(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return MONSTER_EGG;
    }

    @Override
    public double getHardness() {
        return 0.75;
    }

    @Override
    public double getResistance() {
        return 3.75;
    }

    @Override
    public String getName() {
        String[] names = new String[]{
                "Stone",
                "Cobblestone",
                "Stone Brick",
                "Mossy Stone Brick",
                "Cracked Stone Brick",
                "Chiseled Stone Brick"
        };

        return names[this.getDamage() & 0x07] + " Monster Egg";
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[0];
    }

    @Override
    public boolean onBreak(Item item) {
        if (Server.getInstance().getPropertyBoolean("spawn-mobs", true)) {
            if (this.getLevel().getBlockLightAt((int) this.x, (int) this.y, (int) this.z) < 12 && EntityUtils.rand(1, 5) == 1) {

                EntitySilverfish entity = (EntitySilverfish) EntityUtils.create("Silverfish", this.add(0.5, 0, 0.5));
                if (entity != null) {
                    entity.spawnToAll();
                    EntityEventPacket pk = new EntityEventPacket();
                    pk.eid = entity.getId();
                    pk.event = 27;
                    entity.getLevel().addChunkPacket(entity.getChunkX() >> 2, entity.getChunkZ() >> 2, pk);
                }
            }
        }
        return this.getLevel().setBlock(this, new BlockAir(), true);
    }
}
