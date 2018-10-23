package de.comparus.opensource.longmap;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LongMapTest
{
    private LongMap<String> target;
    private static final int DEFAULT_SIZE_FOR_TESTS = 2;

    @Before
    public void prepare()
    {
        target = new LongMapImpl<>(DEFAULT_SIZE_FOR_TESTS);
        target.put(101, "101");
        target.put(102, "102");
        target.put(103, "103");
        target.put(104, "104");
    }

    @Test
    public void testGet()
    {
        assertEquals("101",target.get(101));
        assertEquals("102",target.get(102));
        assertEquals("103",target.get(103));
        assertEquals("104",target.get(104));
    }

    @Test
    public void testPut()
    {
        assertEquals("101",target.get(101));
        assertEquals("102",target.get(102));
        assertEquals("103",target.get(103));
        assertEquals("104",target.get(104));
    }

    @Test
    public void testPutEqual()
    {
        target.put(101, "NEW");
        assertEquals("NEW", target.get(101));
        assertEquals(4, target.size());
    }

    @Test
    public void testRemove()
    {
        String removed = target.remove(103);
        assertEquals("103", removed);
        assertEquals(3, target.size());

        removed = target.remove(101);
        assertEquals("101", removed);
        assertEquals(2, target.size());

        removed = target.remove(104);
        assertEquals("104", removed);
        assertEquals(1, target.size());

        removed = target.remove(888);
        assertNull(removed);
        assertEquals(1, target.size());

        removed = target.remove(102);
        assertEquals("102", removed);
        assertEquals(0, target.size());

        removed = target.remove(101);
        assertNull(removed);
    }

    @Test
    public void testIsEmpty()
    {
        assertFalse(target.isEmpty());
        target.remove(101);
        target.remove(102);
        target.remove(103);
        target.remove(104);
        assertTrue(target.isEmpty());
    }

    @Test
    public void testContainsKey()
    {
        assertTrue(target.containsKey(101));
        assertFalse(target.containsKey(105));
    }

    @Test
    public void testContainsValue()
    {
        assertTrue(target.containsValue("101"));
        assertFalse(target.containsValue("105"));
    }

    @Test
    public void testKeys()
    {
        long[] expectedKeys = new long[]{101, 102,103,104};
        long [] actualKeys = target.keys();
        Arrays.sort(actualKeys);
        assertArrayEquals(expectedKeys, actualKeys);
    }

    @Test
    public void testValues()
    {
        String[] expectedValues = new String[]{"101", "102", "103", "104"};
        String[] actualValues = target.values();
        Arrays.sort(actualValues);
        assertArrayEquals(expectedValues, actualValues);
    }

    @Test
    public void testSize()
    {
        assertEquals(4, target.size());
    }

    @Test
    public void testClear()
    {
        target.clear();
        assertEquals(0, target.size());
    }

}
