package com.example.flashcards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * controls all actions that occur in the main (menu) window
 */
public class FirstController {

    public static String nameF = "in.txt";
    public static int countQ = 30;
    public static int col = 30;

    @FXML
    private Label colVop;

    @FXML
    private Label errorNa;

    @FXML
    private TextField pathName;

    @FXML
    private TextField num;

    @FXML
    private Label chosenF;

    boolean isCorrect = true;

    String styleText(String color){
       return "-fx-text-fill: " + color;
    }

    /**
     * checks the existence of the file
     */
    @FXML
    void check(){
        num.setText("");
        errorNa.setStyle(styleText("red"));
        if(pathName.getLength() > 0){
            String fileName = pathName.getText();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                col = 0;
                String str = reader.readLine();
                while(str != null){
                    str = reader.readLine();
                    col++;
                }
               fileEnds(fileName);
            } catch (FileNotFoundException fnf){
                errorNa.setText("Файл не найден!");
                isCorrect = false;
            } catch (IOException e){
                errorNa.setText("Не удалось определить количество вопросов!");
            }
        }
    }

    /**
     * helper so that the method is not overloaded
     * @param name is filename
     */
    void fileEnds(String name){
        if((col%2) == 0) {
            col /= 2;
            if (name.endsWith(".txt") && col > 3) {
                countQ = col;
                errorNa.setStyle(styleText("green"));
                errorNa.setText("Файл найден!");
                chosenF.setText("Выбранный файл: " + name);
                nameF = name;
                num.setPromptText("Max " + col);
                pathName.setText("");
                pathName.setPromptText(name + " выбран");
                colVop.setText("Количество вопросов " + col);
                isCorrect = true;
            } else if (name.endsWith(".txt") && col <= 3) {
                col = 30;
                errorNa.setStyle(styleText("red"));
                errorNa.setText("Файл найден, но он лишком мал!\nМинимальное количество вопросов, которое\nможет содержать сессия - 4!");
            } else {
                col = 30;
                errorNa.setText("Некорректный ввод!");
                isCorrect = false;
            }
        } else {
            errorNa.setText("Не у всех вопросов есть ответы!\nОтредактируйте файл!");
        }
    }

    /**
     * creates the second window
     * @throws Exception is an exception in case the second window can not be created
     */
    @FXML
    protected void showWindow() throws Exception {
        if(isCorrect){
            pathName.setText("");
            Second second = new Second();
            second.showWindow();
        } else errorNa.setText("Некоректные данные!");
    }

    /**
     * processes the data entered by the user and configures the first window for them
     */
    @FXML
    void setNum(){
        if(num.getLength() > 0) {
            try {
                countQ = Integer.parseInt(num.getText());
                if (countQ < 1 || countQ > col) isCorrect = false;
            } catch (NumberFormatException ne) {
                isCorrect = false;
            }
        }
        if(isCorrect) {
            num.clear();
            errorNa.setStyle(styleText("green"));
            errorNa.setText("Сессия сгенерированна! Можешь начинать!");
            colVop.setText("Количество вопросов " + countQ);
        }
        else{
            errorNa.setStyle(styleText("red"));
            errorNa.setText("Некоректные данные!\nМаксимальное количество вопросов " + col +"\n" + "Минимальное количество вопросов 4!");
        }
    }
}
