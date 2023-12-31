package io.github.felix3621.felixLib.nbt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class fileAccessor {
    public static CompoundTag read(Path file) {
        ArrayList<Byte> input = readArrayListFromBinaryFile(file.toString());

        //double check validity, and remove first element
        if (input.remove(0) != ByteTag.TAG_COMPOUND) {
            throw new UnsupportedOperationException("File invalid");
        }

        return readCompound(input);
    }
    public static void write(Path file, CompoundTag data) {
        ArrayList<Byte> output = writeCompound(data);
        writeArrayListToBinaryFile(output, file.toString());
    }

    private static ArrayList<Byte> writeByte(byte data) {
        ArrayList<Byte> arr = new ArrayList<>();
        arr.add(ByteTag.TAG_BYTE);
        arr.add(data);
        return arr;
    }
    private static ByteTag readByte(ArrayList<Byte> data) {
        return ByteTag.valueOf(data.remove(0));
    }
    private static ArrayList<Byte> writeShort(short data) {
        ArrayList<Byte> arr = new ArrayList<>();
        arr.add(ByteTag.TAG_SHORT);
        byte[] bytes = ByteBuffer.allocate(Short.BYTES).putShort(data).array();
        for (byte b : bytes) {
            arr.add(b);
        }
        return arr;
    }
    private static ShortTag readShort(ArrayList<Byte> data) {
        byte a = data.remove(0);
        byte b = data.remove(0);
        return ShortTag.valueOf(ByteBuffer.wrap(new byte[]{a,b}).getShort());
    }
    private static ArrayList<Byte> writeInt(int data) {
        ArrayList<Byte> arr = new ArrayList<>();
        arr.add(ByteTag.TAG_INT);
        byte[] bytes = ByteBuffer.allocate(Integer.BYTES).putInt(data).array();
        for (byte b : bytes) {
            arr.add(b);
        }
        return arr;
    }
    private static IntTag readInt(ArrayList<Byte> data) {
        byte a = data.remove(0);
        byte b = data.remove(0);
        byte c = data.remove(0);
        byte d = data.remove(0);
        return IntTag.valueOf(ByteBuffer.wrap(new byte[]{a,b,c,d}).getInt());
    }
    private static ArrayList<Byte> writeLong(long data) {
        ArrayList<Byte> arr = new ArrayList<>();
        arr.add(ByteTag.TAG_LONG);
        byte[] bytes = ByteBuffer.allocate(Long.BYTES).putLong(data).array();
        for (byte b : bytes) {
            arr.add(b);
        }
        return arr;
    }
    private static LongTag readLong(ArrayList<Byte> data) {
        byte a = data.remove(0);
        byte b = data.remove(0);
        byte c = data.remove(0);
        byte d = data.remove(0);
        byte e = data.remove(0);
        byte f = data.remove(0);
        byte g = data.remove(0);
        byte h = data.remove(0);
        return LongTag.valueOf(ByteBuffer.wrap(new byte[]{a,b,c,d,e,f,g,h}).getLong());
    }
    private static ArrayList<Byte> writeFloat(float data) {
        ArrayList<Byte> arr = new ArrayList<>();
        arr.add(ByteTag.TAG_FLOAT);
        byte[] bytes = ByteBuffer.allocate(Float.BYTES).putFloat(data).array();
        for (byte b : bytes) {
            arr.add(b);
        }
        return arr;
    }
    private static FloatTag readFloat(ArrayList<Byte> data) {
        byte a = data.remove(0);
        byte b = data.remove(0);
        byte c = data.remove(0);
        byte d = data.remove(0);
        return FloatTag.valueOf(ByteBuffer.wrap(new byte[]{a,b,c,d,}).getFloat());
    }
    private static ArrayList<Byte> writeDouble(double data) {
        ArrayList<Byte> arr = new ArrayList<>();
        arr.add(ByteTag.TAG_DOUBLE);
        byte[] bytes = ByteBuffer.allocate(Double.BYTES).putDouble(data).array();
        for (byte b : bytes) {
            arr.add(b);
        }
        return arr;
    }
    private static DoubleTag readDouble(ArrayList<Byte> data) {
        byte a = data.remove(0);
        byte b = data.remove(0);
        byte c = data.remove(0);
        byte d = data.remove(0);
        byte e = data.remove(0);
        byte f = data.remove(0);
        byte g = data.remove(0);
        byte h = data.remove(0);
        return DoubleTag.valueOf(ByteBuffer.wrap(new byte[]{a,b,c,d,e,f,g,h}).getDouble());
    }
    private static ArrayList<Byte> writeString(String data) {
        ArrayList<Byte> arr = new ArrayList<>();
        arr.add(ByteTag.TAG_STRING);
        arr.add((byte) data.length());
        for (byte b : data.getBytes()) {
            arr.add(b);
        }
        return arr;
    }
    private static StringTag readString(ArrayList<Byte> data) {
        //len
        int len = data.remove(0);
        StringBuilder var = new StringBuilder();
        //key
        while (len > 0) {
            var.append(Character.toChars(data.remove(0)));
            len -= 1;
        }
        return StringTag.valueOf(String.valueOf(var));
    }
    private static ArrayList<Byte> writeBoolean(boolean data) {
        ArrayList<Byte> arr = new ArrayList<>();
        arr.add(ByteTag.TAG_BOOLEAN);
        arr.add((byte) (data ? 1 : 0));
        return arr;
    }
    private static BooleanTag readBoolean(ArrayList<Byte> data) {
        return BooleanTag.valueOf(data.remove(0) == 1);
    }
    private static ArrayList<Byte> writeArray(ListTag data) {
        ArrayList<Byte> arr = new ArrayList<>();
        arr.add(ByteTag.TAG_LIST);
        byte type = data.getElementType();
        arr.add(type);
        for (int i = 0; i < data.size(); i++) {
            switch (type) {
                case 1 -> arr.addAll(writeByte(ByteTag.tagOf(data.get(i))));
                case 2 -> arr.addAll(writeShort(ShortTag.tagOf(data.get(i))));
                case 3 -> arr.addAll(writeInt(IntTag.tagOf(data.get(i))));
                case 4 -> arr.addAll(writeLong(LongTag.tagOf(data.get(i))));
                case 5 -> arr.addAll(writeFloat(FloatTag.tagOf(data.get(i))));
                case 6 -> arr.addAll(writeDouble(DoubleTag.tagOf(data.get(i))));
                case 7 -> arr.addAll(writeString(StringTag.tagOf(data.get(i))));
                case 8 -> arr.addAll(writeBoolean(BooleanTag.tagOf(data.get(i))));
                case 9 -> arr.addAll(writeArray((ListTag) data.get(i)));
                case 10 -> arr.addAll(writeCompound((CompoundTag) data.get(i)));
            }
        }
        arr.add(ByteTag.TAG_END);
        return arr;
    }
    private static ListTag readArray(ArrayList<Byte> data) {
        byte dataType = data.remove(0);
        ListTag rtn = new ListTag();
        while (true) {
            if (data.get(0) == (short)0) {
                data.remove(0);
                break;
            }
            switch (dataType) {
                case 1 -> rtn.add(readByte(data));
                case 2 -> rtn.add(readShort(data));
                case 3 -> rtn.add(readInt(data));
                case 4 -> rtn.add(readLong(data));
                case 5 -> rtn.add(readFloat(data));
                case 6 -> rtn.add(readDouble(data));
                case 7 -> rtn.add(readString(data));
                case 8 -> rtn.add(readBoolean(data));
                case 9 -> rtn.add(readArray(data));
                case 10 -> rtn.add(readCompound(data));
            }
        }
        return rtn;
    }
    private static ArrayList<Byte> writeCompound(CompoundTag data) {
        ArrayList<Byte> rtn = new ArrayList<>();

        rtn.add(ByteTag.TAG_COMPOUND);

        for (String key : data.getAllKeys()) {
            byte dataType = data.getTagType(key);
            //key-length
            rtn.add((byte) key.length());
            //key
            for (byte b : key.getBytes()) {
                rtn.add(b);
            }
            //value
            switch (dataType) {
                case 1 -> rtn.addAll(writeByte(ByteTag.tagOf(data.get(key))));
                case 2 -> rtn.addAll(writeShort(ShortTag.tagOf(data.get(key))));
                case 3 -> rtn.addAll(writeInt(IntTag.tagOf(data.get(key))));
                case 4 -> rtn.addAll(writeLong(LongTag.tagOf(data.get(key))));
                case 5 -> rtn.addAll(writeFloat(FloatTag.tagOf(data.get(key))));
                case 6 -> rtn.addAll(writeDouble(DoubleTag.tagOf(data.get(key))));
                case 7 -> rtn.addAll(writeString(StringTag.tagOf(data.get(key))));
                case 8 -> rtn.addAll(writeBoolean(BooleanTag.tagOf(data.get(key))));
                case 9 -> rtn.addAll(writeArray((ListTag) data.get(key)));
                case 10 -> rtn.addAll(writeCompound((CompoundTag) data.get(key)));
            }
        }
        rtn.add(ByteTag.TAG_END);
        return rtn;
    }

    private static CompoundTag readCompound(ArrayList<Byte> data) {
        CompoundTag rtn = new CompoundTag();

        while (true) {
            //is this the end of the compound?
            if (data.get(0) == (byte)0) {
                data.remove(0);
                break;
            }
            //key-length
            int keyLen = data.remove(0);
            StringBuilder key = new StringBuilder();
            //key
            while (keyLen > 0) {
                key.append(Character.toChars(data.remove(0)));
                keyLen -= 1;
            }

            int dataType = data.remove(0);

            switch (dataType) {
                case 1 -> rtn.put(String.valueOf(key), readByte(data));
                case 2 -> rtn.put(String.valueOf(key), readShort(data));
                case 3 -> rtn.put(String.valueOf(key), readInt(data));
                case 4 -> rtn.put(String.valueOf(key), readLong(data));
                case 5 -> rtn.put(String.valueOf(key), readFloat(data));
                case 6 -> rtn.put(String.valueOf(key), readDouble(data));
                case 7 -> rtn.put(String.valueOf(key), readString(data));
                case 8 -> rtn.put(String.valueOf(key), readBoolean(data));
                case 9 -> rtn.put(String.valueOf(key), readArray(data));
                case 10 -> rtn.put(String.valueOf(key), readCompound(data));
            }
        }

        return rtn;
    }

    private static void writeArrayListToBinaryFile(ArrayList<Byte> byteArrayList, String filePath) {
        try {
            Path path = Path.of(filePath);
            Files.createDirectories(path.getParent());
            if (!Files.exists(path)) Files.createFile(path);
            FileOutputStream fos = new FileOutputStream(filePath, false);
            // Convert ArrayList<Byte> to byte array
            byte[] byteArray = new byte[byteArrayList.size()];
            for (int i = 0; i < byteArrayList.size(); i++) {
                byteArray[i] = byteArrayList.get(i);
            }

            // Write the byte array to the file
            fos.write(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Byte> readArrayListFromBinaryFile(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);) {
            byte[] data =  fis.readAllBytes();
            ArrayList<Byte> arr = new ArrayList<>();
            for (byte b : data) {
                arr.add(b);
            }
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
