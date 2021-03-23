package com.gsu.sequence;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class BitSequenceTest {


    @Test
    public void insert() {
        //given
        byte[] bits = {0, 1, 0};
        int insertedPosition = 2;
        byte inserted = 1;
        byte[] expected = {0, 1, 1, 0};
        //when
        BitSequence bitSequence = new BitSequence(bits);
        bitSequence.insertBit(insertedPosition, inserted);
        //then
        Assert.assertArrayEquals(expected, bitSequence.getBits());
    }

    @Test
    public void insertZero() {
        //given
        byte[] bits = {0, 1, 0};
        int insertedPosition = 0;
        byte inserted = 1;
        byte[] expected = {1, 0, 1, 0};
        //when
        BitSequence bitSequence = new BitSequence(bits);
        bitSequence.insertBit(insertedPosition, inserted);
        //then
        Assert.assertArrayEquals(expected, bitSequence.getBits());
    }

    @Test
    public void pow2 () {
        Assert.assertTrue(BigInteger.valueOf(7).testBit(0));
    }

}
