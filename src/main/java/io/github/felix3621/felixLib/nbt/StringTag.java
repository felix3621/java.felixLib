package io.github.felix3621.felixLib.nbt;

import io.github.felix3621.felixLib.Util;

public class StringTag implements Tag{
    private final String data;

    @Override
    public byte getId() {
        return TAG_STRING;
    }

    private StringTag(String data) {
        this.data = Util.AntiNullFallback(data, "");
    }

    public static StringTag valueOf(String data) {
        return new StringTag(data);
    }

    public String getAsString() {
        return this.data;
    }
    public static String tagOf(Tag data) {
        if (data.getId() == TAG_STRING) {
            return ((StringTag) data).data;
        } else throw new IllegalArgumentException("Tag is not String type");
    }
}
