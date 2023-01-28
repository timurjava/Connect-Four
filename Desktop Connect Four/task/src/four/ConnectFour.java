package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ConnectFour extends JFrame implements ActionListener {
    static boolean  state = true;
    boolean winner = false;
    static ArrayList<JButton> winnerList = new ArrayList<>();
    static final Color defaultBackground = Color.YELLOW;
    static final Color winColor = Color.CYAN;
    static final int size = 4;
    static ArrayList<ArrayList<JButton>> buttonCollection = new ArrayList<>();
    {
        for (int i = 0; i < 6; i++) {
            buttonCollection.add(new ArrayList<>());
        }
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!winner){
            JButton button = (JButton) actionEvent.getSource();
            char letter = button.getName().charAt(6);

            for (int i = 0; i < buttonCollection.size(); i++) {
                for (int j = 0; j < buttonCollection.get(i).size(); j++) {
                    if (letter == buttonCollection.get(i).get(j).getName().charAt(6) && buttonCollection.get(i).get(j).getText().equals(" ")) {
                        button = buttonCollection.get(i).get(j);
                    }

                }
            }
            if (button.getText().equals(" ")) {
                if (state) {
                    button.setText("X");
                    state = false;
                } else {
                    button.setText("O");
                    state = true;
                }
            }
            if(WinnerCheck()) {
                for (JButton buttons: winnerList) {
                    buttons.setBackground(winColor);
                }
                winner = true;
            }
        }
    }

    public ConnectFour() {
        JPanel resetPanel = new JPanel();
        JPanel gridPanel = new JPanel();

        ResetButtonLayout(resetPanel);
        GamePanelLayout(gridPanel);
        setLayout(new BorderLayout());
        add(gridPanel, BorderLayout.CENTER);
        add(resetPanel,BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setTitle("Connect Four");
        setVisible(true);
    }

    private void GamePanelLayout(JPanel jPanel) {
        ArrayList<Character> letters = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            letters.add((char) (65 + i));
        }

        jPanel.setLayout( new GridLayout(6, 7, 0, 0));

        for (int i = 0, j = 0; i < 7 && j < 6; i++) {
            JButton button = new JButton();
            button.setName("Button" + (char) (65 + i) + (6 - j));
            button.setText(" ");
            button.addActionListener(this);
            button.setBackground(defaultBackground);
            buttonCollection.get(j).add(button);
            jPanel.add(button);
            if (i == 6) {
                j++;
                i = - 1;
            }
        }
        
    }
     private void ResetButtonLayout(JPanel jPanel) {
         JButton buttonReset = new JButton("Reset");
         buttonReset.setName("ButtonReset");
         buttonReset.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent actionEvent) {
                 for (int i = 0; i < buttonCollection.size(); i++) {
                     for (int j = 0; j < buttonCollection.get(i).size(); j++) {
                         buttonCollection.get(i).get(j).setText(" ");
                         buttonCollection.get(i).get(j).setBackground(defaultBackground);
                         winnerList = new ArrayList<>();
                         winner = false;
                         state = true;
                     }
                 }
             }
         });
         jPanel.add(buttonReset);
         jPanel.setLayout(new FlowLayout());
     }
    private boolean WinnerCheck() {
        ArrayList<Character> list = new ArrayList<>(Arrays.asList('X', 'O'));
        for (Character letter : list) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if(checkDiagonal(letter, i, j) || checkLanes(letter, i, j)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean checkDiagonal(char symb, int offsetY, int offsetX) {
        boolean toright = true;
        boolean toleft = true;
        // установили логическое значение 1
        for (int i = 0; i < size; i++) { // Блок от 0 до 3
            toright = toright & (buttonCollection.get(i + offsetY).get(i + offsetX).getText().charAt(0) == symb);
            toleft = toleft & (buttonCollection.get(i + offsetY).get(size - i - 1 + offsetX).getText().charAt(0) == symb);
        }

        // Дальше мы вернули (true), если во всех клетках нам встретились символы symb
        if (toright) {
            for (int i = 0; i < size; i++) {
                winnerList.add(buttonCollection.get(i + offsetY).get(i + offsetX));
            }
            return true;
        }
        if (toleft) {
            for (int i = 0; i < size; i++) {
                winnerList.add(buttonCollection.get(i + offsetY).get(size - i - 1 + offsetX));
            }
            return true;
        }

        // или вернули (false), если встретился хоть один символ, отличный от symb
        return false;
    }
    private boolean checkLanes(char symb, int offsetY, int offsetX) {
        boolean cols, rows;
        for (int i = 0; i < size; i++) {
            cols = true;
            rows = true;
            for (int j = 0; j < size; j++) {
                cols &= cols & (buttonCollection.get(j + offsetY).get(i + offsetX).getText().charAt(0) == symb);
                rows &= rows & (buttonCollection.get(i + offsetY).get(j + offsetX).getText().charAt(0) == symb);
            }
            if (cols) {
                for (int j = 0; j < size; i++) {
                    winnerList.add(buttonCollection.get(j + offsetY).get(i + offsetX));
                }
                return true;
            }
            if (rows) {
                for (int j = 0; j < size; j++) {
                    winnerList.add(buttonCollection.get(i + offsetY).get(j + offsetX));
                }
                return true;
            }
        }

        return false;
    }
}