package calculator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CalculatorUI {

    private final TextField display = new TextField();
    private final Calculator logic = new Calculator();
    private final BorderPane root = new BorderPane();
    private final MenuButton historyMenu = new MenuButton("⋮");

    public CalculatorUI() {
        buildUI();
    }

    private void buildUI() {
        // DISPLAY
        display.setEditable(false);
        display.setPrefHeight(60);
        display.setAlignment(Pos.CENTER_RIGHT);

        // BUTTON GRID
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        String[][] buttons = {
                {"9", "8", "7", "÷"},
                {"6", "5", "4", "×"},
                {"3", "2", "1", "-"},
                {"C", "0", ".", "+"}
        };

        for (int r = 0; r < buttons.length; r++) {
            for (int c = 0; c < buttons[r].length; c++) {
                grid.add(createButton(buttons[r][c]), c, r);
            }
        }

        // EQUALS BUTTON
        Button equals = new Button("=");
        equals.setPrefSize(260, 50);
        equals.getStyleClass().add("equals");
        equals.setOnAction(e -> calculate());

        HBox bottom = new HBox(equals);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(10));

        // TOP BAR (DISPLAY + MENU)
        historyMenu.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: #1976d2;"
        );

        HBox topBar = new HBox(historyMenu);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(5));

        BorderPane topPane = new BorderPane();
        topPane.setCenter(display);
        topPane.setRight(topBar);

        // LAYOUT
        root.setTop(topPane);
        root.setCenter(grid);
        root.setBottom(bottom);
    }

    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setPrefSize(60, 50);

        if ("+-×÷".contains(text)) {
            btn.getStyleClass().add("operator");
        } else if ("C".equals(text)) {
            btn.getStyleClass().add("clear");
        } else {
            btn.getStyleClass().add("number"); // numbers + dot
        }

        btn.setOnAction(e -> handleInput(text));
        return btn;
    }

    private void handleInput(String value) {
        switch (value) {
            case "C" -> {
                display.clear();
                logic.clear();
            }
            case "+", "-", "×", "÷" -> {
                if (!display.getText().isEmpty()) {
                    logic.setFirstNumber(Double.parseDouble(display.getText()));
                    logic.setOperator(value);
                    display.appendText(value);
                }
            }
            default -> display.appendText(value);
        }
    }

    private void calculate() {
        if (!logic.hasOperator()) return;

        String text = display.getText();
        String operator = text.replaceAll("[0-9.]", "");
        String[] parts = text.split("[+\\-×÷]");

        if (parts.length < 2) return;

        double first = Double.parseDouble(parts[0]);
        double second = Double.parseDouble(parts[1]);

        logic.setFirstNumber(first);
        Number result = logic.calculate(second);

        String resultStr;
        if (result instanceof Integer) {
            resultStr = String.valueOf(result.intValue());
        } else {
            resultStr = String.valueOf(result.doubleValue());
        }

        String record = parts[0] + " " + operator + " " + parts[1] + " = " + resultStr;
        historyMenu.getItems().add(0, new MenuItem(record));

        display.setText(resultStr);
    }

    public Parent getRoot() {
        return root;
    }
}
