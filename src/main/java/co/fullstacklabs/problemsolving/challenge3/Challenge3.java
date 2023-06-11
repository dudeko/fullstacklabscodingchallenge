package co.fullstacklabs.problemsolving.challenge3;

import static java.lang.Integer.MAX_VALUE;

import java.util.PriorityQueue;

import lombok.Builder;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
public class Challenge3 {


    public static int findLessCostPath(int[][] primitiveBoard) {
        return new Board(primitiveBoard).getShortestPath();
    }
}


@Builder
class Node implements Comparable<Node> {
    int x;
    int y;
    int value;
    int dist;

    public static Node maxDist(int x, int y, int value) {
        return Node.builder().x(x).y(y).value(value).dist(MAX_VALUE).build();
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.dist, node.dist);
    }
}

class Board {
    private int[][] originalBoard;

    public Node[][] getDistanceBoard() {
        return distanceBoard;
    }

    public void setDistanceBoard(Node[][] distanceBoard) {
        this.distanceBoard = distanceBoard;
    }

    public PriorityQueue<Node> getPq() {
        return pq;
    }

    public void setPq(PriorityQueue<Node> pq) {
        this.pq = pq;
    }

    private Node[][] distanceBoard;

    private PriorityQueue<Node> pq = new PriorityQueue<>();


    Board(int[][] primitiveBoard) {
        this.originalBoard = primitiveBoard;
        this.distanceBoard = new Node[primitiveBoard.length][primitiveBoard[0].length];
        for (int xIndex = 0; xIndex < this.distanceBoard.length; xIndex++) {
            for (int yIndex = 0; yIndex < this.distanceBoard[xIndex].length; yIndex++) {
                this.distanceBoard[xIndex][yIndex] = Node.maxDist(xIndex, yIndex, this.originalBoard[xIndex][yIndex]);
            }
        }
        initializeFirstNodeDist();
        pq.add(getFirstNode());
    }

    private void initializeFirstNodeDist() {
        if (this.isOneByOne()) {
            getFirstNode().dist = 0;
        } else {
            getFirstNode().dist = this.originalBoard[0][0];
        }
    }

    private Node getFirstNode() {
        return this.distanceBoard[0][0];
    }

    public int[][] getOriginalBoard() {
        return originalBoard;
    }

    public void setOriginalBoard(int[][] originalBoard) {
        this.originalBoard = originalBoard;
    }

    public int getShortestPath() {
        while (!pq.isEmpty()) {
            goLeft();
            goUp();
            goRight();
            goDown();
            pq.remove();
        }
        return getLastNode().dist;
    }

    private boolean isOneByOne() {
        return this.distanceBoard.length == 1 && this.distanceBoard[0].length == 1;
    }

    private Node getLastNode() {
        return this.distanceBoard[this.distanceBoard.length - 1][this.distanceBoard[0].length - 1];
    }

    private void moveTo(int dx, int dy) {
        int newX = getCurrentNode().x - dx;
        int newY = getCurrentNode().y - dy;
        if (isNotOutOfBounds(newX, newY)) {
            Node nextNode = this.distanceBoard[newX][newY];
            if (nextNode.dist > getCurrentNode().dist + nextNode.value) {
                if (nextNode.dist != MAX_VALUE) {
                    pq.remove(nextNode);
                } else if (nextNode.equals(getLastNode())) {
                    nextNode.dist = getCurrentNode().dist;
                } else {
                    nextNode.dist = getCurrentNode().dist + nextNode.value;
                    pq.add(nextNode);
                }
            }
        }
    }

    private boolean isNotOutOfBounds(int newX, int newY) {
        return newX >= 0 && newX < this.distanceBoard.length &&
                newY >= 0 && newY < this.distanceBoard[0].length;
    }

    private void goLeft() {
        moveTo(0, -1);
    }

    private void goUp() {
        moveTo(-1, 0);
    }

    private void goRight() {
        moveTo(0, 1);
    }

    private void goDown() {
        moveTo(1, 0);
    }

    private Node getCurrentNode() {
        return pq.peek();
    }
}