package de.comparus.opensource.longmap;

import java.lang.reflect.Array;

public class LongMapImpl<V> implements LongMap<V> {

    private static final int DEF_SIZE = 16;
    private int capacity;
    private Entry<V> [] table;
    private int size = 0;

    public LongMapImpl()
    {
        this(DEF_SIZE);
    }

    public LongMapImpl(int capacity)
    {
        this.capacity = capacity;
        table = (Entry<V>[]) new Entry[capacity];
    }

    public V put(long key, V value) {
        V result = remove(key);

        if (reHashNeeded())
            reHash();
        int hash = hash(key);
        Entry<V> entry = new Entry<>(key, value);
        entry.next = table[hash];
        if (entry.next!=null)
            entry.next.previous = entry;
        table[hash] = entry;
        size++;

        return result;
    }

    public V remove(long key) {
        int hash = hash(key);
        Entry<V> entry = table[hash];
        V result = null;
        while (entry != null)
        {
            if (entry.key == key)
            {
                result = entry.value;
                if(entry.previous!=null)
                    entry.previous.next = entry.next;
                else
                    table[hash] = entry.next;
                entry = null;
                size--;
            }
            else
            {
                entry = entry.next;
            }
        }
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        int hash = hash(key);
        Entry<V> entry = table[hash];
        boolean result = false;
        while (entry != null)
        {
            if (entry.key == key)
            {
                result = true;
                break;
            }
            entry = entry.next;
        }
        return result;
    }

    public boolean containsValue(V value) {
        for (Entry<V> entry : table )
        {
            while (entry!=null)
            {
                if (entry.value.equals(value))
                    return true;
                entry = entry.next;
            }
        }
        return false;
    }

    public long[] keys() {
        long[] result = new long[size];
        int index = 0;
        for (Entry<V> entry : table )
        {
            while (entry!=null)
            {
                result[index++] = entry.key;
                entry = entry.next;
            }
        }
        return result;
    }


    public V[] values() {

        V[] result = (V[])new Object[size];
        int index = 0;
        for (Entry<V> entry : table )
        {
            while (entry!=null)
            {
                result[index++] = entry.value;
                entry = entry.next;
            }
        }

        V[] resultArray = (V[])Array.newInstance(result[0].getClass(), size);
        System.arraycopy(result, 0, resultArray, 0, size);
        return resultArray;
    }

    public long size() {
        return size;
    }

    public void clear() {
        table = (Entry<V>[]) new Entry[DEF_SIZE];
        size = 0;
    }

    private int hash(long key)
    {
        return (int)(key% capacity);
    }

    private void reHash()
    {
        capacity *=2;
        Entry<V>[] tmpTable = table;
        table = (Entry<V>[]) new Entry[capacity];
        size = 0;

        for(Entry<V> e : tmpTable)
        {
            while (e!=null)
            {
                put(e.key, e.value);
                e = e.next;
            }
        }
    }

    private boolean reHashNeeded()
    {
        if (size > 0)
            return ((1.0 * size)/ capacity) > (0.7);
        else
            return false;
    }

    public V get(long key) {
        int hash = hash(key);
        Entry<V> entry = table[hash];
        while (entry!=null)
        {
            if (key == entry.key)
                break;
            else
                entry = entry.next;
        }
        return entry != null ? entry.value : null;
    }

    class Entry<V> {
        long key;
        V value;
        Entry<V> next;
        Entry<V> previous;

        Entry(long key, V value)
        {
            this.key = key;
            this.value = value;
        }

    }
}
