package calculator;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Calculator extends Application{
    List<Button> btns = new ArrayList<>();
    TextField tf = new TextField();
    BorderPane p = new BorderPane();
    boolean startNewNumber = true;
    float firstNumber = 0;
    String op = "";
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // Display field
        tf = new TextField("0");
        tf.setEditable(false);
        tf.setFocusTraversable(false);
        tf.setPrefHeight(70);
        tf.setFont(Font.font("Arial", 26));
        tf.setAlignment(Pos.CENTER_RIGHT);
        
        // Grid for buttons (It's like layout container to get all controls buttons in it)
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(15));
        
        for (int i = 0; i <=9; i++){
            btns.add(createBtn(String.valueOf(i)));
        }
        
        Button dot = createBtn(".");
        Button mul = createBtn("*");
        Button sub = createBtn("-");
        Button add = createBtn("+");
        Button div = createBtn("/");
        Button equal = createBtn("=");
        Button clear = createBtn("c");
        
        // Row 0
        grid.add(btns.get(1), 0, 0);
        grid.add(btns.get(2), 1, 0);
        grid.add(btns.get(3), 2, 0);
        grid.add(div, 3, 0);
        
        // Row 1
        grid.add(btns.get(4), 0, 1);
        grid.add(btns.get(5), 1, 1);
        grid.add(btns.get(6), 2, 1);
        grid.add(mul, 3, 1);
        
        // Row 2
        grid.add(btns.get(7), 0, 2);
        grid.add(btns.get(8), 1, 2);
        grid.add(btns.get(9), 2, 2);
        grid.add(sub, 3, 2);
        
        // Row 3
        grid.add(btns.get(0), 0, 3);
        grid.add(dot, 1, 3);
        grid.add(clear, 2, 3);
        grid.add(add, 3, 3);
        
        // Row 4
        grid.add(equal, 0, 4);
        
        ///////////////////////////////////////////////  here we will put the events and every event have a handler event
        
        // this for when I press on number appear in tf, and if I want number from more than one digit so we above we make this (boolean startNewNumber = true;)
        for (int i=0; i<=9; i++){
            final int digit = i; // here cause lembda don't accept local variable just instance var or final local variable
            btns.get(i).setOnAction(e->{
                if(startNewNumber){
                    tf.setText(String.valueOf(digit));
                    startNewNumber = false;
                }else{
                    tf.appendText(String.valueOf(digit));
                }
            });
        }
        
        // now this for apply the operators on numbers so we need to store the first number and the operator then press the = to get the result for this we above make (float firstNumber = 0, String op = "";)
        add.setOnAction(e->{
            op = "+";
            firstNumber = Float.parseFloat(tf.getText());
            
            // when I press the oparator I put new number not append so:
            startNewNumber = true;
        });
        
        sub.setOnAction(e->{
            op = "-";
            firstNumber = Float.parseFloat(tf.getText());
            startNewNumber = true;
        });
        
        mul.setOnAction(e->{
            op = "*";
            firstNumber = Float.parseFloat(tf.getText());
            startNewNumber = true;
        });
        
        div.setOnAction(e->{
            op = "/";
            firstNumber = Float.parseFloat(tf.getText());
            startNewNumber = true;
        });
        
        clear.setOnAction(e->{
            tf.setText("0");
            clear();
        });
        
        equal.setOnAction(e->{
            float secondNumber = Float.parseFloat(tf.getText());
            if (secondNumber == 0 && op.equals("/")){
                tf.setText("error");
                clear();
                return;
            }
            
            float result = calculate(secondNumber);
            
            // this for if the numbers is int then the result is int not like 12.0
            if (result == (int)result){
                tf.setText(String.valueOf((int) result));
            }else{
                tf.setText(String.valueOf(result));
            }
            clear(); // this for start new oparator
        });
        
        dot.setOnAction(e->{
            String text = tf.getText();
            if (text.equals("error") || startNewNumber){
                tf.setText("0.");
                startNewNumber = false; // to do appeand
                return;
            }
            if (!text.contains(".")){
                tf.appendText(".");
            }
        });
        
        
        // Main container
        VBox container = new VBox(15, tf, grid);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.CENTER);
        
        p.setCenter(container);
        
        Scene scene = new Scene(p, 380, 500);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setAlwaysOnTop(true);
        
    }
    
    private Button createBtn(String text){
        Button b = new Button(text);
        b.setPrefSize(75, 60);
        b.setFont(Font.font("Arial", 18)); // font family, font size
        return b;
    }
    
    private void clear(){
        op = "";
        firstNumber = 0;
        startNewNumber = true;
    }
    
    private float calculate(float secondNumber){
        float result = secondNumber; // we put 2nd number cause if the user put the 2nd number without 1st and press = the result is 0 + 2nd = 2nd number
        switch(op){
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                result = firstNumber / secondNumber;
                break;
        }
        return result;
    }
}
