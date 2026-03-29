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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculator2 extends Application{
    
    TextField num1Field = new TextField();
    TextField num2Field = new TextField();
    Label resultTitleLabel = new Label("Result:");
    Label resultValueLabel = new Label("");
    Label historyDisplay = new Label(""); // هنا سنعرض ما نقرأه من الملف
    String fileName = "src/calculator2Assignment1/history.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // العناوين
        Label lblNum1 = new Label("Number 1: ");
        Label lblNum2 = new Label("Number 2: ");
        Label lblHistoryTitle = new Label("History:");

        // ترتيب الصفوف
        HBox row1 = new HBox(10, lblNum1, num1Field); row1.setAlignment(Pos.CENTER);
        HBox row2 = new HBox(10, lblNum2, num2Field); row2.setAlignment(Pos.CENTER);

        // الأزرار
        Button addBtn = new Button("+"); Button subBtn = new Button("-");
        Button mulBtn = new Button("*"); Button divBtn = new Button("/");
        HBox opsRow = new HBox(10, addBtn, subBtn, mulBtn, divBtn); opsRow.setAlignment(Pos.CENTER);

        HBox resultRow = new HBox(5, resultTitleLabel, resultValueLabel);
        resultRow.setAlignment(Pos.CENTER);
        
        Button clearBtn = new Button("Clear");
        Button historyBtn = new Button("History");
        HBox actionRow = new HBox(10, clearBtn, historyBtn); actionRow.setAlignment(Pos.CENTER);

        // برمجة الأزرار
        addBtn.setOnAction(e -> calculate('+'));
        subBtn.setOnAction(e -> calculate('-'));
        mulBtn.setOnAction(e -> calculate('*'));
        divBtn.setOnAction(e -> calculate('/'));

        // زر Clear: يمسح الحقول والملف والشاشة
        clearBtn.setOnAction(e -> {
            num1Field.clear();
            num2Field.clear();
            resultValueLabel.setText("");
            historyDisplay.setText("");
            clearFile(); 
        });

        // زر History: يقرأ من الملف مباشرة ويعرض
        historyBtn.setOnAction(e -> {
            readHistoryFromFile();
        });

        VBox root = new VBox(15, row1, row2, opsRow, resultRow, actionRow, lblHistoryTitle, historyDisplay);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }

    private void calculate(char op) {
        try {
            double n1 = Double.parseDouble(num1Field.getText());
            double n2 = Double.parseDouble(num2Field.getText());
            double res = 0;
            
            if (op == '/' && n2 == 0){
                resultValueLabel.setText("Error (Div by 0)");
            }
            
            if (op == '+') res = n1 + n2;
            else if (op == '-') res = n1 - n2;
            else if (op == '*') res = n1 * n2;
            else if (op == '/') res = n1 / n2;

            resultValueLabel.setText(String.valueOf(res));
            
            // إضافة للملف مباشرة فور نجاح العملية
            appendToFile(n1 + " " + op + " " + n2 + " = " + res);

        } catch (Exception ex) {
            resultValueLabel.setText("Error");
        }
    }

    // دالة للكتابة في الملف (إضافة)
    private void appendToFile(String line) {
        try (FileWriter fw = new FileWriter(fileName, true);
            PrintWriter pw = new PrintWriter(fw)) {
            pw.println(line);
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    }

    // دالة القراءة من الملف (فكرتكِ الأساسية)
    private void readHistoryFromFile() {
        String data = "";
        File file = new File(fileName);
        if (!file.exists()) {
            historyDisplay.setText("No history yet.");
            return;
        }

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                data += reader.nextLine() + "\n";
            }
            historyDisplay.setText(data); // عرض النص المقروء
        } catch (FileNotFoundException e) {
            e.printStackTrace(); 
        }
    }

    private void clearFile() {
        try (PrintWriter pw = new PrintWriter(fileName)) {
            pw.print(""); // مسح المحتوى
        } catch (FileNotFoundException e) { 
            e.printStackTrace();
        }
    }
    
    
    
