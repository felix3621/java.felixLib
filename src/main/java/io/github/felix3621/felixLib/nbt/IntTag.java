package io.github.felix3621.felixLib.nbt;

import io.github.felix3621.felixLib.Util;

public class IntTag implements Tag{
    private final int data;

    @Override
    public byte getId() {
        return TAG_INT;
    }

    private IntTag(int data) {
        this.data = Util.AntiNullFallback(data, 0);
    }

    public static IntTag valueOf(int data) {
        return new IntTag(data);
    }

    public int getAsInt() {
        return this.data;
    }
    public static int tagOf(Tag data) {
        if (data.getId() == TAG_INT) {
            return ((IntTag) data).data;
        } else throw new IllegalArgumentException("Tag is not Int type");
    }
}
