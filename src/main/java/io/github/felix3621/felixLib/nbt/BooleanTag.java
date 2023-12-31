package io.github.felix3621.felixLib.nbt;

import io.github.felix3621.felixLib.Util;

public class BooleanTag implements Tag {
    private final boolean data;

    @Override
    public byte getId() {
        return TAG_BOOLEAN;
    }

    private BooleanTag(boolean data) {
        this.data = Util.AntiNullFallback(data, false);
    }

    public static BooleanTag valueOf(boolean data) {
        return new BooleanTag(data);
    }

    public boolean getAsBoolean() {
        return this.data;
    }
    public static boolean tagOf(Tag data) {
        if (data.getId() == TAG_BOOLEAN) {
            return ((BooleanTag) data).data;
        } else throw new IllegalArgumentException("Tag is not Boolean type");
    }
}
