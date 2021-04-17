package com.shpp.p2p.cs.lzhukova.assignment14;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

public class Compressor {

    public byte[] archive(byte[] inBytes) {
        System.out.println("In-array of bytes: " + Arrays.toString(inBytes));

        HashMap<Byte, BitSet> codes = new HashMap<>();
        for (byte inByte : inBytes) {
            codes.put(inByte, null);
        }

        byte neededBitSetSize = (byte) Math.ceil(Math.sqrt(codes.size()));
//        int codesInBytes = (int) Math.ceil(codes.size() * neededBitSetSize / 8);
        int dataInBytes = (inBytes.length * neededBitSetSize + codes.size() * neededBitSetSize) / 8 + 1;
        System.out.println(dataInBytes);

        ByteBuffer buffer = ByteBuffer.allocate(4 + 8 + codes.size() + 1 + dataInBytes);
        System.out.println("Unique symbols " + codes.size());
        System.out.println("Inbyte size " + inBytes.length);
        System.out.println("neededBitSetSize " + (int) Math.ceil(Math.sqrt(codes.size())));
        long fileSizeInBytes = inBytes.length;

        buffer.putInt(codes.size());
        buffer.putLong(inBytes.length);
        buffer.put(neededBitSetSize);

        System.out.println("buffer after putting unique symbols and size of the file in bytes: ");
        System.out.println(Arrays.toString(buffer.array()));

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

        System.out.println();
        System.out.println("codes:" + codes.size());
        System.out.println("bits:" + bits.size());

        buffer.put(bits.toByteArray());
//        System.out.println(Arrays.toString(bits.toByteArray()));
        return buffer.array();
    }

    public byte[] unarchive(byte[] inBytes) {
        HashMap<BitSet, Byte> codes = new HashMap<>();

        int position = 0;

        System.out.println(inBytes.length);
        ByteBuffer buffer = ByteBuffer.wrap(inBytes);
//        System.out.println("table size " + buffer.getInt(position));
//        System.out.println("data length " + buffer.getLong(position));
//        System.out.println("size of bitset " + buffer.get(12));

        int tableSize = buffer.getInt(position);
        position += 4;
        long dataSize = buffer.getLong(position);
        position += 8;
        int bitSetSize = buffer.get(position);
        position += 1;

        byte[] keysList = new byte[tableSize];
        for (int i = 0; i < tableSize; i++) {
            keysList[i] = buffer.get(position);
            position++;
        }



//            for (byte b : inBytes) {
//                BitSet codeBitSet = codes.get(b);
//                for (int k = 0; k < neededBitSetSize; k++) {
//                    bitIndex++;
//                    bits.set(bitIndex, codeBitSet.get(k));
//                }
//            }
        System.out.println(Arrays.toString(keysList));
        System.out.println(codes);
        return inBytes;
    }

}

