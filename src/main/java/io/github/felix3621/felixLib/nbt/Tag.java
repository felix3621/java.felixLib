package io.github.felix3621.felixLib.nbt;

public interface Tag {
    byte TAG_END = 0;
    byte TAG_BYTE = 1;
    byte TAG_SHORT = 2;
    byte TAG_INT = 3;
    byte TAG_LONG = 4;
    byte TAG_FLOAT = 5;
    byte TAG_DOUBLE = 6;
    byte TAG_STRING = 7;
    byte TAG_BOOLEAN = 8;
    byte TAG_LIST = 9;
    byte TAG_COMPOUND = 10;

    byte getId();
}
