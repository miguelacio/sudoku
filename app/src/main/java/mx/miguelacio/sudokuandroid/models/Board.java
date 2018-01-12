package mx.miguelacio.sudokuandroid.models;

import java.util.ArrayList;
/**
 * Created by miguelacio on 10/01/18.
 */

public class Board {

    private int[][] gameCells = new int[9][9];

    public Board() {

    }

    public void setValue(int row, int column, int value) {
        gameCells[row][column] = value;
    }

    public void copyValues(int[][] newGameCells) {
        for (int i = 0; i < newGameCells.length; i++) {
            for (int j = 0; j < newGameCells[i].length; j++) {
                gameCells[i][j] = newGameCells[i][j];
            }
        }
    }
    public int[][] getGameCells() {
        return gameCells;
    }

    public int getValue(int row, int column) {
        return gameCells[row][column];
    }


}
