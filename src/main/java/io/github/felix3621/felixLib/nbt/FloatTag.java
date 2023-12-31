package io.github.felix3621.felixLib.nbt;

import io.github.felix3621.felixLib.Util;

public class FloatTag implements Tag{
    private final float data;

    @Override
    public byte getId() {
        return TAG_FLOAT;
    }

    private FloatTag(float data) {
        this.data = Util.AntiNullFallback(data, 0f);
    }

    public static FloatTag valueOf(float data) {
        return new FloatTag(data);
    }

    public float getAsFloat() {
        return this.data;
    }
    public static float tagOf(Tag data) {
        if (data.getId() == TAG_FLOAT) {
            return ((FloatTag) data).data;
        } else throw new IllegalArgumentException("Tag is not Float type");
    }
}
