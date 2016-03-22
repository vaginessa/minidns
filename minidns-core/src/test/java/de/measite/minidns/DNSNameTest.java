/*
 * Copyright 2015 the original author or authors
 *
 * This software is licensed under the Apache License, Version 2.0,
 * the GNU Lesser General Public License version 2 or later ("LGPL")
 * and the WTFPL.
 * You may choose either license to govern your use of this software only
 * upon the condition that you accept all of the terms of either
 * the Apache License 2.0, the LGPL 2.1+ or the WTFPL.
 */
package de.measite.minidns;

import static de.measite.minidns.Assert.assertCsEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.junit.Test;

public class DNSNameTest {

    @Test
    public void sizeTest() {
        assertEquals(1, (DNSName.from("")).size());
        assertEquals(13, (DNSName.from("example.com")).size());
        assertEquals(16, (DNSName.from("dömäin")).size());
        assertEquals(24, (DNSName.from("dömäin.example")).size());
    }

    @Test
    public void toByteArrayTest() {
        assertArrayEquals(new byte[]{0}, DNSName.from("").getBytes());
        assertArrayEquals(new byte[]{7, 'e', 'x', 'a', 'm', 'p', 'l', 'e', 0}, DNSName.from("example").getBytes());
        assertArrayEquals(new byte[]{7, 'e', 'x', 'a', 'm', 'p', 'l', 'e', 3, 'c', 'o', 'm', 0}, DNSName.from("example.com").getBytes());
        assertArrayEquals(new byte[]{14, 'x', 'n', '-', '-', 'd', 'm', 'i', 'n', '-', 'm', 'o', 'a', '0', 'i', 0}, DNSName.from("dömäin").getBytes());
    }

    @Test
    public void parseTest() throws IOException {
        byte[] test = new byte[]{7, 'e', 'x', 'a', 'm', 'p', 'l', 'e', 0};
        assertCsEquals("example", DNSName.parse(new DataInputStream(new ByteArrayInputStream(test)), test));
        test = new byte[]{7, 'e', 'x', 'a', 'm', 'p', 'l', 'e', 3, 'c', 'o', 'm', 0};
        assertCsEquals("example.com", DNSName.parse(new DataInputStream(new ByteArrayInputStream(test)), test));
    }

    @Test
    public void equalsTest() {
        assertEquals(DNSName.from(""), DNSName.from("."));
    }
}
