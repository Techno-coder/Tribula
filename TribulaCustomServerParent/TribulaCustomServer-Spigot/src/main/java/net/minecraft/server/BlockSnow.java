package net.minecraft.server;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockSnow extends Block {

    public static final BlockStateInteger LAYERS = BlockStateInteger.of("layers", 1, 8);
    protected static final AxisAlignedBB[] b = new AxisAlignedBB[] { new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};

    protected BlockSnow() {
        super(Material.PACKED_ICE);
        this.w(this.blockStateList.getBlockData().set(BlockSnow.LAYERS, 1));
        this.a(true);
        this.a(CreativeModeTab.c);
    }

    @SuppressWarnings("deprecation")
    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return BlockSnow.b[iblockdata.get(BlockSnow.LAYERS)];
    }

    public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockaccess.getType(blockposition).get(BlockSnow.LAYERS) < 5;
    }

    @SuppressWarnings("deprecation")
    public boolean k(IBlockData iblockdata) {
        return iblockdata.get(BlockSnow.LAYERS) == 7;
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public AxisAlignedBB a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        int i = iblockdata.get(BlockSnow.LAYERS) - 1;
        float f = 0.125F;
        AxisAlignedBB axisalignedbb = iblockdata.c(world, blockposition);

        return new AxisAlignedBB(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c, axisalignedbb.d, (double) ((float) i * 0.125F), axisalignedbb.f);
    }

    @SuppressWarnings("deprecation")
    public boolean b(IBlockData iblockdata) {
        return false;
    }

    @SuppressWarnings("deprecation")
    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        IBlockData iblockdata = world.getType(blockposition.down());
        Block block = iblockdata.getBlock();

        return (block != Blocks.ICE && block != Blocks.PACKED_ICE) && (iblockdata.getMaterial() == Material.LEAVES || (block == this && iblockdata.get(BlockSnow.LAYERS) >= 7 || iblockdata.p() && iblockdata.getMaterial().isSolid()));
    }

    @SuppressWarnings("deprecation")
    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        this.e(world, blockposition, iblockdata);
    }

    private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!this.canPlace(world, blockposition)) {
            this.b(world, blockposition, iblockdata, 0);
            world.setAir(blockposition);
            return false;
        } else {
            return true;
        }
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, @Nullable ItemStack itemstack) {
        a(world, blockposition, new ItemStack(Items.SNOWBALL, iblockdata.get(BlockSnow.LAYERS) + 1, 0));
        world.setAir(blockposition);
        entityhuman.b(StatisticList.a(this));
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Items.SNOWBALL;
    }

    public int a(Random random) {
        return 0;
    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        if (world.b(EnumSkyBlock.BLOCK, blockposition) > 11) {
            // CraftBukkit start
            if (org.bukkit.craftbukkit.event.CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), Blocks.AIR).isCancelled()) {
                return;
            }
            // CraftBukkit end
            this.b(world, blockposition, world.getType(blockposition), 0);
            world.setAir(blockposition);
        }

    }

    @SuppressWarnings("deprecation")
    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockSnow.LAYERS, (i & 7) + 1);
    }

    public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockaccess.getType(blockposition).get(BlockSnow.LAYERS) == 1;
    }

    public int toLegacyData(IBlockData iblockdata) {
        return iblockdata.get(BlockSnow.LAYERS) - 1;
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, BlockSnow.LAYERS);
    }
}
