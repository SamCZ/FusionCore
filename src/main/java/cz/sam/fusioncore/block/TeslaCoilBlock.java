package cz.sam.fusioncore.block;

import cz.sam.fusioncore.HorizontalDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.ORIENTATION;

public class TeslaCoilBlock extends Block {

    private static VoxelShape makeCollisionShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.875, 0, 1, 0.9375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.0625, 0.9375, 0.875, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.9375, 0.375, 0.625, 1.75, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 1, 0.3125, 0.6875, 1.0625, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 1.125, 0.3125, 0.6875, 1.1875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 1.25, 0.3125, 0.6875, 1.3125, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 1.375, 0.3125, 0.6875, 1.4375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 1.5, 0.3125, 0.6875, 1.5625, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 1.625, 0.3125, 0.6875, 1.6875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 1.6875, 0.1875, 0.8125, 1.75, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 1.75, 0.125, 0.875, 1.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 1.8125, 0.1875, 0.8125, 1.875, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 1.875, 0.3125, 0.6875, 1.9375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 1.8359375000000002, -0.1875, 0.5625, 1.8984375000000002, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 1.875, -0.1875, 0.5625, 1.9375, -0.125), BooleanOp.OR);

        return shape;
    }

    public static final VoxelShape COLLIDER = makeCollisionShape();

    public static final EnumProperty<HorizontalDirection> ORIENTATION = EnumProperty.create("orientation",
            HorizontalDirection.class);


    public TeslaCoilBlock() {
        super(BlockBehaviour.Properties.of()
                .dynamicShape()
                .forceSolidOn()
                .sound(SoundType.METAL));
        registerDefaultState(getStateDefinition().any().setValue(ORIENTATION, HorizontalDirection.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ORIENTATION);
    }

    @Override
    public BlockState getStateForPlacement(final @NotNull BlockPlaceContext context) {
        // @formatter:off
        return defaultBlockState()
                .setValue(ORIENTATION, HorizontalDirection.of(context.getHorizontalDirection()));
        // @formatter:on
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(final @NotNull BlockState state, final @NotNull BlockGetter world,
                                        final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        return COLLIDER;
    }


}
