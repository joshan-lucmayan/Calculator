package calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        CalculatorUI ui = new CalculatorUI();
        Scene scene = new Scene(ui.getRoot(), 300, 400);

        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("calculator.css")).toExternalForm()
        );

        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }
}