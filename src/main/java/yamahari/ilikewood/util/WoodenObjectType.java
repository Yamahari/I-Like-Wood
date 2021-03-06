package yamahari.ilikewood.util;

import net.minecraft.util.IStringSerializable;

public enum WoodenObjectType implements IStringSerializable {
    BARREL("barrel"),
    BED("bed"),
    BOOKSHELF("bookshelf"),
    CHEST("chest"),
    COMPOSTER("composter"),
    CRAFTING_TABLE("crafting_table"),
    LADDER("ladder"),
    LECTERN("lectern"),
    LOG_PILE("log_pile"),
    PANELS("panels"),
    POST("post"),
    SCAFFOLDING("scaffolding"),
    SLAB("slab"),
    STAIRS("stairs"),
    STICK("stick"),
    STRIPPED_POST("stripped_%s_post"),
    TORCH("torch"),
    WALL("wall");

    private final String name;

    WoodenObjectType(String name) {
        this.name = name;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String getName() {
        return this.name;
    }
}
