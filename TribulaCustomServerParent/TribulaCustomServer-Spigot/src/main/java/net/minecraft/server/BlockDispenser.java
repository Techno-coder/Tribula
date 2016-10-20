package net.minecraft.server;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockDispenser extends BlockTileEntity {

    public static final BlockStateDirection FACING = BlockDirectional.FACING;
    public static final BlockStateBoolean TRIGGERED = BlockStateBoolean.of("triggered");
    @SuppressWarnings("unchecked")
    public static final RegistryDefault<Item, IDispenseBehavior> REGISTRY = new RegistryDefault(new DispenseBehaviorItem());
    public static boolean eventFired = false; // CraftBukkit
    protected Random d = new Random();

    protected BlockDispenser() {
        super(Material.STONE);
        this.w(this.blockStateList.getBlockData().set(BlockDispenser.FACING, EnumDirection.NORTH).set(BlockDispenser.TRIGGERED, Boolean.FALSE));
        this.a(CreativeModeTab.d);
    }

    public static IPosition a(ISourceBlock isourceblock) {
        EnumDirection enumdirection = isourceblock.e().get(BlockDispenser.FACING);
        double d0 = isourceblock.getX() + 0.7D * (double) enumdirection.getAdjacentX();
        double d1 = isourceblock.getY() + 0.7D * (double) enumdirection.getAdjacentY();
        double d2 = isourceblock.getZ() + 0.7D * (double) enumdirection.getAdjacentZ();

        return new Position(d0, d1, d2);
    }

    public int a(World world) {
        return 4;
    }

    public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
        super.onPlace(world, blockposition, iblockdata);
        this.e(world, blockposition, iblockdata);
    }

    private void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!world.isClientSide) {
            EnumDirection enumdirection = iblockdata.get(BlockDispenser.FACING);
            boolean flag = world.getType(blockposition.north()).b();
            boolean flag1 = world.getType(blockposition.south()).b();

            if (enumdirection == EnumDirection.NORTH && flag && !flag1) {
                enumdirection = EnumDirection.SOUTH;
            } else if (enumdirection == EnumDirection.SOUTH && flag1 && !flag) {
                enumdirection = EnumDirection.NORTH;
            } else {
                boolean flag2 = world.getType(blockposition.west()).b();
                boolean flag3 = world.getType(blockposition.east()).b();

                if (enumdirection == EnumDirection.WEST && flag2 && !flag3) {
                    enumdirection = EnumDirection.EAST;
                } else if (enumdirection == EnumDirection.EAST && flag3 && !flag2) {
                    enumdirection = EnumDirection.WEST;
                }
            }

            world.setTypeAndData(blockposition, iblockdata.set(BlockDispenser.FACING, enumdirection).set(BlockDispenser.TRIGGERED, Boolean.FALSE), 2);
        }
    }

    public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumHand enumhand, @Nullable ItemStack itemstack, EnumDirection enumdirection, float f, float f1, float f2) {
        if (world.isClientSide) {
            return true;
        } else {
            TileEntity tileentity = world.getTileEntity(blockposition);

            if (tileentity instanceof TileEntityDispenser) {
                entityhuman.openContainer((TileEntityDispenser) tileentity);
                if (tileentity instanceof TileEntityDropper) {
                    entityhuman.b(StatisticList.Q);
                } else {
                    entityhuman.b(StatisticList.S);
                }
            }

            return true;
        }
    }

    public void dispense(World world, BlockPosition blockposition) {
        SourceBlock sourceblock = new SourceBlock(world, blockposition);
        TileEntityDispenser tileentitydispenser = sourceblock.getTileEntity();

        if (tileentitydispenser != null) {
            int i = tileentitydispenser.m();

            if (i < 0) {
                world.triggerEffect(1001, blockposition, 0);
            } else {
                ItemStack itemstack = tileentitydispenser.getItem(i);
                IDispenseBehavior idispensebehavior = this.a(itemstack);

                if (idispensebehavior != IDispenseBehavior.NONE) {
                    ItemStack itemstack1 = idispensebehavior.a(sourceblock, itemstack);
                    eventFired = false; // CraftBukkit - reset event status

                    tileentitydispenser.setItem(i, itemstack1.count <= 0 ? null : itemstack1);
                }

            }
        }
    }

    protected IDispenseBehavior a(@Nullable ItemStack itemstack) {
        return BlockDispenser.REGISTRY.get(itemstack == null ? null : itemstack.getItem());
    }

    @SuppressWarnings("deprecation")
    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        boolean flag = world.isBlockIndirectlyPowered(blockposition) || world.isBlockIndirectlyPowered(blockposition.up());
        boolean flag1 = iblockdata.get(BlockDispenser.TRIGGERED);

        if (flag && !flag1) {
            world.a(blockposition, this, this.a(world));
            world.setTypeAndData(blockposition, iblockdata.set(BlockDispenser.TRIGGERED, Boolean.TRUE), 4);
        } else if (!flag && flag1) {
            world.setTypeAndData(blockposition, iblockdata.set(BlockDispenser.TRIGGERED, Boolean.FALSE), 4);
        }

    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        if (!world.isClientSide) {
            this.dispense(world, blockposition);
        }

    }

    public TileEntity a(World world, int i) {
        return new TileEntityDispenser();
    }

    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        return this.getBlockData().set(BlockDispenser.FACING, BlockPiston.a(blockposition, entityliving)).set(BlockDispenser.TRIGGERED, Boolean.FALSE);
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
        world.setTypeAndData(blockposition, iblockdata.set(BlockDispenser.FACING, BlockPiston.a(blockposition, entityliving)), 2);
        if (itemstack.hasName()) {
            TileEntity tileentity = world.getTileEntity(blockposition);

            if (tileentity instanceof TileEntityDispenser) {
                ((TileEntityDispenser) tileentity).a(itemstack.getName());
            }
        }

    }

    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        TileEntity tileentity = world.getTileEntity(blockposition);

        if (tileentity instanceof TileEntityDispenser) {
            InventoryUtils.dropInventory(world, blockposition, (TileEntityDispenser) tileentity);
            world.updateAdjacentComparators(blockposition, this);
        }

        super.remove(world, blockposition, iblockdata);
    }

    @SuppressWarnings("deprecation")
    public boolean isComplexRedstone(IBlockData iblockdata) {
        return true;
    }

    @SuppressWarnings("deprecation")
    public int d(IBlockData iblockdata, World world, BlockPosition blockposition) {
        return Container.a(world.getTileEntity(blockposition));
    }

    public EnumRenderType a(IBlockData iblockdata) {
        return EnumRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockDispenser.FACING, EnumDirection.fromType1(i & 7)).set(BlockDispenser.TRIGGERED, (i & 8) > 0);
    }

    public int toLegacyData(IBlockData iblockdata) {
        byte b0 = 0;
        int i = b0 | iblockdata.get(BlockDispenser.FACING).a();

        if (iblockdata.get(BlockDispenser.TRIGGERED)) {
            i |= 8;
        }

        return i;
    }

    @SuppressWarnings("deprecation")
    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return iblockdata.set(BlockDispenser.FACING, enumblockrotation.a(iblockdata.get(BlockDispenser.FACING)));
    }

    @SuppressWarnings("deprecation")
    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a(iblockdata.get(BlockDispenser.FACING)));
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, BlockDispenser.FACING, BlockDispenser.TRIGGERED);
    }
}
