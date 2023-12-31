package io.github.felix3621.felixLib;

import io.github.felix3621.felixLib.nbt.*;

import java.nio.ByteBuffer;
import java.nio.file.Path;

public class Main {
    /**
     * This function only exists to test the different parts of the library
     * It is not made to be run outside of library development
     * @param args not in use
     */
    public static void main(String[] args) {
        //util test
        String test = null;
        System.out.println("AntiNull: " + Util.AntiNullFallback(test, "void"));
        System.out.println("Random.Int: " + Util.Random.Int(0,10));
        System.out.println("Random.Float: " + Util.Random.Float(0.0F, 1.0F, 5));

        //Logger test: basic, sub, file
        Logger log_main = new Logger(Path.of("./log"), "main");
        log_main.debug("basic: debug message");
        log_main.info("basic: info message");
        log_main.warn("basic: warn message");
        log_main.error("basic: error message");

        Logger log_sub = new Logger(log_main, "sub");
        log_sub.debug("sub: debug message");
        log_sub.info("sub: info message");
        log_sub.warn("sub: warn message");
        log_sub.error("sub: error message");

        Logger log_file = new Logger(log_sub.getOrCreateFile(), "file");
        log_file.debug("file: debug message");
        log_file.info("file: info message");
        log_file.warn("file: warn message");
        log_file.error("file: error message");

        log_sub.end();
        log_file.end();

        //nbt test:write
        CompoundTag compound = new CompoundTag();
        compound.put("BooleanTest", BooleanTag.valueOf(true));
        compound.put("ByteTest", ByteTag.valueOf((byte)12));
        compound.put("CompoundTest", new CompoundTag());
        compound.put("DoubleTest", DoubleTag.valueOf(15d));
        compound.put("FloatTest", FloatTag.valueOf(15f));
        compound.put("IntTest", IntTag.valueOf(11));
        compound.put("ListTest", new ListTag());
        compound.put("LongTest", LongTag.valueOf(15L));
        compound.put("ShortTest", ShortTag.valueOf((short)15));
        compound.put("StringTest", StringTag.valueOf("Bob"));
        fileAccessor.write(Path.of("./alpha/test.nbt"),compound);

        //nbt test:read
        CompoundTag file = fileAccessor.read(Path.of("./alpha/test.nbt"));
        System.out.println(file.getAllKeys());
    }
}