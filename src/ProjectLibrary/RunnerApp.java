package ProjectLibrary;

import javafx.application.Application;
import javafx.stage.Stage;

public class RunnerApp extends Application {

    public void start(Stage primaryStage) {
        new LibraryApp().start(primaryStage);
    }

    public static void main(String[] args){
        launch(args);
    }
}
