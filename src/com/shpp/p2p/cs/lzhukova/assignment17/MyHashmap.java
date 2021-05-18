package com.shpp.p2p.cs.lzhukova.assignment17;


import java.util.*;

public class MyHashmap<K, V> {
    int DEFAULT_CAPACITY = 32;
    Entry<K, V>[] table = new Entry[DEFAULT_CAPACITY];
    MyArrayList<K> keys = new MyArrayList<>();
    private int size = 0;

    /**
     * Method implements putting pair of key and value to the hashmap;
     * if the hashmap already contains the key - its value changes to the new one, that comes with arguments.
     *
     * @param key   - key to add;
     * @param value - value to add;
     * @return - V - previous value;
     */
    public V put(K key, V value) {
        int bucket = getBucket(key);
        Entry<K, V> newEntry = new Entry<>(key, value, hash(key), null);

        if (table[bucket] != null) {
            Entry<K, V> entry = table[bucket];
            do {
                if (entry.key.hashCode() == newEntry.key.hashCode()) {
                    if (Objects.equals(entry.key, newEntry.key)) {
                        V prevVal = entry.value;
                        entry.value = newEntry.value;
                        return prevVal;
                    }
                }
                if (entry.next != null) {
                    entry = entry.next;
                }
            }
            while (entry.hasNext());
            entry.next = newEntry;
//            System.out.println(entry.next.key.toString());
        } else {
            table[bucket] = newEntry;
//            System.out.println(newEntry.key.toString());
        }

        if (!keys.contains(key)) {
            keys.add(key);
        }
        size++;

        return null;
    }

    /**
     * Method implements search for a value by key;
     *
     * @return - V, value of the key, that is passed to the method; or null,
     * if the key or value hasn't been added to the hashmap;
     */
    public V get(K key) {
        int bucket = getBucket(key);
        if (table[bucket] != null) {
            Entry<K, V> entry = table[bucket];
            do {
                if (Objects.equals(entry.key, key)) {
                    return entry.value;
                }
                if (entry.next != null) {
                    entry = entry.next;
                }
            }
            while (entry.hasNext());
            return entry.value;
        }
        return null;
    }

    /**
     * Getting bucket by the hashcode;
     *
     * @param key - key to get hashcode;
     * @return - bucket, that contains the key;
     */
    private int getBucket(K key) {
        return hash(key) % DEFAULT_CAPACITY;
    }

    /**
     * @param key - key to be found;
     * @return - true, if the hashmap contains a key, false  - if doesn't;
     */
    public boolean containsKey(K key) {
        if (keys == null) return false;
        return keySet().contains(key);
    }

    /**
     * @param value - value to be found;
     * @return - true, if the hashmap contains a value, false  - if doesn't;
     */
    public boolean containsValue(V value) {
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            if (table[i] != null) {
                Entry<K, V> entry = table[i];
                do {
                    if ((Objects.equals(entry.value, value))) {
                        return true;
                    }
                }
                while (entry.hasNext());
            }
        }
        return false;
    }

    /**
     * @return - size of the hashmap(amount of nodes);
     */
    public int size() {
        return size;
    }

    /**
     * Clearing a hashmap;
     */
    public void clear() {
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            table[i] = null;
        }
        keys.clear();
        size = 0;
    }

    protected int hash(K key) {
        return key.hashCode();
    }

    public boolean isEmpty() {
        return keys.isEmpty();
    }

    public String toString() {
        Iterator<Entry<K, V>> iterator = entrySet().iterator();
        StringBuilder sb;
        String[] array = new String[size()];
        int counter = 0;
        do {
            Entry<K, V> entry = iterator.next();
            sb = new StringBuilder();
            sb.append(entry.key.toString()).append("=").append(entry.value.toString());
            array[counter] = sb.toString();
            counter++;
        } while (iterator.hasNext());

//        String[] arr = new String[size];
//        StringBuilder sb;
//
//        int counter = 0;
//        System.out.println(size);
//        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
//            if (table[i] != null) {
//                Entry<K, V> entry = table[i];
//                do {
//                    sb = new StringBuilder();
//                    sb.append(entry.key.toString()).append("=").append(entry.value.toString());
//                    arr[counter] = sb.toString();
//                    counter++;
//                    entry = entry.next;
//                }
//                while (entry!=null && entry.hasNext());
//                System.out.println("bucket " + i);
//            }
//        }
        return Arrays.toString(array);
    }

    public AbstractSet<K> keySet() {
        return new AbstractSet<>() {
            @Override
            public Iterator<K> iterator() {
                return new Iterator<>() {
                    int position = 0;

                    @Override
                    public boolean hasNext() {
                        return position < keys.size();
                    }

                    @Override
                    public K next() {
                        return keys.get(position++);
                    }
                };

            }

            @Override
            public int size() {
                return keys.size();
            }
        };
    }

    public AbstractSet<Entry<K, V>> entrySet() {
        return new AbstractSet<>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<>() {
                    int index = 0;

                    @Override
                    public boolean hasNext() {
                        return index < size();
                    }

                    @Override
                    public Entry<K, V> next() {
                        K key = keys.get(index++);

                        return new Entry<>(key, get(key), hash(key), null);
                    }
                };

            }

            @Override
            public int size() {
                return keys.size();
            }
        };
    }

    static class Entry<K, V> implements Map.Entry<K, V> {
        protected K key;
        protected V value;
        protected int hash;
        Entry<K, V> next;

        Entry(K key, V value, int hash, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }

        public boolean hasNext() {
            return next != null;
        }
    }
}
