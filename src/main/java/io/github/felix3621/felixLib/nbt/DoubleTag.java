package io.github.felix3621.felixLib.nbt;

import io.github.felix3621.felixLib.Util;

public class DoubleTag implements Tag{
    private final double data;

    @Override
    public byte getId() {
        return TAG_DOUBLE;
    }

    private DoubleTag(double data) {
        this.data = Util.AntiNullFallback(data, 0d);
    }

    public static DoubleTag valueOf(double data) {
        return new DoubleTag(data);
    }

    public double getAsDouble() {
        return this.data;
    }
    public static double tagOf(Tag data) {
        if (data.getId() == TAG_DOUBLE) {
            return ((DoubleTag) data).data;
        } else throw new IllegalArgumentException("Tag is not Double type");
    }
}
