package org.example.chatbot;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root Layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Chat Display Area
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        chatArea.setStyle("-fx-control-inner-background: rgba(0,0,0,0.91); -fx-text-fill: #ffffff; -fx-font-size: 14px;-fx-font-weight: bold;");

        // Input Field
        TextField inputField = new TextField();
        inputField.setPromptText("Type your message...");
        inputField.setStyle("-fx-background-color: #1A1A1A; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-font-weight: bold;");

        // Send Button
        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-font-weight: bold;");

        // Input Area
        HBox inputArea = new HBox(10, inputField, sendButton);
        inputArea.setPadding(new Insets(10));
        inputArea.setStyle("-fx-background-color: #1A1A1A;");

        // Add Components to Layout
        root.setCenter(chatArea);
        root.setBottom(inputArea);

        // Set custom border styling for the entire layout
        root.setStyle("-fx-border-color: #ffffff; -fx-border-width: 4; -fx-border-style: solid; -fx-background-color: #000000;");

        // Scene Setup
        Scene scene = new Scene(root, 600, 400);
        scene.setFill(javafx.scene.paint.Color.WHITE);
        primaryStage.setTitle("ChullBull ChatBot");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial Greeting
        chatArea.appendText("ChatBot: Hello! How can I help you today?\n");

        // Button Action
        sendButton.setOnAction(e -> processInput(inputField, chatArea));

        // Enter Key Action
        inputField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                processInput(inputField, chatArea);
            }
        });
    }

    private void processInput(TextField inputField, TextArea chatArea) {
        String userInput = inputField.getText().trim().toLowerCase();
        if (userInput.isEmpty()) return;

        chatArea.appendText("You: " + userInput + "\n");
        inputField.clear();

        if ("exit".equals(userInput)) {
            chatArea.appendText("ChatBot: Goodbye! Have a great day!\n");
            System.exit(0);
        }

        switch (userInput) {
            case "hello":
            case "hi":
            case "hyy":
            case "Hyy":
            case "Hello":
                chatArea.appendText("ChatBot: Hello! How are you?\n");
                break;
            case "what is your name":
                chatArea.appendText("ChatBot: My name is ChatBot everyone call me ChullBull!\n");
                break;
            case  "what is the time":
            case "Whats the time now":
            case "Can you tell me the current time?":
            case "What time is it":
            case "Time please":
                chatArea.appendText("ChatBot: " + getCurrentTime() + "\n");
                break;
            case "set budget":
                chatArea.appendText("ChatBot: Please specify the budget (e.g., 'set budget 5000').\n");
                break;
            case "ask me a trivia question":
                chatArea.appendText("ChatBot: " + askTriviaQuestion() + "\n");
                break;
            case " what is the date" :
            case "What is the Date":
            case "whatisthedate":
            case "What's today's date":
            case "What date is it":
            case "Can you tell me the date":
            case "Give me the current date and time":
                    chatArea.appendText("ChatBot:"+getCurrentDate());
                break;
            default:

                if (userInput.startsWith("set budget")) {
                    chatArea.appendText("ChatBot: " + setBudget(userInput) + "\n");
                } else if (userInput.startsWith("add expense")) {
                    chatArea.appendText("ChatBot: " + addExpense(userInput) + "\n");
                }
                else if (isArithmeticExpression(userInput)) {
                    chatArea.appendText("ChatBot: " + calculateExpression(userInput) + "\n");
                }
                else {
                    chatArea.appendText("ChatBot: I'm not sure how to respond to that.\n");
                }
        }
    }

    private String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "The current time is " + LocalDateTime.now().format(formatter);
    }

    private String setBudget(String userInput) {
        try {
            String[] parts = userInput.split(" ");
            double budget = Double.parseDouble(parts[2]);
            return "Your budget has been set to " + budget;
        } catch (Exception e) {
            return "Invalid format. Use 'set budget [amount]'.";
        }
    }

    private String addExpense(String userInput) {
        try {
            String[] parts = userInput.split(" ", 3);
            double expense = Double.parseDouble(parts[2]);
            return "Expense of " + expense + " added successfully.";
        } catch (Exception e) {
            return "Invalid format. Use 'add expense [amount]'.";
        }
    }

    private String askTriviaQuestion() {
        String[] questions = {
                "What is the capital of France?",
                "Who wrote 'To Kill a Mockingbird'?",
                "What is the tallest mountain in the world?"
        };
        return questions[(int) (Math.random() * questions.length)];
    }
    public String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Current date is: " + now.format(dateFormatter);
    }
    private boolean isArithmeticExpression(String input) {
        return input.matches(".*[\\d]+[\\+\\-\\*/][\\d]+.*");
    }

    private String calculateExpression(String expression) {
        try {
            String[] tokens = expression.split(" ");
            double num1 = Double.parseDouble(tokens[0]);
            String operator = tokens[1];
            double num2 = Double.parseDouble(tokens[2]);

            double result = 0;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        return "ChatBot: Cannot divide by zero!";
                    }
                    break;
                default:
                    return "ChatBot: Invalid operator.";
            }
            return "Result: " + result;
        } catch (Exception e) {
            return "ChatBot: Invalid mathematical expression. Please use the format 'number operator number'.";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}