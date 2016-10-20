package net.minecraft.server;

import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.Random;

public class BlockMonsterEggs extends Block {

    public static final BlockStateEnum<BlockMonsterEggs.EnumMonsterEggVarient> VARIANT = BlockStateEnum.of("variant", BlockMonsterEggs.EnumMonsterEggVarient.class);

    public BlockMonsterEggs() {
        super(Material.CLAY);
        this.w(this.blockStateList.getBlockData().set(BlockMonsterEggs.VARIANT, BlockMonsterEggs.EnumMonsterEggVarient.STONE));
        this.c(0.0F);
        this.a(CreativeModeTab.c);
    }

    public static boolean i(IBlockData iblockdata) {
        Block block = iblockdata.getBlock();

        return iblockdata == Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.STONE) || block == Blocks.COBBLESTONE || block == Blocks.STONEBRICK;
    }

    public int a(Random random) {
        return 0;
    }

    protected ItemStack u(IBlockData iblockdata) {
        switch (BlockMonsterEggs.SyntheticClass_1.a[iblockdata.get(BlockMonsterEggs.VARIANT).ordinal()]) {
        case 1:
            return new ItemStack(Blocks.COBBLESTONE);

        case 2:
            return new ItemStack(Blocks.STONEBRICK);

        case 3:
            return new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.EnumStonebrickType.MOSSY.a());

        case 4:
            return new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.EnumStonebrickType.CRACKED.a());

        case 5:
            return new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.EnumStonebrickType.CHISELED.a());

        default:
            return new ItemStack(Blocks.STONE);
        }
    }

    public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
        if (!world.isClientSide && world.getGameRules().getBoolean("doTileDrops")) {
            EntitySilverfish entitysilverfish = new EntitySilverfish(world);

            entitysilverfish.setPositionRotation((double) blockposition.getX() + 0.5D, (double) blockposition.getY(), (double) blockposition.getZ() + 0.5D, 0.0F, 0.0F);
            world.addEntity(entitysilverfish, SpawnReason.SILVERFISH_BLOCK); // CraftBukkit - add SpawnReason
            entitysilverfish.doSpawnEffect();
        }

    }

    public ItemStack a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return new ItemStack(this, 1, iblockdata.getBlock().toLegacyData(iblockdata));
    }

    @SuppressWarnings("deprecation")
    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockMonsterEggs.VARIANT, BlockMonsterEggs.EnumMonsterEggVarient.a(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return iblockdata.get(BlockMonsterEggs.VARIANT).a();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, BlockMonsterEggs.VARIANT);
    }

    public enum EnumMonsterEggVarient implements INamable {

        STONE(0, "stone") {;
            public IBlockData d() {
                return Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.STONE);
            }
        }, COBBLESTONE(1, "cobblestone", "cobble") {;
    public IBlockData d() {
        return Blocks.COBBLESTONE.getBlockData();
    }
}, STONEBRICK(2, "stone_brick", "brick") {;
    public IBlockData d() {
        return Blocks.STONEBRICK.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.DEFAULT);
    }
}, MOSSY_STONEBRICK(3, "mossy_brick", "mossybrick") {;
    public IBlockData d() {
        return Blocks.STONEBRICK.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.MOSSY);
    }
}, CRACKED_STONEBRICK(4, "cracked_brick", "crackedbrick") {;
    public IBlockData d() {
        return Blocks.STONEBRICK.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.CRACKED);
    }
}, CHISELED_STONEBRICK(5, "chiseled_brick", "chiseledbrick") {;
    public IBlockData d() {
        return Blocks.STONEBRICK.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.CHISELED);
    }
};

        private static final BlockMonsterEggs.EnumMonsterEggVarient[] g = new BlockMonsterEggs.EnumMonsterEggVarient[values().length];

        static {
            BlockMonsterEggs.EnumMonsterEggVarient[] ablockmonstereggs_enummonstereggvarient = values();
            int i = ablockmonstereggs_enummonstereggvarient.length;

            for (EnumMonsterEggVarient blockmonstereggs_enummonstereggvarient : ablockmonstereggs_enummonstereggvarient) {
                EnumMonsterEggVarient.g[blockmonstereggs_enummonstereggvarient.a()] = blockmonstereggs_enummonstereggvarient;
            }

        }

        private final int h;
        private final String i;
        private final String j;

        EnumMonsterEggVarient(int i, String s) {
            this(i, s, s);
        }

        EnumMonsterEggVarient(int i, String s, String s1) {
            this.h = i;
            this.i = s;
            this.j = s1;
        }

        EnumMonsterEggVarient(int i, String s, BlockMonsterEggs.SyntheticClass_1 blockmonstereggs_syntheticclass_1) {
            this(i, s);
        }

        EnumMonsterEggVarient(int i, String s, String s1, BlockMonsterEggs.SyntheticClass_1 blockmonstereggs_syntheticclass_1) {
            this(i, s, s1);
        }

        public static BlockMonsterEggs.EnumMonsterEggVarient a(int i) {
            if (i < 0 || i >= BlockMonsterEggs.EnumMonsterEggVarient.g.length) {
                i = 0;
            }

            return BlockMonsterEggs.EnumMonsterEggVarient.g[i];
        }

        public static BlockMonsterEggs.EnumMonsterEggVarient a(IBlockData iblockdata) {
            BlockMonsterEggs.EnumMonsterEggVarient[] ablockmonstereggs_enummonstereggvarient = values();
            int i = ablockmonstereggs_enummonstereggvarient.length;

            for (EnumMonsterEggVarient blockmonstereggs_enummonstereggvarient : ablockmonstereggs_enummonstereggvarient) {
                if (iblockdata == blockmonstereggs_enummonstereggvarient.d()) {
                    return blockmonstereggs_enummonstereggvarient;
                }
            }

            return BlockMonsterEggs.EnumMonsterEggVarient.STONE;
        }

        public int a() {
            return this.h;
        }

        public String toString() {
            return this.i;
        }

        public String getName() {
            return this.i;
        }

        public String c() {
            return this.j;
        }

        public abstract IBlockData d();
    }

    static class SyntheticClass_1 {

        static final int[] a = new int[BlockMonsterEggs.EnumMonsterEggVarient.values().length];

        static {
            try {
                BlockMonsterEggs.SyntheticClass_1.a[BlockMonsterEggs.EnumMonsterEggVarient.COBBLESTONE.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockMonsterEggs.SyntheticClass_1.a[BlockMonsterEggs.EnumMonsterEggVarient.STONEBRICK.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockMonsterEggs.SyntheticClass_1.a[BlockMonsterEggs.EnumMonsterEggVarient.MOSSY_STONEBRICK.ordinal()] = 3;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockMonsterEggs.SyntheticClass_1.a[BlockMonsterEggs.EnumMonsterEggVarient.CRACKED_STONEBRICK.ordinal()] = 4;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockMonsterEggs.SyntheticClass_1.a[BlockMonsterEggs.EnumMonsterEggVarient.CHISELED_STONEBRICK.ordinal()] = 5;
            } catch (NoSuchFieldError ignored) {
            }

        }
    }
}
