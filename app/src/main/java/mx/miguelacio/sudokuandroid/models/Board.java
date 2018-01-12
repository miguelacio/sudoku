package mx.miguelacio.sudokuandroid.models;

import java.util.ArrayList;
/**
 * Created by miguelacio on 10/01/18.
 */

public class Board {
    //modelo de datos Board

    private int[][] gameCells = new int[9][9];
    //Matriz 9x9 porque asi de grande es un sudoku

    public Board() {
    //constructor vacio
    }
    //En este método asignamos el valor obtenido en el lugar que queremos
    public void setValue(int row, int column, int value) {
        gameCells[row][column] = value;
    }

    //Aquí copiamos la matriz
    public void copyValues(int[][] newGameCells) {
        for (int i = 0; i < newGameCells.length; i++) {
            for (int j = 0; j < newGameCells[i].length; j++) {
                gameCells[i][j] = newGameCells[i][j];
            }
        }
    }
    //un simple get
    public int[][] getGameCells() {
        return gameCells;
    }

    //este metodo nos regresa el valor al darle la columna y fila
    public int getValue(int row, int column) {
        return gameCells[row][column];
    }


}
