package com.example.double_six_game_gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.spi.AbstractResourceBundleProvider;

public class HelloController {
    int numRolls = 0;
    int rollAmount1 = 0;
    int rollAmount2 = 0;
    int pos = 0;
    int posistion = 0;
    int leadRolls = 0;
    String leadInfo = "";
    ArrayList<String> data = new ArrayList<String>();
    @FXML
    private Label welcomeText;
    @FXML
    private Text title;

    @FXML
    private Text roll1;
    @FXML
    private Text roll2;

    @FXML
    private Text rollsNum;
    @FXML
    private Button rollButton;

    @FXML
    private TextField nameField;
    @FXML
    public void initialize(){
        rollButton.setDisable(true);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void start() {
        if (!nameField.getText().equals("")) {
            rollButton.setDisable(false);
            numRolls = 0;
            rollsNum.setText("Rolls: "+numRolls);
            rollAmount2 = 0;
            rollAmount1 = 0;
            roll1.setText("" +rollAmount2);
            roll2.setText("" +rollAmount1);
        }
    }
    @FXML
    protected void roll() {
        rollAmount1 = (int)(Math.random()*6+1);
        rollAmount2 = (int)(Math.random()*6+1);
        roll1.setText("" +rollAmount2);
        roll2.setText("" +rollAmount1);
        numRolls++;
        rollsNum.setText("Rolls: "+numRolls);
        if (rollAmount1 == 6 && rollAmount2 == 6){
            won();
        }
    }

    public void won(){
        rollButton.setDisable(true);
        File leaderboardfile = new File("src/main/java/com/example/double_six_game_gui/leaderboard.txt");
        try {
            Scanner fileInput = new Scanner(leaderboardfile);
            while (fileInput.hasNextLine()) {
                data.add(fileInput.nextLine());
            }
            leaderboard();
        }
        catch (IOException e){
            System.out.println("File not found");
        }

    }
    public void leaderboard() throws IOException {
        //find posistion
        String name = nameField.getText();
        System.out.println(data);
        for (String i : data){
            leadRolls = Integer.parseInt(i.substring(i.indexOf(" ")+1));
            posistion = Integer.parseInt(i.substring(0,i.indexOf(".")));
            if(numRolls < leadRolls && pos == 0){
                System.out.println(posistion);
                pos = posistion;
            }
            System.out.println(i);
        }
        System.out.println("yes");
        if (pos == 0){
            pos = posistion +1;
        }

        //write leaderboard
        FileWriter writer = new FileWriter("src/main/java/com/example/double_six_game_gui/leaderboard.txt");
        for (String i: data){
            System.out.println(i);
            leadInfo = i.substring(i.indexOf(".")+1);
            posistion = Integer.parseInt(i.substring(0,i.indexOf(".")));
            System.out.println(leadInfo +" "+ posistion);
            if (pos == posistion){
                writer.write(pos +"."+name+" "+numRolls+"\n");
                posistion ++;
                writer.write(posistion +"."+leadInfo+"\n");
            }
            else if (pos < posistion){
                posistion ++;
                writer.write(posistion +"."+leadInfo+"\n");
            }
            else{
                writer.write(posistion +"."+leadInfo+"\n");
            }
        }
        if (pos > posistion){
            writer.write(pos +"."+name+" "+numRolls+"\n");
        }

        writer.close();


    }
}