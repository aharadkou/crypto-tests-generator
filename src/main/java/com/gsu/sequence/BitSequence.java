package com.gsu.sequence;

import java.util.Arrays;

public class BitSequence {

    private byte[] bits;

    public BitSequence(final int bitCount) {
        bits = new byte[bitCount];
    }

    public BitSequence(final byte[] bits) {
        this.bits = Arrays.copyOf(bits, bits.length);
    }

    public void setBit(final int bitNumber, final byte value) {
        checkNumber(bitNumber);
        bits[bitNumber] = value;
    }

    public byte getBit(final int bitNumber) {
        checkNumber(bitNumber);
        return bits[bitNumber];
    }

    public byte[] getBits() {
        return Arrays.copyOf(bits, bits.length);
    }

    public void insertBit(final int position, final byte value) {
        checkNumber(position);
        byte[] newArray = Arrays.copyOf(bits, bits.length + 1);
        for (int i = position; i < bits.length; i++) {
            newArray[i + 1] = bits[i];
        }
        newArray[position] = value;
        bits = newArray;
    }

    public BitSequence addModTwo(final BitSequence added) {
        return addModN(added, 2);
    }

    public BitSequence addModN(final BitSequence added, final int n) {
        checkLength(added);
        BitSequence result = new BitSequence(getLength());
        for (int i = 0; i < result.getLength(); i++) {
            result.setBit(i, (byte)((getBit(i) + added.getBit(i)) % n));
        }
        return result;
    }

    public int getHammingDistance(final BitSequence other) {
        checkLength(other);
        int length = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] != other.bits[i]) {
                ++length;
            }
        }
        return length;
    }

    private void checkNumber(final int bitNumber) {
        if(bitNumber < 0 || bitNumber >= bits.length) {
            throw new IllegalArgumentException("Illegal bit number!");
        }
    }

    private void checkLength(final BitSequence other) {
        if(getLength() != other.getLength()) {
            throw new IllegalArgumentException("Elements of sequences must have equal length!");
        }
    }

    public int getLength() {
        return bits.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BitSequence)) {
            return false;
        }
        BitSequence that = (BitSequence) o;
        return Arrays.equals(bits, that.bits);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bits);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(byte bit : bits) {
            stringBuilder.append(bit);
        }
        return stringBuilder.toString();
    }
}
