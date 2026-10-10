
package main.java.com.magcorporation.kinal;
import javafx.application.Application;
import javafx.stage.Stage;
import main.java.com.magcorporation.kinal.util.SceneManager;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager manager = new SceneManager(stage);
        SceneManager.showLoginView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}