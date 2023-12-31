package io.github.felix3621.felixLib.nbt;

import io.github.felix3621.felixLib.Util;

public class LongTag implements Tag{
    private final long data;

    @Override
    public byte getId() {
        return TAG_LONG;
    }

    private LongTag(long data) {
        this.data = Util.AntiNullFallback(data, 0L);
    }

    public static LongTag valueOf(long data) {
        return new LongTag(data);
    }

    public float getAsLong() {
        return this.data;
    }
    public static long tagOf(Tag data) {
        if (data.getId() == TAG_LONG) {
            return ((LongTag) data).data;
        } else throw new IllegalArgumentException("Tag is not Long type");
    }
}
