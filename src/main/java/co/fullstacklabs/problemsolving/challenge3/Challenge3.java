package co.fullstacklabs.problemsolving.challenge3;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.internal.util.Maps;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

import static java.lang.Integer.MAX_VALUE;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
public class Challenge3 {


    public static int findLessCostPath(int[][] board) {
        Board boardObject = new Board(board);

        if (boardObject.isOneByOneGrid()) {
            boardObject.dist[0][0].distance = 0;
        } else {
            boardObject.dist[0][0].distance = board[0][0];
        }

        boardObject.priorityQueue.add(boardObject.dist[0][0]);
        while (!boardObject.priorityQueue.isEmpty()) {
            boardObject.goToCell("left");
            boardObject.goToCell("right");
            boardObject.goToCell("up");
            boardObject.goToCell("down");
            boardObject.priorityQueue.remove();
        }
        return boardObject.getTotalCost();
    }




}

@Getter
@Setter
class Cell implements Comparable<Cell> {
    int x;
    int y;
    int distance;
    int value;

    Cell(int x, int y, int distance, int value) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", distance=" + distance +
                '}';
    }


    @Override
    public int compareTo(Cell other) {
        if (this.distance < other.distance) {
            return -1;
        } else if (this.distance > other.distance) {
            return 1;
        }
        return 0;
    }
}


@Getter
@Setter
class Board {

//    int[][] primitiveBoard;
    int rowQuantity;
    int columnQuantity;
    Cell[][] dist;
    PriorityQueue<Cell> priorityQueue;
    Map<String, Pair<Integer, Integer>> directionMap;

    Board(int [][] board) {
//        this.primitiveBoard = board;
        this.rowQuantity = board.length;
        this.columnQuantity = board.length > 0 ? board[0].length : 0;
        priorityQueue = new PriorityQueue<>(rowQuantity * columnQuantity);
        initializeDistance2dArray(board);
        initializeDirectionMap();
    }

    private void initializeDirectionMap() {
        directionMap = new HashMap<>();
        directionMap.put("left", new Pair<>(0, -1));
        directionMap.put("right", new Pair<>(0, 1));
        directionMap.put("up", new Pair<>(-1, 0));
        directionMap.put("down", new Pair<>(1, 0));
    }

    private void initializeDistance2dArray(int [][] board) {
        this.dist = new Cell[this.rowQuantity][this.columnQuantity];
        for (int i = 0; i < rowQuantity; i++) {
            for (int j = 0; j < columnQuantity; j++) {
                dist[i][j] = new Cell(i, j, MAX_VALUE, board[i][j]);
            }
        }
    }

    boolean isInsideBoard(int i, int j) {
        return i >= 0 && i < this.rowQuantity && j >= 0 && j < this.columnQuantity;
    }

    public int getTotalCost() {
        return dist[rowQuantity - 1][columnQuantity - 1].distance;
    }

    public boolean isLastCell(int newCurrX, int newCurrY) {
        return newCurrX == rowQuantity - 1 && newCurrY == columnQuantity - 1;
    }

    public boolean isOneByOneGrid() {
        return columnQuantity == 1 && rowQuantity == 1;
    }

    public boolean distWasAlreadyFilled(int newCurrX, int newCurrY) {
        return dist[newCurrX][newCurrY].distance != MAX_VALUE;
    }

    public int getCurrentCellDistance() {
        return dist[getCurrentCell().x][getCurrentCell().y].distance;
    }

    public Cell getCurrentCell() {
        return priorityQueue.peek() != null ? priorityQueue.peek() : null;
    }

    public void goToCell(String direction) {
        int newCurrX = this.getCurrentCell().x + directionMap.get(direction).getKey();
        int newCurrY = this.getCurrentCell().y + directionMap.get(direction).getValue();
        if (isInsideBoard(newCurrX, newCurrY)) {
            Cell nextCell = dist[newCurrX][newCurrY];
            if (nextCell.distance > this.getCurrentCellDistance() + nextCell.value) {
                if (distWasAlreadyFilled(newCurrX, newCurrY)) {
                    this.priorityQueue.remove(nextCell);
                } else if (isLastCell(newCurrX, newCurrY)) {
                    nextCell.distance = getCurrentCell().distance;
                } else {
                    nextCell.distance = getCurrentCell().distance + nextCell.value;
                    priorityQueue.add(nextCell);
                }
            }
        }
    }
}
