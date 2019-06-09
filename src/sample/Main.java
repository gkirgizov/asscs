package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/uc1.fxml"));
        Parent root = fxmlLoader.load();
        // specialize type
        InfoController controller = fxmlLoader.getController();
        // provide data needed
//        controller.setUser(user_id);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
