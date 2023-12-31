package io.github.felix3621.felixLib.nbt;

import io.github.felix3621.felixLib.Util;

public class ShortTag implements Tag{
    private final short data;

    @Override
    public byte getId() {
        return TAG_SHORT;
    }

    private ShortTag(short data) {
        this.data = Util.AntiNullFallback(data, (short)0);
    }

    public static ShortTag valueOf(short data) {
        return new ShortTag(data);
    }

    public short getAsShort() {
        return this.data;
    }
    public static short tagOf(Tag data) {
        if (data.getId() == TAG_SHORT) {
            return ((ShortTag) data).data;
        } else throw new IllegalArgumentException("Tag is not Short type");
    }
}
