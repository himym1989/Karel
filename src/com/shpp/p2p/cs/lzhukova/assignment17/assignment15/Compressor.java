package com.shpp.p2p.cs.lzhukova.assignment17.assignment15;

import com.shpp.p2p.cs.lzhukova.assignment15.Node;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * This class implements file compressor with the use of
 * Huffman algorithm;
 */
public class Compressor {
    // allocating place for tree size;
    private static final byte TREE_SIZE = 2;
    // hashmap with unique bytes if the incoming file and their frequencies;
    HashMap<Byte, Integer> bytesAndFrequencies = new HashMap<>();
    LinkedList<com.shpp.p2p.cs.lzhukova.assignment15.Node> huffmanTree;
    // contains tree shape in boolean: 1 - not leaf(node has no value), 0 - leaf;
    LinkedList<Boolean> treeShape = new LinkedList<>();
    // hashmap with unique bytes if the incoming file and their codes, created by using Huffman algorithm;
    HashMap<Byte, String> bytesAndCodes = new HashMap<>();
    // contains value of nodes - unique bytes of incoming file;
    LinkedList<Byte> nodes = new LinkedList<>();
    // incoming byte array;
    byte[] fileInBytes;

    public Compressor(byte[] inBytes) {
        fileInBytes = inBytes;
    }

    public byte[] compress() throws Exception {
        getBytesAndFrequencies();
        huffmanTree = buildHuffmanTree();
        Node root = huffmanTree.peekFirst();

        buildHuffmanCode(root, "");

        // allocating place for size of the tree(2 bytes), shape of the tree, unique bytes of the incoming file
        // and encoded data
        // will contain all needed info before writing to the outgoing file;
        ByteBuffer data = ByteBuffer.allocate(TREE_SIZE + (short) treeShape.size() + bytesAndCodes.size() + 4
                + fileInBytes.length);
        // put size of the tree to the buffer;
        data.putShort((short) treeShape.size());

        // write tree shape
        BitSet bs = new BitSet();
        int bsIndex = 0;
        for (Boolean bit : treeShape) {
            bs.set(bsIndex, bit);
            bsIndex += 1;
        }

        // counting tree size in bytes in order to write it to the bytebuffer;
        int treeSizeInBytes = (int) (Math.ceil(treeShape.size() / (double) Byte.SIZE));
        // put tree shape to the buffer
        data.put(Arrays.copyOf(bs.toByteArray(), treeSizeInBytes));

        // put nodes values to the buffer;
        for (Byte node : nodes) {
            data.put(node);
        }
        // put size of the incoming file to the buffer(needed for correct decompressing);
        data.putInt(fileInBytes.length);
        // put encoded info to the buffer;
        data.put(encodeFile().toByteArray());

        // preparing byte buffer for converting to byte array
        int pos = data.position();
        data.flip();
        byte[] result = new byte[pos];
        data.get(result);

        return result;
    }

    /**
     * Method implements encoding file using generated codes;
     */
    private BitSet encodeFile() {
        BitSet bs = new BitSet();
        int bsIndex = 0;
        // iterating through incoming array;
        for (byte b : fileInBytes) {
            String code = bytesAndCodes.get(b);
            for (int i = 0; i < code.length(); i++) {
                bs.set(bsIndex, code.charAt(i) != '0');
                bsIndex++;
            }
        }
        return bs;
    }

    /**
     * Method implements generating codes for incoming bytes;
     *
     * @param node node of the tree
     * @param code - String, code to be generated;
     */
    private void buildHuffmanCode(com.shpp.p2p.cs.lzhukova.assignment15.Node node, String code) {
        if (!node.hasValue()) {
            treeShape.add(true);
            buildHuffmanCode(node.getLeftChild(), code + '0');
            buildHuffmanCode(node.getRightChild(), code + '1');
        } else {
            treeShape.add(false);
            bytesAndCodes.put(node.getValue(), code);
            nodes.add(node.getValue());
        }
    }


    /**
     * Building Huffman tree using linked list
     *
     * @return Linked List with built tree;
     */
    private LinkedList<Node> buildHuffmanTree() {
        LinkedList<Node> huffmanTree = new LinkedList<>();

        for (Object o : bytesAndFrequencies.entrySet()) {
            Map.Entry pair = (Map.Entry) o;

            Byte value = (Byte) pair.getKey();
            Integer frequency = (Integer) pair.getValue();

            Node node = new Node(frequency);
            node.setValue(value);
            huffmanTree.add(node);
        }

        while (huffmanTree.size() > 1) {
            // sorting linked list by value(frequency of bytes) in ascending order;
            huffmanTree.sort(Comparator.comparingInt(Node::getFrequency));

            Node leftChild = huffmanTree.pollFirst();
            Node rightChild = huffmanTree.pollFirst();

            Node newNode = new Node(leftChild.getFrequency() + rightChild.getFrequency());
            newNode.setLeftChild(leftChild);
            newNode.setRightChild(rightChild);
            huffmanTree.addFirst(newNode);
        }

        return huffmanTree;
    }

    /**
     * filling in hashmap with unique bytes of the file and their frequencies;
     *
     * @return filled in hashmap;
     */
    private void getBytesAndFrequencies() throws Exception {
        // Hashmap, that contains unique bytes(key) and their frequency;
        int frequency = 1;
        for (byte inByte : fileInBytes) {
            if (!bytesAndFrequencies.containsKey(inByte)) {
                bytesAndFrequencies.put(inByte, frequency);
            } else {
                bytesAndFrequencies.put(inByte, bytesAndFrequencies.get(inByte) + 1); // 1 - iterator of frequency;
            }
        }
        if (bytesAndFrequencies.size() < 2) {
            throw new Exception("ERROR! File should have minimum 2 unique symbols.");
        }
    }
}
