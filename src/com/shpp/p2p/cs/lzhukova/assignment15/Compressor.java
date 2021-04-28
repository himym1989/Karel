package com.shpp.p2p.cs.lzhukova.assignment15;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * This class implements the compressor using Haffman algorithm;
 */
public class Compressor implements Constants {
    // Contains unique bytes of the file and their frequencies;
    HashMap bytesAndFrequencies;

    LinkedList huffmanTree;

    // Contains unique bytes and their generated codes;
    HashMap<Byte, String> bytesAndCodes;

    // structure of the tree in string representation(0 - is a leaf; 1 - is not a leaf);
    String treeStructure;

    // buffer, that contain unique bytes to be encoded; will be written to general buffer and then to output file
    // in order to restore the Huffman tree after decompression;
    ByteBuffer bufferForBytes;
    // buffer, that contains encoded file in bytes;
    ByteBuffer bufferForFile;
    //buffer, that will be filled with all needed info before compression;
    ByteBuffer generalBuffer;

    // bitset will contain encoded information in bits;
    BitSet dataInBits;
    // structure of the tree in bits;
    BitSet treeStructureInBits;


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
    public byte[] compress(byte[] inBytes) {
        bytesAndFrequencies = getBytesAndFrequencies(inBytes);
        huffmanTree = buildHuffmanTree();
        treeStructure = new String();
        dataInBits = new BitSet();
        treeStructureInBits = new BitSet();

        bufferForBytes = ByteBuffer.allocate(bytesAndFrequencies.size());
        bufferForFile = ByteBuffer.allocate(inBytes.length);
        generateCodes();

        generalBuffer = ByteBuffer.allocate(TREE_SIZE + TREE_STRUCTURE + bytesAndFrequencies.size() + bufferForFile.capacity());


        for (int i = 0; i < treeStructure.length(); i++) {

            if (treeStructure.charAt(i) == '0') {
                treeStructureInBits.set(i, false);
            } else {
                treeStructureInBits.set(i, true);
            }
        }


        // STEPS OF PREPARING OUTCOMING BYTE ARRAY
        // FIRST step: putting to the buffer length of tree in bits
        generalBuffer.putInt(treeStructure.length());
        System.out.println("length of tree " + treeStructure.length());
//        System.out.println(treeStructureInBits);

        // SECOND step: putting to the buffer tree structure in bits;
        generalBuffer.put(treeStructureInBits.toByteArray());
        System.out.println(Arrays.toString(treeStructureInBits.toByteArray()));

        encodeTheFile(inBytes);

        // THIRD step: putting to the buffer bytes, that are encoded;
        generalBuffer.put(bufferForBytes.array());

        // FORTH step: putting to the buffer encoded byte array;
        generalBuffer.put(dataInBits.toByteArray());

        int pos = generalBuffer.position();
        generalBuffer.rewind();

        byte[] result = new byte[pos];
        generalBuffer.get(result);


//    System.out.println(Arrays.toString(bufferForBytes.array()));
        System.out.println(Arrays.toString(result));

        return result;
    }

    private void encodeTheFile(byte[] inBytes) {
        int bitSetIndex = 0;
        for (byte b : inBytes) {
            String code = bytesAndCodes.get(b);
            for (int i = 0; i < code.length(); i++) {
                dataInBits.set(bitSetIndex, code.charAt(i) == '0' ? false : true);
                bitSetIndex += 1;
            }
        }
    }

    /**
     * Generating unique codes for bytes, using Huffman tree traversal;
     */
    private void generateCodes() {
        // hashmap for containing unique bytes and generated codes by traversing the Huffman tree;
        bytesAndCodes = new HashMap<>();

        // start traverse with the root of the tree
        Node startNode = (Node) huffmanTree.peekFirst();
        traverseHuffmanTree(startNode, "");

        System.out.println("bytes and codes " + bytesAndCodes);
        System.out.println("tree structure " + treeStructure);
    }

    /**
     * Traversing the Huffman tree:
     * - generating codes for bytes;
     * - writing tree structure in bits;
     * - putting bytes to interim buffer(it will help to restore the tree after decompression);
     *
     * @param node - Node, that traverse starts with;
     * @param code - String, that will contain representation of unique code for specific byte;
     */
    private void traverseHuffmanTree(Node node, String code) {
        if (!node.hasValue()) {
            treeStructure = treeStructure + '1';
            traverseHuffmanTree(node.getLeftChild(), code + '0');
            traverseHuffmanTree(node.getRightChild(), code + '1');
        } else {
            treeStructure = treeStructure + '0';
            bufferForBytes.put(node.getValue());

            bytesAndCodes.put(node.getValue(), code);
        }
    }

    /**
     * Building Huffman tree using linked list
     *
     * @return Linked List with built tree;
     */
    private LinkedList buildHuffmanTree() {
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
            huffmanTree.sort(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.getFrequency() - o2.getFrequency();
                }
            });

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
     * @param inBytes - incoming byte array;
     * @return filled in hashmap;
     */
    private HashMap<Byte, Integer> getBytesAndFrequencies(byte[] inBytes) {
        // Hashmap, that contains unique bytes(key) and their frequency;
        HashMap<Byte, Integer> codes = new HashMap<>();
        int frequency = 1;
        for (byte inByte : inBytes) {
            if (!codes.containsKey(inByte)) {
                codes.put(inByte, frequency);
            } else {
                codes.put(inByte, codes.get(inByte) + 1); // 1 - iterator of frequency;
            }
        }
        System.out.println(codes);
        return codes;
    }
}

