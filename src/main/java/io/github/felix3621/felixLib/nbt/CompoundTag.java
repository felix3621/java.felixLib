package io.github.felix3621.felixLib.nbt;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CompoundTag implements Tag {
    private final Map<String, Tag> tags;

    @Override
    public byte getId() {
        return TAG_COMPOUND;
    }

    public CompoundTag() {
        this.tags = new HashMap<>();
    }
    public CompoundTag(Map<String, Tag> tags) {
        this.tags = tags;
    }

    public Set<String> getAllKeys() {
        return tags.keySet();
    }

    public void put(String key, Tag value) {
        if (value == null) throw new IllegalArgumentException("Invalid null NBT value with key " + key);
        this.tags.put(key, value);
    }

    public Tag get(String key) {
        return this.tags.get(key);
    }

    public byte getTagType(String key) {
        Tag tag = this.tags.get(key);
        return tag == null ? 0 : tag.getId();
    }

    public boolean contains(String key) {
        return this.tags.containsKey(key);
    }

    public boolean contains(String key, byte tagType) {
        if (contains(key)) {
            return this.getTagType(key) == tagType;
        } else return false;
    }

    public void remove(String key) {
        this.tags.remove(key);
    }

    public boolean isEmpty() {
        return this.tags.isEmpty();
    }
}
