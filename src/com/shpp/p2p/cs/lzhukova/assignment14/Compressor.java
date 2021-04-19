package com.shpp.p2p.cs.lzhukova.assignment14;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.HashMap;

public class Compressor {

    public byte[] archive(byte[] inBytes) {
//        System.out.println("In-array of bytes: " + Arrays.toString(inBytes));

        HashMap<Byte, BitSet> codes = new HashMap<>();
        for (byte inByte : inBytes) {
            codes.put(inByte, null);
        }

        byte neededBitSetSize =  (byte) (Math.ceil(Math.log(codes.size()) / Math.log(2)));
        int dataInBytes = (inBytes.length * neededBitSetSize + codes.size() * neededBitSetSize) / 8 + 1;

        ByteBuffer buffer = ByteBuffer.allocate(4 + 8 + 1 + codes.size()  + dataInBytes);

        long fileSizeInBytes = inBytes.length;

        buffer.putInt(codes.size());
        buffer.putLong(inBytes.length);
        buffer.put(neededBitSetSize);

//        System.out.println("buffer after putting unique symbols and size of the file in bytes: ");

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
//
//        System.out.println();
//        System.out.println("codes:" + codes.size());
//        System.out.println("bits:" + bits.size());

        buffer.put(bits.toByteArray());
        return buffer.array();
    }

    public byte[] unarchive(byte[] inBytes) {
        HashMap<BitSet, Byte> codes = new HashMap<>();

        int position = 0;
//
//        System.out.println("длина входящего массива(архив): " + inBytes.length);
//        System.out.println("весь входящий массив " + Arrays.toString(inBytes));

        ByteBuffer buffer = ByteBuffer.wrap(inBytes);

        int tableSize = buffer.getInt(position);
        position += 4;
        long dataSize = buffer.getLong(position);
        position += 8;
        int bitSetSize = buffer.get(position);
        position += 1;

//        System.out.println("table size " + buffer.getInt(position));
//        System.out.println("data length " + buffer.getLong(position));
//        System.out.println("size of bitset " + buffer.get(12));


        byte[] valuesList = new byte[tableSize];
        for (int i = 0; i < tableSize; i++) {
            valuesList[i] = buffer.get(position);
            position++;
        }
//        System.out.println("Value list " + Arrays.toString(valuesList));

        byte[] restOfArchive = new byte[(int) (tableSize + dataSize) / 8 * bitSetSize + 1];
//        System.out.println(restOfArchive.length);
        for (int i = 0; i < (int) (tableSize + dataSize) / 8 * bitSetSize + 1; i++) {
            restOfArchive[i] = inBytes[position];
            position++;
        }

//        System.out.println("ключи и текст: " + Arrays.toString(restOfArchive));
        BitSet dataInBits = BitSet.valueOf(restOfArchive);
//        System.out.println("значение и текст в битстете " + dataInBits);
//        System.out.println(dataInBits.size());
        int valuesInBytes = tableSize * bitSetSize; // 40
        ByteBuffer result = ByteBuffer.allocate((int) dataSize);


        for (int i = 0, k = 0; i <= dataInBits.length(); i += bitSetSize, k++) {
            BitSet set = dataInBits.get(i, i + bitSetSize);
            if (k < tableSize) {
                codes.put(set, valuesList[k]);
            } else {
                result.put(codes.get(set));
            }
        }

//        System.out.println(codes);
//        System.out.println(Arrays.toString(result.array()));
        return result.array();
    }

}

