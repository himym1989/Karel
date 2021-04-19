package com.shpp.p2p.cs.lzhukova.assignment14;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.HashMap;

/**
 * This class implements the compressor and decompressor directly;
 */
public class Compressor {

    /**
     * @param inBytes - Array of bytes, that is made from incoming file, is passed to the method;
     * @return byte array, that contains compressed information;
     * also the result array contains some technical information:
     * first four bytes  - amount of unique symbols in file;
     * next eight bytes - size of incoming byte array;
     * next one byte - bits amount, that is needed to "code" one unique symbol;
     * This information is important for later unarchiving;
     */
    public byte[] archive(byte[] inBytes) {

        /**
         * Hashmap, that contains unique bytes(key) and bitsets(value) for coding;
         * */
        HashMap<Byte, BitSet> codes = new HashMap<>();
        for (byte inByte : inBytes) {
            codes.put(inByte, null);
        }

        // variable, that contains amount of bits, needed for coding. Depends on the amount of unique bytes
        // in the incoming file
        byte neededBitSetSize = (byte) (Math.ceil(Math.log(codes.size()) / Math.log(2)));
        // variable, that contains size of the hashmap with codes and size of incoming info in bytes;
        int dataInBytes = (inBytes.length * neededBitSetSize + codes.size() * neededBitSetSize) / 8 + 1;

        // byte buffer, that will contain all the encoded info before writing it to the output file;
        ByteBuffer buffer = ByteBuffer.allocate(4 + 8 + 1 + codes.size() + dataInBytes);

        //  amount of unique symbols in file;
        buffer.putInt(codes.size());
        // size of incoming byte array;
        buffer.putLong(inBytes.length);
        // bits amount, that is needed to "code" one unique symbol;
        buffer.put(neededBitSetSize);

        // bitset, that will be used for reading per
        BitSet bits = BitSet.valueOf(ByteBuffer.allocate(dataInBytes));


        int i = 1;
        int bitIndex = -1;
        for (Byte code : codes.keySet()) {

            BitSet codeBitSet = BitSet.valueOf(new byte[]{(byte) i}).get(0, neededBitSetSize);

            for (int k = 0; k < neededBitSetSize; k++) {
                bitIndex++;
                bits.set(bitIndex, codeBitSet.get(k));
            }

            codes.put(code, codeBitSet);
            buffer.put(code);
            i++;
        }

        for (byte b : inBytes) {
            BitSet codeBitSet = codes.get(b);
            for (int k = 0; k < neededBitSetSize; k++) {
                bitIndex++;
                bits.set(bitIndex, codeBitSet.get(k));
            }
        }

        buffer.put(bits.toByteArray());
        return buffer.array();
    }

    public byte[] unarchive(byte[] inBytes) {
        HashMap<BitSet, Byte> codes = new HashMap<>();

        int position = 0;

        ByteBuffer buffer = ByteBuffer.wrap(inBytes);

        int tableSize = buffer.getInt(position);
        position += 4;
        long dataSize = buffer.getLong(position);
        position += 8;
        int bitSetSize = buffer.get(position);
        position += 1;


        byte[] valuesList = new byte[tableSize];
        for (int i = 0; i < tableSize; i++) {
            valuesList[i] = buffer.get(position);
            position++;
        }

        byte[] restOfArchive = new byte[(int) (tableSize + dataSize) / 8 * bitSetSize + 1];
        for (int i = 0; i < (int) (tableSize + dataSize) / 8 * bitSetSize + 1; i++) {
            restOfArchive[i] = inBytes[position];
            position++;
        }

        BitSet dataInBits = BitSet.valueOf(restOfArchive);
        ByteBuffer result = ByteBuffer.allocate((int) dataSize);


        for (int i = 0, k = 0; i <= dataInBits.length(); i += bitSetSize, k++) {
            BitSet set = dataInBits.get(i, i + bitSetSize);
            if (k < tableSize) {
                codes.put(set, valuesList[k]);
            } else {
                result.put(codes.get(set));
            }
        }
        return result.array();
    }

}

