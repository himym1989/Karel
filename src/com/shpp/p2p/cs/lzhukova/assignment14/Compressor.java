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
     * <p>
     * also the result array contains some technical information:
     * first four bytes  - amount of unique bytes in file;
     * next eight bytes - size of incoming byte array;
     * next one byte - bits amount, that is needed to "code" one unique byte;
     * This information is important for later unarchiving;
     */
    public byte[] archive(byte[] inBytes) {

        // Hashmap, that contains unique bytes(key) and bitsets(value) for coding;
        HashMap<Byte, BitSet> codes = new HashMap<>();

        for (byte inByte : inBytes) {
            codes.put(inByte, null);
        }

        // variable, that contains amount of bits, needed for coding. Depends on the amount of unique bytes
        // in the incoming file
        byte neededBitSetSize = (byte) (Math.ceil(Math.log(codes.size()) / Math.log(2)));
        System.out.println(neededBitSetSize);
        // variable, that contains size of the hashmap values and size of incoming info in bytes after coding;
        int dataInBytes = (inBytes.length * neededBitSetSize + codes.size() * neededBitSetSize) / 8 + 1;

        // byte buffer, that will contain all the "technical" and encoded info before writing it to the output file;
        ByteBuffer buffer = ByteBuffer.allocate(4 + 8 + 1 + codes.size() + dataInBytes);

        //  amount of unique symbols in file;
        buffer.putInt(codes.size());
        System.out.println(codes.size());
        // size of incoming byte array;
        buffer.putLong(inBytes.length);
        // bits amount, that is needed to "code" one unique symbol;
        buffer.put(neededBitSetSize);

        // bitset, that will contain values of unique bytes in bits and the rest info of the file;
        BitSet bits = BitSet.valueOf(ByteBuffer.allocate(dataInBytes));

        // will use iterator "i" for creating unique bit sequences.
        // We get byte value of number "0" and "cut" needed length and so on;
        int i = 0;
        // index in the bitset, where values of the hashmap and the encoded info will be written in;
        int bitIndex = -1;

        // iterating through hashmap keySet and creating bits sequences for each key;
        for (Byte code : codes.keySet()) {
            // creating new bits sequence; in different cases we need different amount of bits, this amount
            // is defined by the variable "neededBitSetSize". That's why we form bitset from "zero" position to
            // the position, that is defined by value of the variable "neededBitSetSize";
            BitSet codeBitSet = BitSet.valueOf(new byte[]{(byte) i}).get(0, neededBitSetSize);

            // saving bits sequences to the bitset
            for (int k = 0; k < neededBitSetSize; k++) {
                bitIndex++;
                // saving each bit of the bit sequence to the bitset;
                bits.set(bitIndex, codeBitSet.get(k));
            }

            // filling the hashmap with values(unique bitset sequences);
            codes.put(code, codeBitSet);
            // table with codes are saved to the archive the next way: at first keys, then values;
            // putting codes to the buffer;
            buffer.put(code);
            i++;
        }

        // iterating through byte array, formed from incoming file, getting unique value of the byte from hashmap
        // and saving code to the bitset;
        for (byte b : inBytes) {
            BitSet codeBitSet = codes.get(b);
            for (int k = 0; k < neededBitSetSize; k++) {
                bitIndex++;
                // saving each bit of the bit sequence to the bitset;
                bits.set(bitIndex, codeBitSet.get(k));
            }
        }

        // putting filled bitset to the buffer, converting it to byte array;
        buffer.put(bits.toByteArray());
        return buffer.array();
    }


    /**
     * This method implement unarchiving;
     *
     * @param inBytes - byte array, formed from incoming archived file;
     * @return unarchived byte array, that will be written to the file;
     */
    public byte[] unarchive(byte[] inBytes) {
        // Hashmap, that will contain unique bitsets as keys and unique bytes as values;
        HashMap<BitSet, Byte> codes = new HashMap<>();

        // iterator of the position un the incoming byte archive;
        int position = 0;

        // creating a bytebuffer from the incoming byte array;
        ByteBuffer buffer = ByteBuffer.wrap(inBytes);

        // getting all needed info about archive, that is saved at the beginning of the archived file;
        // size of the hashmap(equals to amount of unique bytes), takes first 4 bytes;
        int tableSize = buffer.getInt(position);
        position += 4;
        // size of file before archiving, take next 8 bytes;
        long dataSize = buffer.getLong(position);
        position += 8;
        // amount of bits, that was needed to encode the file, take 1 byte;
        int bitSetSize = buffer.get(position);
        position += 1;

        // filling the array with future values of the hashmap (unique bytes of the encoded file);
        byte[] valuesList = new byte[tableSize];
        for (int i = 0; i < tableSize; i++) {
            valuesList[i] = buffer.get(position);
            position++;
        }

        // filling the array with the rest of archive(unique bitset sequences as keys of hashmap and the encoded info,
        // formed into bytes);
        byte[] restOfArchive = new byte[(int) (tableSize + dataSize) / 8 * bitSetSize + 1];
        for (int i = 0; i < (int) (tableSize + dataSize) / 8 * bitSetSize + 1; i++) {
            restOfArchive[i] = inBytes[position];
            position++;
        }

        // forming bitset from the byte array in order to read it by needed amount of bits to decode the information;
        BitSet dataInBits = BitSet.valueOf(restOfArchive);
        // initializing result buffer, that will contain decoded info;
        ByteBuffer result = ByteBuffer.allocate((int) dataSize*10);

        // iterating through the bitset with unique bitset sequences and encoded info;
        // if iterator is less then size of the table - we put sequence of bits to the hashmap as a key and
        // byte from array with values as a value;
        // else we decode the rest of info using formed hashmap;
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

