package calculator2Assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class Calculator2 extends Application{
    
    TextField textFiledNum1 = new TextField();
    TextField textFiledNum2 = new TextField();
    Label resultTitleLabel = new Label("Result:");
    Label resultValueLabel = new Label("");
    Label historyDisplayLabel = new Label("");
    String filePath = "src/calculator2Assignment1/history.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        Label labelNum1 = new Label("Number 1: ");
        Label labelNum2 = new Label("Number 2: ");
        Label labelTitleHistory = new Label("History:");
        
        textFiledNum1.setPromptText("Enter first number");
        textFiledNum2.setPromptText("Enter second number");

        HBox hboxNum1 = new HBox(10, labelNum1, textFiledNum1); 
        hboxNum1.setAlignment(Pos.CENTER);
        HBox hboxNum2 = new HBox(10, labelNum2, textFiledNum2); 
        hboxNum2.setAlignment(Pos.CENTER);

        Button addBtn = new Button("+"); 
        addBtn.setPrefWidth(70);
        Button subBtn = new Button("-");
        subBtn.setPrefWidth(70);
        Button mulBtn = new Button("*"); 
        mulBtn.setPrefWidth(70);
        Button divBtn = new Button("/");
        divBtn.setPrefWidth(70);
        HBox hboxOperators = new HBox(15, addBtn, subBtn, mulBtn, divBtn); 
        hboxOperators.setAlignment(Pos.CENTER);

        HBox hboxResult = new HBox(5, resultTitleLabel, resultValueLabel);
        hboxResult.setAlignment(Pos.CENTER);
        
        Button clearBtn = new Button("Clear");
        clearBtn.setPrefWidth(100);
        Button historyBtn = new Button("History");
        historyBtn.setPrefWidth(100);
        HBox hboxClearHistory = new HBox(15, clearBtn, historyBtn); 
        hboxClearHistory.setAlignment(Pos.CENTER);

        // ************ actions
        
        addBtn.setOnAction(e -> calculate('+'));
        subBtn.setOnAction(e -> calculate('-'));
        mulBtn.setOnAction(e -> calculate('*'));
        divBtn.setOnAction(e -> calculate('/'));

        clearBtn.setOnAction(e -> {
            textFiledNum1.clear();
            textFiledNum2.clear();
            resultValueLabel.setText("");
            historyDisplayLabel.setText("");
            clearFile(); 
        });

        historyBtn.setOnAction(e -> {
            readHistoryFromFile();
        });
        
        // ************ end actions

        VBox root = new VBox(15, hboxNum1, hboxNum2, hboxOperators, hboxResult, hboxClearHistory, labelTitleHistory, historyDisplayLabel);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(root, 530, 500));
        primaryStage.setTitle("JavaFX Calculator With History");
        primaryStage.show();
    }

    private void calculate(char operation) {
        try {
            double num1 = Double.parseDouble(textFiledNum1.getText());
            double num2 = Double.parseDouble(textFiledNum2.getText());
            
            double resultOfOperation = 0;
            switch (operation) {
                case '+':
                    resultOfOperation = num1 + num2;
                    toAvoidHistory(num1, operation, num2, resultOfOperation);
                    break;
                case '-':
                    resultOfOperation = num1 - num2;
                    toAvoidHistory(num1, operation, num2, resultOfOperation);
                    break;
                case '*':
                    resultOfOperation = num1 * num2;
                    toAvoidHistory(num1, operation, num2, resultOfOperation);
                    break;
                case '/':
                    if (num2 != 0){
                        resultOfOperation = num1 / num2;
                        toAvoidHistory(num1, operation, num2, resultOfOperation);
                    }else{
                        resultValueLabel.setText("Cannot Divide by 0");
                        return;
                    }
                    break;
            }

        } catch (Exception ex) {
            resultValueLabel.setText("Error");
        }
    }

    // here we use the fw with true to add text at end of file it's ok
    private void appendToFile(String line) {
        try (
            FileWriter fw = new FileWriter(filePath, true);
            PrintWriter pw = new PrintWriter(fw)) {
            pw.println(line);
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    }

    private void readHistoryFromFile() {
        String data = "";
        File file = new File(filePath);
        if (!file.exists()) {
            historyDisplayLabel.setText("No history yet.");
            return;
        }
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                data += reader.nextLine() + "\n";
            }
            historyDisplayLabel.setText(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace(); 
        }
    }

    // we use here printWriter without FileWriter because the pw its use to open the file and write form zero, so in this method he delete all old lines and set ""
    private void clearFile() {
        try (PrintWriter pw = new PrintWriter(filePath)) {
            pw.print("");
        } catch (FileNotFoundException e) { 
            e.printStackTrace();
        }
    }
    
    private void toAvoidHistory(double num1, char opr, double num2, double finalResult){
        resultValueLabel.setText((String.valueOf(finalResult)));
        appendToFile(num1 + " " + opr + " " + num2 + " = " + finalResult);
    }
    
}