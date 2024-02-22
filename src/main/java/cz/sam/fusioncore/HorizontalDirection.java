package cz.sam.fusioncore;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum HorizontalDirection implements StringRepresentable {
    EAST,
    WEST,
    SOUTH,
    NORTH;

    public static HorizontalDirection of(Direction direction) {
        switch (direction) {
            case DOWN, UP, EAST -> {
                return HorizontalDirection.EAST;
            }
            case NORTH -> {
                return HorizontalDirection.NORTH;
            }
            case SOUTH -> {
                return HorizontalDirection.SOUTH;
            }
            case WEST -> {
                return HorizontalDirection.WEST;
            }
        }
        return HorizontalDirection.EAST;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase();
    }
}
