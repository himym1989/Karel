package com.shpp.p2p.cs.lzhukova.assignment17.assignment15;


import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;

/**
 * This class implements decompressor with the use of the Huffman algorithm;
 */
public class Decompressor {
    // incoming file;
    byte[] fileInBytes;
    int inputFileSize;
    // buffer will contain info from the incoming file;
    ByteBuffer buffer;
    int treeSize;
    int bufferPosition = 0;
    LinkedList<Byte> treeLeaves;
    LinkedList<Boolean> treeShape;


    public Decompressor(byte[] bytes) {
        fileInBytes = bytes;
        treeLeaves = new LinkedList<>();
        treeShape = new LinkedList<>();
    }

    public byte[] decompress() {
        buffer = ByteBuffer.wrap(fileInBytes);
        treeSize = buffer.getShort();
        bufferPosition += Short.SIZE / Byte.SIZE;
        getTreeShape();
        inputFileSize = buffer.getInt(bufferPosition);
        bufferPosition += Integer.SIZE / Byte.SIZE;
        Node tree = unflattenTree();
        return decode(tree);
    }


    private void getTreeShape() {
        // get tree shape in the form of bit sequence: '1' - not leaf, '0' - leaf
        // dividing means, that we need position in bytes, but treeSize variable is represented in bits;
        int treeSizeInBytes = (int) (Math.ceil(treeSize / (double) Byte.SIZE));
        // converting tree shape in bytes to the bitset;
        BitSet bs = BitSet.valueOf(Arrays.copyOfRange(buffer.array(), bufferPosition, bufferPosition + treeSizeInBytes));
        bufferPosition += treeSizeInBytes;

        int treeLeavesAmount = 0;
        for (int i = 0; i < treeSize; i++) {
            boolean bit = bs.get(i);
            if (!bit) {
                treeLeavesAmount += 1;
            }
            treeShape.add(bit);
        }

        // adding tree leaves to the linked list;
        for (int i = bufferPosition; i < bufferPosition + treeLeavesAmount; i++) {
            treeLeaves.add(buffer.get(i));
        }
        // increasing buffer position for the amount of leaves(unique bytes);
        bufferPosition = bufferPosition + treeLeavesAmount;
    }


    private Node unflattenTree() {
        if (!treeShape.isEmpty()) {
            boolean isLeaf = !treeShape.pollFirst();
            Node node = new Node(-1);
            if (isLeaf) {
                node.setValue(treeLeaves.pollFirst());
                return node;
            } else {
                node.setLeftChild(unflattenTree());
                node.setRightChild(unflattenTree());
            }
            return node;
        }

        return null;
    }

    private byte[] decode(Node root) {
        // concerting decoded data of the incoming file to the bitset;
        BitSet dataInBits = BitSet.valueOf(Arrays.copyOfRange(buffer.array(), bufferPosition, buffer.capacity()));
        int bitIndex = 0;

        ByteBuffer result = ByteBuffer.allocate(inputFileSize);

        for (int i = 0; i < inputFileSize; i++) {
            Node node = root;
            while (!node.hasValue() && i < inputFileSize) {
                boolean bit = dataInBits.get(bitIndex);
                bitIndex += 1;
                if (bit) node = node.getRightChild();
                else node = node.getLeftChild();
            }
            if (node.hasValue()) {
                result.put(node.getValue());
            }
        }

        // preparing byte buffer for converting to byte array
        int pos = result.position();
        result.flip();
        byte[] resultArray = new byte[pos];
        result.get(resultArray);
        return resultArray;
    }
}