//  ///////////////////////////////////////////////////////////////////////////////////////////////////////////// try 2  
//    // تعريف العناصر كمتغيرات عامة (Instance Variables) للوصول إليها من كل الدوال
//    TextField num1Field = new TextField();
//    TextField num2Field = new TextField();
//    Label resultLabel = new Label("Result:");
//    Label historyDisplay = new Label(""); // هذا المربع الذي ستظهر فيه العمليات تحت كلمة History
//    List<String> historyList = new ArrayList<>();
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//    
//    @Override
//    public void start(Stage primaryStage) {
//    // 1. إعداد العناوين والحقول
////        Label title = new Label("JavaFX Calculator With History");
//        Label lblNum1 = new Label("Number 1: ");
//        Label lblNum2 = new Label("Number 2: ");
//        Label lblHistoryTitle = new Label("History:"); // الكلمة الثابتة
//        
//        num1Field.setPromptText("Enter first number");
//        num2Field.setPromptText("Enter second number");
//
//        // 2. ترتيب الحقول في صفوف (HBox)
//        HBox row1 = new HBox(10, lblNum1, num1Field);
//        row1.setAlignment(Pos.CENTER);
//        
//        HBox row2 = new HBox(10, lblNum2, num2Field);
//        row2.setAlignment(Pos.CENTER);
//
//        // 3. أزرار العمليات
//        Button addBtn = createBtn("+");
//        Button subBtn = createBtn("-");
//        Button mulBtn = createBtn("*");
//        Button divBtn = createBtn("/");
//        
//        HBox opsRow = new HBox(10, addBtn, subBtn, mulBtn, divBtn);
//        opsRow.setAlignment(Pos.CENTER);
//
//        // 4. أزرار Clear و History
//        Button clearBtn = createBtn("Clear");
//        Button historyBtn = createBtn("History");
//        HBox actionRow = new HBox(10, clearBtn, historyBtn);
//        actionRow.setAlignment(Pos.CENTER);
//
//        // 5. برمجة الأزرار (Events)
//        addBtn.setOnAction(e -> calculate('+'));
//        subBtn.setOnAction(e -> calculate('-'));
//        mulBtn.setOnAction(e -> calculate('*'));
//        divBtn.setOnAction(e -> calculate('/'));
//
//        // زر Clear ينظف كل شيء (الحقول، النتيجة، القائمة، الملف، والشاشة)
//        clearBtn.setOnAction(e -> {
//            num1Field.clear();
//            num2Field.clear();
//            resultLabel.setText("Result:");
//            historyDisplay.setText(""); // تنظيف العرض على الشاشة
//            historyList.clear();        // تنظيف القائمة في الذاكرة
//            clearFile();                // تنظيف الملف البرمجي
//        });
//
//        // زر History يحفظ في الملف ويعرض على الشاشة
//        historyBtn.setOnAction(e -> {
//            saveHistoryToFile();
//            displayHistoryOnScreen();
//        });
//
//        // 6. التجميع النهائي (Main Layout)
//        VBox mainLayout = new VBox(15);
//        mainLayout.setPadding(new Insets(20));
//        mainLayout.setAlignment(Pos.TOP_CENTER);
//        mainLayout.getChildren().addAll(
//            title, row1, row2, opsRow, resultLabel, actionRow, lblHistoryTitle, historyDisplay
//        );
//
//        Scene scene = new Scene(mainLayout, 400, 550);
//        primaryStage.setTitle("JavaFX Calculator");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//    
//    // دالة لإنشاء الأزرار وتنسيقها
//    private Button createBtn(String text) {
//        Button b = new Button(text);
//        b.setPrefSize(80, 40);
//        return b;
//    }
//
//    // دالة الحساب وتخزين العملية
//    private void calculate(char operator) {
//        try {
//            double n1 = Double.parseDouble(num1Field.getText());
//            double n2 = Double.parseDouble(num2Field.getText());
//            double res = 0;
//
//            switch (operator) {
//                case '+': res = n1 + n2; break;
//                case '-': res = n1 - n2; break;
//                case '*': res = n1 * n2; break;
//                case '/': 
//                    if (n2 != 0) res = n1 / n2; 
//                    else { resultLabel.setText("Result: Error"); return; }
//                    break;
//            }
//            
//            String fullOperation = n1 + " " + operator + " " + n2 + " = " + res;
//            resultLabel.setText("Result: " + res);
//            historyList.add(fullOperation); // حفظ العملية في القائمة فوراً
//
//        } catch (Exception ex) {
//            resultLabel.setText("Result: Invalid Input");
//        }
//    }
//
//    // دالة عرض التاريخ تحت كلمة History
//    private void displayHistoryOnScreen() {
//        String allOps = "";
//        for (String s : historyList) {
//            allOps += s + "\n"; // تجميع كل العمليات مع سطر جديد
//        }
//        historyDisplay.setText(allOps);
//    }
//
//    // دالة حفظ التاريخ في ملف txt
//    private void saveHistoryToFile() {
//        try (FileWriter fw = new FileWriter("history.txt", true);
//             PrintWriter pw = new PrintWriter(fw)) {
//            for (String s : historyList) {
//                pw.println(s);
//            }
//        } catch (IOException e) {
//            System.out.println("Error writing to file");
//        }
//    }
//
//    // دالة مسح محتوى الملف (عند الضغط على Clear)
//    private void clearFile() {
//        try (FileWriter fw = new FileWriter("src/calculator2Assignment1/history.txt", false)) {
//            fw.write(""); // كتابة نص فارغ لمسح الملف
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

    
    

    

    
     /////////////////////////////////////////////////////////////////////////////////////// try 1
