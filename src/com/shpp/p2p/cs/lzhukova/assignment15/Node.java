package com.shpp.p2p.cs.lzhukova.assignment15;

public class Node {
    private final int frequency;
    private Byte value;
    private Node leftChild;
    private Node rightChild;
    private boolean visited;

    public Node(int frequency) {
        this.frequency = frequency;
        visited = false;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
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

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public boolean getVisited() {
        return visited;
    }
}
