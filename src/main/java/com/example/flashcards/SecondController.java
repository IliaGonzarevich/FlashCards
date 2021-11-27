package com.example.flashcards;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Objects;
import static com.example.flashcards.FirstController.countQ;
import static com.example.flashcards.FirstController.nameF;
import static com.example.flashcards.FirstController.col;

/**
 * tretretre
 */
public class SecondController {

    @FXML
    private Label errorLbl;

    @FXML
    private Button creatFile;

    @FXML
    private Button fileButton;

    @FXML
    private TextField textField;

    @FXML
    private TextArea wrongVarQ;

    @FXML
    private TextArea wrongQ;

    @FXML
    private Label varKnow;

    @FXML
    private Label dontKnow;

    @FXML
    private Label learn;

    @FXML
    private AnchorPane resultAnchorPane;

    @FXML
    private Label lblShow2;

    @FXML
    private Label variantLbl;

    @FXML
    private VBox radioVertical;

    @FXML
    private RadioButton radio1;

    @FXML
    private RadioButton radio2;

    @FXML
    private RadioButton radio3;

    @FXML
    private RadioButton radio4;

    @FXML
    private ToggleGroup radioGroup;

    @FXML
    private AnchorPane hideBottom;

    @FXML
    private VBox hideVB;

    @FXML
    private AnchorPane hideTop;

    @FXML
    private Separator sep;

    @FXML
    private Label lblShow;

    @FXML
    private Label lblA;

    @FXML
    private Label lblY;

    @FXML
    private Label lblN;

    @FXML
    private Label lblQ;

    @FXML
    private Label labelHideA;
    @FXML
    private Label resultTitle;

    @FXML
    private ProgressBar pBar;

    ArrayList<Questions> questions = new ArrayList<>();

    ArrayList<String> varMas = new ArrayList<>();
    private int num = countQ;
    private int length = col;
    private int badStat = 0;
    private int rightStat = 0;
    private int varStatF = 0;
    private int isTina;
    private boolean isVar = false;
    int[] masN;
    SecureRandom random = new SecureRandom();

    void rand() {
        masN = new int[4];
        int j;
        int n;
        n = random.nextInt(length);
        length--;
        masN[0] = n;
        isTina = n;
        for (int i = 1; i < masN.length; i++) {
            j = 0;
            n = random.nextInt(col);
            while (j < i) {
                if (masN[j] == n) {
                    n = random.nextInt(col);
                    j = 0;
                } else {
                    j++;
                }
            }
            masN[i] = n;
        }
        putPrVAnswer(masN);
        String[] variantMas = new String[4];
        for (int ch = 0; ch < variantMas.length; ch++) {
            variantMas[ch] = questions.get(masN[ch]).getAnswer();
        }
        radio1.setText(variantMas[0]);
        radio2.setText(variantMas[1]);
        radio3.setText(variantMas[2]);
        radio4.setText(variantMas[3]);
        lblQ.setText(questions.get(isTina).getQuestion());
        lblA.setText(questions.get(isTina).getAnswer());
    }

    void putPrVAnswer(int[] massive) {
        int num1 = 0;
        int obMen;
        for (int i = 0; i < massive.length; i++) {
            int k = random.nextInt(4);
            obMen = massive[k];
            massive[k] = massive[num1];
            massive[num1] = obMen;
            num1 = k;
        }
    }