//    TextField num1Field = new TextField();
//    TextField num2Field = new TextField();
//    Label resultLabel = new Label("Result:");
//    Label historyLabel = new Label("History:");
//    
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        num1Field.setPromptText("Enter first number");
//        num2Field.setPromptText("Enter second number");
//        styleTextField(num1Field);
//        styleTextField(num2Field);
//
//        // إنشاء العناوين (Labels)
//        Label lblNum1 = new Label("Number 1: ");
//        Label lblNum2 = new Label("Number 2: ");
//        
//        // ترتيب الحقول مع عناوينها في HBox (أفقي)
//        HBox row1 = new HBox(10, lblNum1, num1Field);
//        row1.setAlignment(Pos.CENTER);
//        HBox row2 = new HBox(10, lblNum2, num2Field);
//        row2.setAlignment(Pos.CENTER);
//
//        // أزرار العمليات الحسابية (+, -, *, /)
//        Button addBtn = createBtn("+");
//        Button subBtn = createBtn("-");
//        Button mulBtn = createBtn("*");
//        Button divBtn = createBtn("/");
//        
//        HBox opsRow = new HBox(10, addBtn, subBtn, mulBtn, divBtn);
//        opsRow.setAlignment(Pos.CENTER);
//
//        // أزرار Clear و History
//        Button clearBtn = createBtn("Clear");
//        Button historyBtn = createBtn("History");
//        HBox actionRow = new HBox(10, clearBtn, historyBtn);
//        actionRow.setAlignment(Pos.CENTER);
//
//        // برمجة الأزرار (Logic) بأسلوب الدكتورة
//        addBtn.setOnAction(e -> calculate('+'));
//        subBtn.setOnAction(e -> calculate('-'));
//        mulBtn.setOnAction(e -> calculate('*'));
//        divBtn.setOnAction(e -> calculate('/'));
//        
//        clearBtn.setOnAction(e -> {
//            num1Field.clear();
//            num2Field.clear();
//            resultLabel.setText("Result:");
//        });
//
//        // الحاوية الرئيسية (VBox) لترتيب العناصر عمودياً كما في الصورة
//        VBox mainLayout = new VBox(20);
//        mainLayout.setPadding(new Insets(30));
//        mainLayout.setAlignment(Pos.CENTER);
//        mainLayout.getChildren().addAll(
////            new Label("JavaFX Calculator With History"), // العنوان العلوي
//            row1, row2, 
//            opsRow, 
//            resultLabel, 
//            actionRow, 
//            historyLabel
//        );
//
//        Scene scene = new Scene(mainLayout, 450, 550);
//        primaryStage.setTitle("JavaFX Calculator With History");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//    }
//    
//    private Button createBtn(String text) {
//        Button b = new Button(text);
//        b.setPrefSize(90, 40); // مقاسات تناسب شكل الصورة
//        b.setStyle("-fx-background-radius: 5;"); // لجعل الحواف منحنية قليلاً كالصورة
//        return b;
//    }
//    
//    private void styleTextField(TextField tf) {
//        tf.setPrefWidth(200);
//        tf.setPrefHeight(35);
//    }
//    
//    private void calculate(char operator) {
//        try {
//            double n1 = Double.parseDouble(num1Field.getText());
//            double n2 = Double.parseDouble(num2Field.getText());
//            double res = 0;
//
//            switch (operator) {
//                case '+': res = n1 + n2; break;
//                case '-': res = n1 - n2; break;
//                case '*': res = n1 * n2; break;
//                case '/': 
//                    if (n2 != 0) res = n1 / n2;
//                    else { resultLabel.setText("Result: Error (Div by 0)"); return; }
//                    break;
//            }
//            resultLabel.setText("Result: " + res);
//        } catch (NumberFormatException ex) {
//            resultLabel.setText("Result: Please enter numbers");
//        }
//    }
//    
}
