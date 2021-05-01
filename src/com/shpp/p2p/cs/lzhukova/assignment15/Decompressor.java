package com.shpp.p2p.cs.lzhukova.assignment15;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;

public class Decompressor implements Constants {

    public static final double BITS_IN_BYTE = 8.0;

    LinkedList<Byte> treeLeaves;
    BitSet treeShapeInBits;
    LinkedList<Boolean> treeShape;
    LinkedList<Boolean> messageBits;
    int position;
    long dataLengthInBits;


    public Decompressor() {
        treeShape = new LinkedList<>();
        treeLeaves = new LinkedList<>();
        messageBits = new LinkedList<>();
    }


    /**
     * This method implement unarchiving;
     *
     * @param inBytes - byte array, formed from incoming archived file;
     * @return unarchived byte array, that will be written to the file;
     */

    public byte[] decompress(byte[] inBytes) throws CharacterCodingException {
        //[0, 0, 0, 13, 115, 2, 32, 104, 105, 111, 97, 121, 112, -42, 55, -58, -104, 3]

        position = 0;
        ByteBuffer buffer = ByteBuffer.wrap(inBytes);

        // get size of the tree;
        int treeSize = buffer.getInt();
        System.out.println("treeSize " + treeSize);
        position += TREE_SIZE;

        dataLengthInBits = buffer.getLong();
        System.out.println(dataLengthInBits);
        position += DATA_LENGTH;

       getTreeShape(buffer, treeSize);
//        System.out.println("treeLeaves " + treeLeaves);

        BitSet dataInBits = BitSet.valueOf(Arrays.copyOfRange(buffer.array(), position, buffer.capacity()));
//        int dataInBitsCapacity = (int) Math.ceil((buffer.capacity() - position) * BITS_IN_BYTE);

//        System.out.println(Arrays.toString(toBooleanArray(Arrays.copyOfRange(buffer.array(), position, buffer.capacity()))));


        for (int i = 0; i < dataLengthInBits; i++) {
            messageBits.add(dataInBits.get(i));
        }


//        System.out.println(messageBits);
        Node tree = unflattenTree();
        return decode(tree, messageBits);
    }

    private byte[] decode(Node root, LinkedList<Boolean> dataInBits) throws CharacterCodingException {
        StringBuilder decodedText = new StringBuilder();
        while (!dataInBits.isEmpty()) {
//            System.out.println("data " + dataInBits);
//            System.out.println("decoded " +decodedText);
            Node x = root;
            while (!x.hasValue() && !dataInBits.isEmpty()) {
                boolean bit = dataInBits.pollFirst();
                if (bit) x = x.getRightChild();
                else x = x.getLeftChild();
            }
            if (x.hasValue()) {
                decodedText.append((char) x.getValue().byteValue());
            }
        }

        System.out.println(decodedText);
//
//        Charset charset = StandardCharsets.US_ASCII;
//        CharsetEncoder encoder = charset.newEncoder();
//
//        CharBuffer buffer = CharBuffer.wrap(decodedText);
//        ByteBuffer bytes = encoder.encode(buffer);
        return null;
    }

    private void getTreeShape(ByteBuffer buffer, int treeSize) {
        // get tree shape in the form of bit sequence: '1' - not leaf, '0' - leaf
        // dividing by 8.0 means, that we need position in bytes, but treeSize variable is represented in bits;
        treeShapeInBits = BitSet.valueOf(Arrays.copyOfRange(buffer.array(), position,
                position + (int) (Math.ceil(treeSize / BITS_IN_BYTE))));
        position += (int) (Math.ceil(treeSize / BITS_IN_BYTE));
        System.out.println("tree shape in bits " + treeShapeInBits);


        int treeLeavesAmount = 0;
        for (int i = 0; i < treeSize; i++) {

            if (!treeShapeInBits.get(i)) {
                treeLeavesAmount += 1;
            }
            treeShape.add(treeShapeInBits.get(i));
        }
//        System.out.println("Boolean linked list of tree shape " + treeShape);

        for (int i = position; i < position + treeLeavesAmount; i++) {
            treeLeaves.add(buffer.get(i));

        }

        position = position + treeLeavesAmount;
    }


    private Node unflattenTree() {
        while (!treeShape.isEmpty()) {
            boolean isLeaf = !treeShape.pollFirst();
            Node node = new Node(-1);
            if (isLeaf) {
                node.setValue(treeLeaves.pollFirst());
                return node;
            }
            node.setLeftChild(unflattenTree());
            node.setRightChild(unflattenTree());

            return node;
        }
        return null;
    }

}