    ArrayList<Questions> readFile(){
        String str1;
        String str2;
        ArrayList<Questions> questionsFile = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nameF));
            while((str1 = reader.readLine()) != null && (str2 = reader.readLine()) != null){
                str2 = str2.replaceAll("[&]", "\n");
                questionsFile.add(new Questions(str1, str2));
            }
        } catch (Exception e){
            lblQ.setText("error file");
        }
        return questionsFile;
    }

    void peRest() {
        String x = questions.get(isTina).getQuestion();
        String y = questions.get(isTina).getAnswer();
        String x2 = questions.get(length).getQuestion();
        String y2 = questions.get(length).getAnswer();
        questions.get(length).setQuestion(x);
        questions.get(length).setAnswer(y);
        questions.get(isTina).setQuestion(x2);
        questions.get(isTina).setAnswer(y2);
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    void doLblShow(){
        lblShow.setOnMousePressed(show -> {
            sep.setVisible(true);
            lblShow.setVisible(false);
            lblA.setVisible(true);
            variantLbl.setVisible(false);
        });
    }

    void queViVod(){
        if(badStat == 0) {
            wrongQ.setText("Вы ответили верно на все вопросы!\nПоздравляем!");
        } else {
            wrongQ.setText("В процессе вы выучили следующие термины:\n");
            String pred;
            for (int i = 0; i < col; i++) {
                if (!questions.get(i).isChosen()) {
                    pred = wrongQ.getText();
                    wrongQ.setText(pred + questions.get(i).getQuestion() + "\n");
                }
            }
        }
        if(varStatF == 0) {
            wrongVarQ.setText("Вы ответили верно на все вопросы\nс использованием вариантов ответа!\nВы молодец!");
        } else {
            wrongVarQ.setText("С использованием вариантов ответа вы ответили\nневерно на следующие вопросы:\n");
            int i = 0;
            while (i < varMas.size()) {
                String predVar = wrongVarQ.getText();
                wrongVarQ.setText(predVar + varMas.get(i) + "\n");
                i = i + 2;
            }
        }
    }

    /**
     * vtnjl ngtjq
     * @param isVarR sgervd
     */
    void doLblY(boolean isVarR){
        lblY.setOnMousePressed(event -> {
            peRest();
            num--;
            rightStat++;
            String conclusion;
            if (isBetween(rightStat, 2, 4) || isBetween(rightStat, 22, 24)) {
                conclusion = " термина";
            } else if (isBetween(rightStat, 5, 20) || isBetween(rightStat, 25, 30)) {
                conclusion = " терминов";
            } else conclusion = " термин";
            learn.setText("Вы выучили " + rightStat + conclusion);

            if (num == 0) {
                resultTitle.setText("Из " + countQ + " терминов ты...");
                hideVB.setVisible(false);
                hideTop.setVisible(false);
                hideBottom.setVisible(false);
                variantLbl.setVisible(false);
                resultAnchorPane.setVisible(true);
                if(isVarR){
                    radioVertical.setVisible(false);
                    sep.setVisible(false);
                    lblShow2.setVisible(false);
                }
                queViVod();
            } else {
                rand();
                lblA.setVisible(false);
                pBar.setProgress(pBar.getProgress() + 1.0/countQ);
                sep.setVisible(false);
                lblShow.setVisible(true);
                variantLbl.setVisible(true);
                if(isVarR){
                    variantLbl.setText(" Добавить варианты ответа");
                    radioVertical.setVisible(false);
                    lblShow2.setVisible(false);
                }
            }
        });
    }
    /**
     * sjnvn
     * @param isVarR параметр
     */
    void doLblN(boolean isVarR){
        lblN.setOnMousePressed(event -> {
            if (questions.get(isTina).isChosen()) {
                questions.get(isTina).setChosen(false);
                badStat++;
                dontKnow.setText(String.valueOf(badStat));
            }
            length++;
            rand();
            sep.setVisible(false);
            lblShow.setVisible(true);
            lblA.setVisible(false);
            variantLbl.setVisible(true);
            lblShow2.setVisible(false);
            if(isVarR) {
                variantLbl.setText(" Добавить варианты ответа");//это
                radioVertical.setVisible(false);//это
            }
        });
    }
    @FXML
    void show2Pressed(){
        RadioButton selectedRadio = (RadioButton) radioGroup.getSelectedToggle();
        if (selectedRadio != null) {
            String toggleGroupValue = selectedRadio.getText();
            if (!Objects.equals(toggleGroupValue, lblA.getText()) && questions.get(isTina).isChosen()) {
                varMas.add(questions.get(isTina).getQuestion());
                varMas.add(questions.get(isTina).getAnswer());
                varStatF++;
                varKnow.setText(String.valueOf(varStatF));
            }
            sep.setVisible(true);
            lblA.setVisible(true);
            radioVertical.setVisible(false);
            lblShow2.setVisible(false);
            variantLbl.setVisible(false);
            lblY.setVisible(true);
            lblN.setVisible(true);
            selectedRadio.setSelected(false);
        }
    }
    @FXML
    void variantPressed(){
        isVar = true;
        if (radioVertical.isVisible()) {
            variantLbl.setText("  Добавить варианты ответа");
            radioVertical.setVisible(false);
            lblShow2.setVisible(false);
            lblShow.setVisible(true);
            lblA.setVisible(false);
            sep.setVisible(false);
        }
        else{
            sep.setVisible(true);
            radioVertical.setVisible(true);
            lblShow2.setVisible(true);
            lblShow.setVisible(false);
            variantLbl.setText("   Убрать варианты ответа");
        }
        doLblY(isVar);
        doLblN(isVar);
        RadioButton selectedRadio = (RadioButton) radioGroup.getSelectedToggle();
        if (selectedRadio != null) {
            selectedRadio.setSelected(false);
        }
    }
    @FXML
    void textShow(){
        errorLbl.setText("");
        if (textField.isVisible()) {
            textField.setVisible(false);
            fileButton.setText("Создать отчёт");
            creatFile.setVisible(false);
            textField.setText("");
        } else {
            fileButton.setText("Вернуться");
            textField.setVisible(true);
            creatFile.setVisible(true);
        }
    }
    @FXML
    void creatFile() throws IOException {
        FileWriter fw = null;
        String name = textField.getText();
        if (name.endsWith(".txt")) {
            try {
                fw = new FileWriter(name);
                errorLbl.setStyle("-fx-text-fill: green");
                errorLbl.setText("Файл " + name + " успешно создан!");
                if (badStat == 0) {
                    fw.write("Вами не было допущено никаких ошибок! Поздравляем!\n");
                }
                else {
                    fw.write("В процессе вы выучили следующие термины:\n");
                    for (int g = 0; g < 30; g++) {
                        if (!questions.get(g).isChosen()) {
                            fw.write("*" + questions.get(g).getQuestion() + "\n");
                            g++;
                            fw.write("\tОтвет: " + questions.get(g).getAnswer().replaceAll("[\n]", " ") + "\n");
                            g++;
                        }
                    }
                }
                int h = 0;
                if (varMas.isEmpty()) {
                    fw.write("Вы ответили верно на все вопросы с использованием вариантов ответа!");
                } else {
                    fw.write("С использованием вариантов ответа вы ответили неверно на следующие вопросы:\n");
                    while (h < varMas.size()) {
                        fw.write("*" + varMas.get(h) + "\n");
                        h++;
                        fw.write("\tОтвет: " + varMas.get(h).replaceAll("[\n]", " ") + "\n");
                        h++;
                    }
                }
            } catch (IOException e) {
                errorLbl.setText("Ошибка: " + e);
            } finally {
                 if(fw != null) fw.close();
            }
        } else {
            errorLbl.setText("Название файла не корректно!\nПроверьте наличие .txt в конце.");
        }
        textField.setText("");
    }
    @FXML
    void showWrongQ(){
        wrongQ.setVisible(true);
    }

    @FXML
    void hideWrongQ(){
       wrongQ.setVisible(false);
    }

    @FXML
    void showWrongVarQ(){
        wrongVarQ.setVisible(true);
    }

    @FXML
    void hideWrongVarQ(){
        wrongVarQ.setVisible(false);
    }

    void changeBackground(Label lbl, String clr, String bckRad, String brad, String bWth) {
        lbl.setStyle("-fx-background-color: " + clr + "; -fx-background-radius: 0 0 " + bckRad + "; -fx-border-radius: 0 0 " + brad + "; -fx-border-color: grey; -fx-border-width: 1 1 1 " + bWth + ";");
    }

    @FXML
    void lblYOnEntered() {
        changeBackground(lblY, "green", "0 20", "0 20", "1");
    }

    @FXML
    void lblYOnExited() {
        changeBackground(lblY, "white", "0 20", "0 20", "1");
    }

    @FXML
    void lblNOnEntered() {
        changeBackground(lblN, "grey", "20 0", "20 0", "0");
    }

    @FXML
    void lblNOnExited() {
        changeBackground(lblN, "white", "20 0", "20 0", "0");
    }

    @FXML
    void hideAOnEntered() {
        labelHideA.setText("Ответ: " + questions.get(isTina).getAnswer());
    }
    @FXML
    void hideAOnExited() {
        labelHideA.setText("Наведи, чтобы увидеть часть ответа");
    }

    @FXML
    void initialize() {
        String traverF = "-fx-focus-traversable: false";
        questions = readFile();
        rand();
        creatFile.setVisible(false);
        creatFile.setStyle(traverF);
        fileButton.setStyle(traverF);
        textField.setVisible(false);
        textField.setStyle(traverF);
        wrongVarQ.setVisible(false);
        wrongVarQ.setStyle(traverF);
        wrongQ.setVisible(false);//добавил
        wrongQ.setStyle(traverF);
        resultAnchorPane.setVisible(false);
        radioVertical.setVisible(false);
        lblShow2.setVisible(false);
        lblA.setVisible(false);
        sep.setVisible(false);
        doLblShow();
        doLblY(isVar);
        doLblN(isVar);
    }
}
