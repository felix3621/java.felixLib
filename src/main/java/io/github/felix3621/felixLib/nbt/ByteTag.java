package io.github.felix3621.felixLib.nbt;

import io.github.felix3621.felixLib.Util;

public class ByteTag implements Tag{
    private final byte data;

    @Override
    public byte getId() {
        return TAG_BYTE;
    }

    private ByteTag(byte data) {
        this.data = Util.AntiNullFallback(data, (byte)0);
    }

    public static ByteTag valueOf(byte data) {
        return new ByteTag(data);
    }

    public byte getAsByte() {
        return this.data;
    }
    public static byte tagOf(Tag data) {
        if (data.getId() == TAG_BYTE) {
            return ((ByteTag) data).data;
        } else throw new IllegalArgumentException("Tag is not Byte type");
    }
}
