package com.gsu.sequence;

import static com.gsu.utils.MathUtils.nextIntInclusive;

public class RandomBitSequenceGenerator implements SequenceFiller {

    public BitSequence fillSequence(final BitSequence bitSequence, final int codeDimension) {
        for(int i = 0; i < bitSequence.getLength(); i++) {
            bitSequence.setBit(i, (byte) nextIntInclusive(0, codeDimension - 1));
        }
        return bitSequence;
    }

}
