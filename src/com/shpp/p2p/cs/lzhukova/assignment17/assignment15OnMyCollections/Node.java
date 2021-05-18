package com.shpp.p2p.cs.lzhukova.assignment17.assignment15OnMyCollections;


public class Node {
    private final int frequency;
    private Byte value;
    private Node leftChild;
    private Node rightChild;
    private boolean visited;
    private boolean hasValue;

    public Node(int frequency) {
        this.frequency = frequency;
        visited = false;
        hasValue = false;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
        hasValue = true;
    }

    public boolean hasValue() {
        return hasValue;
    }

    public int getFrequency() {
        return frequency;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
