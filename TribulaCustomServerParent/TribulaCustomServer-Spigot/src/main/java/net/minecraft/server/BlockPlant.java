package net.minecraft.server;

import org.bukkit.event.block.BlockPhysicsEvent;

import javax.annotation.Nullable;
import java.util.Random;

// CraftBukkit start
// CraftBukkit end

public class BlockPlant extends Block {

    protected static final AxisAlignedBB b = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);

    protected BlockPlant() {
        this(Material.PLANT);
    }

    protected BlockPlant(@SuppressWarnings("SameParameterValue") Material material) {
        this(material, material.r());
    }

    protected BlockPlant(Material material, MaterialMapColor materialmapcolor) {
        super(material, materialmapcolor);
        this.a(true);
        this.a(CreativeModeTab.c);
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        return super.canPlace(world, blockposition) && this.i(world.getType(blockposition.down()));
    }

    protected boolean i(IBlockData iblockdata) {
        return iblockdata.getBlock() == Blocks.GRASS || iblockdata.getBlock() == Blocks.DIRT || iblockdata.getBlock() == Blocks.FARMLAND;
    }

    @SuppressWarnings("deprecation")
    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        //noinspection deprecation
        super.a(iblockdata, world, blockposition, block);
        this.e(world, blockposition, iblockdata);
    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        this.e(world, blockposition, iblockdata);
    }

    protected void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!this.f(world, blockposition, iblockdata)) {
            // CraftBukkit Start
            org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
            //noinspection deprecation,deprecation
            BlockPhysicsEvent event = new BlockPhysicsEvent(block, block.getTypeId());
            world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }
            // CraftBukkit end
            this.b(world, blockposition, iblockdata, 0);
            world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
        }

    }

    public boolean f(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return this.i(world.getType(blockposition.down()));
    }

    @SuppressWarnings("deprecation")
    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return BlockPlant.b;
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public AxisAlignedBB a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        return BlockPlant.k;
    }

    @SuppressWarnings("deprecation")
    public boolean b(IBlockData iblockdata) {
        return false;
    }

    @SuppressWarnings("deprecation")
    public boolean c(IBlockData iblockdata) {
        return false;
    }
}
