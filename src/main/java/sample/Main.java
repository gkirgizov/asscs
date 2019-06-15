package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.StartupFactory;
import sample.UserID;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // todo: get userID from command line args?

        Parent root = new StartupFactory().getScene(UserID.TEST);

//        String mainFormId = "fxml/mainInfoForm.fxml";
//        FXMLLoader mainFxmlLoader = new FXMLLoader(getClass().getResource(mainFormId));
//        Parent root = mainFxmlLoader.load();

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
