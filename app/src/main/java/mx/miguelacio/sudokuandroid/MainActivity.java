        package mx.miguelacio.sudokuandroid;

        import android.annotation.SuppressLint;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;

        import mx.miguelacio.sudokuandroid.models.Board;

        public class MainActivity extends AppCompatActivity implements CellGroupFragment.OnFragmentInteractionListener {

            private Board currentBoard;
            private Board startBoard;
            private TextView clickedCell;
            private int clickedGroup;
            private int clickedCellId;
            String textNumber;
            Button buttonCheck;
            String selectedNumber;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                buttonCheck =  findViewById(R.id.button_check);

                ArrayList<Board> boards = fillBoard();
                startBoard = boards.get(0);
                currentBoard = new Board();
                currentBoard.copyValues(startBoard.getGameCells());

                int cellGroupFragments[] = new int[]{R.id.cellGroupFragment, R.id.cellGroupFragment2, R.id.cellGroupFragment3, R.id.cellGroupFragment4,
                        R.id.cellGroupFragment5, R.id.cellGroupFragment6, R.id.cellGroupFragment7, R.id.cellGroupFragment8, R.id.cellGroupFragment9};
                for (int i = 1; i < 10; i++) {
                    CellGroupFragment thisCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[i-1]);
                    thisCellGroupFragment.setGroupId(i);
                }

                CellGroupFragment tempCellGroupFragment;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        int column = j / 3;
                        int row = i / 3;

                        int fragmentNumber = (row * 3) + column;
                        tempCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[fragmentNumber]);
                        int groupColumn = j % 3;
                        int groupRow = i % 3;

                        int groupPosition = (groupRow * 3) + groupColumn;
                        int currentValue = currentBoard.getValue(i, j);

                        if (currentValue != 0) {
                            tempCellGroupFragment.setValue(groupPosition, currentValue);
                        }
                    }
                }

                buttonCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int[][] gameCells = currentBoard.getGameCells();

                        boolean isFull = isboardfull(gameCells);
                        boolean isCorrect = isBoardCorrect(gameCells);


                            if (isFull){
                                if (isCorrect){
                                    Toast.makeText(MainActivity.this, "Juego completado", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Campos llenos pero incorrectos", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Falta campos de llenar", Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }

            public boolean isboardfull(int[][] gameCells){
                for (int i = 0; i < gameCells.length; i++) {
                    for (int j = 0; j < gameCells[i].length; j++) {
                        if (gameCells[i][j] == 0) {
                            return false;
                        }
                    }
                }
                return true;
            }

            public boolean isBoardCorrect(int[][] gameCells) {

                for (int i = 0; i < gameCells.length; i++) {
                    ArrayList<Integer> numbers = new ArrayList<>();
                    for (int j = 0; j < gameCells[i].length; j++) {
                        int number = gameCells[i][j];
                        if (numbers.contains(number)) {
                            return false;
                        } else {
                            numbers.add(number);
                        }
                    }
                }
                for (int i = 0; i < gameCells.length; i++) {
                    ArrayList<Integer> numbers = new ArrayList<>();
                    for (int j = 0; j < gameCells[i].length; j++) {
                        int number = gameCells[j][i];
                        if (numbers.contains(number)) {
                            return false;
                        } else {
                            numbers.add(number);
                        }
                    }
                }
                return true;
            }


            private ArrayList<Board>  fillBoard() {
                ArrayList<Board> boards = new ArrayList<>();
                int fileId = R.raw.easy;

                InputStream inputStream = getResources().openRawResource(fileId);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                try {
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        Board board = new Board();
                        for (int i = 0; i < 9; i++) {
                            String rowCells[] = line.split(" ");
                            for (int j = 0; j < 9; j++) {
                                if (rowCells[j].equals("-")) {
                                    board.setValue(i, j, 0);
                                } else {
                                    board.setValue(i, j, Integer.parseInt(rowCells[j]));
                                }
                            }
                            line = bufferedReader.readLine();
                        }
                        boards.add(board);
                        line = bufferedReader.readLine();
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e("ex", e.getMessage());
                }

                return boards;
            }

            @Override
            public void onFragmentInteraction(int groupId, int cellId, View view) {

                clickedCell = (TextView) view;
                clickedGroup = groupId;
                clickedCellId = cellId;

                final int row = ((clickedGroup-1)/3)*3 + (clickedCellId/3);
                final int column = ((clickedGroup-1)%3)*3 + ((clickedCellId)%3);




                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.layout_dialog_numbers, null);
                    dialogBuilder.setView(dialogView);
                    final TextView textViewnumber = dialogView.findViewById(R.id.text_view_numero);
                    Button button1 = dialogView.findViewById(R.id.button_1);
                    Button button2 = dialogView.findViewById(R.id.button_2);
                    Button button3 = dialogView.findViewById(R.id.button_3);
                    Button button4 = dialogView.findViewById(R.id.button_4);
                    Button button5 = dialogView.findViewById(R.id.button_5);
                    Button button6 = dialogView.findViewById(R.id.button_6);
                    Button button7 = dialogView.findViewById(R.id.button_7);
                    Button button8 = dialogView.findViewById(R.id.button_8);
                    Button button9 = dialogView.findViewById(R.id.button_9);

                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewnumber.setText("Usted seleccionó el número 1");
                            selectedNumber = "1";
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            textViewnumber.setText("Usted seleccionó el número 2");
                            selectedNumber = "2";
                        }
                    });
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewnumber.setText("Usted seleccionó el número 3");
                            selectedNumber = "3";
                        }
                    });
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewnumber.setText("Usted seleccionó el número 4");
                            selectedNumber = "4";
                        }
                    });
                    button5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewnumber.setText("Usted seleccionó el número 5");
                            selectedNumber = "5";
                        }
                    });
                    button6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewnumber.setText("Usted seleccionó el número 6");
                            selectedNumber = "6";
                        }
                    });
                    button7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewnumber.setText("Usted seleccionó el número 7");
                            selectedNumber = "7";
                        }
                    });
                    button8.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            textViewnumber.setText("Usted seleccionó el número 8");
                            selectedNumber = "8";
                        }
                    });
                    button9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textViewnumber.setText("Usted seleccionó el número 9");
                            selectedNumber = "9";
                        }
                    });

                    dialogBuilder.setTitle("Seleccione un número");

                    dialogBuilder.setPositiveButton("Colocar numero", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            textNumber = textViewnumber.getText().toString();

                            clickedCell.setText(String.valueOf(selectedNumber));
                            currentBoard.setValue(row, column, Integer.parseInt(selectedNumber));


                        }
                    });
                    dialogBuilder.setNegativeButton("Borrar numero", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            clickedCell.setText("");
                        }
                    });
                    AlertDialog b = dialogBuilder.create();
                    b.show();

            }
        }
