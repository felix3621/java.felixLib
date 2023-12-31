package io.github.felix3621.felixLib.nbt;

import java.util.ArrayList;
import java.util.List;

public class ListTag implements Tag{
    private final List<Tag> list;
    private byte type;
    @Override
    public byte getId() {
        return TAG_LIST;
    }
    public ListTag(ArrayList<Tag> list, byte type) {
        this.list = list;
        this.type = type;
    }
    public ListTag() {
        this(new ArrayList<>(), (byte)0);
    }

    public void clear() {
        this.list.clear();
        this.type = 0;
    }

    public byte getElementType() {
        return this.type;
    }

    private boolean updateType(Tag tag) {
        if (tag.getId() == 0) {
            return false;
        } else if (this.type == 0) {
            this.type = tag.getId();
            return true;
        } else {
            return this.type == tag.getId();
        }
    }

    public boolean setTag(int index, Tag tag) {
        if (this.updateType(tag)) {
            this.list.set(index, tag);
            return true;
        } else {
            return false;
        }
    }

    public boolean addTag(int index, Tag tag) {
        if (this.updateType(tag)) {
            this.list.add(index, tag);
            return true;
        } else {
            return false;
        }
    }

    public boolean add(Tag tag) {
        if (this.updateType(tag)) {
            this.list.add(tag);
            return true;
        } else {
            return false;
        }
    }

    public Tag get(int index) {
        return this.list.get(index);
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public Tag remove(int index) {
        Tag tag = this.list.remove(index);
        if (this.list.isEmpty()) {
            this.type = 0;
        }
        return tag;
    }
}
