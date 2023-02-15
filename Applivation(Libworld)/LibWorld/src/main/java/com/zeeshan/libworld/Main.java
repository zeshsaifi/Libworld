package com.zeeshan.libworld;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Application {
    @Override
    public void start(Stage stage){

        Path p=Path.of("Data\\file.information.user");
        String u= null;
        try {
            u = Files.readString(p);
            if(u==""){
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main_fxml.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setResizable(false);
                stage.getIcons().add(new Image("file:Data\\file.information.view"));
                scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
                stage.setTitle("Lib World");
                stage.setScene(scene);
                stage.show();
            }
            else{
                Stage s=new Stage();
                s.getIcons().add(new Image("file:Data\\file.information.view"));
                Parent root = FXMLLoader.load(getClass().getResource("User_panel_fxml.fxml"));
                Scene scene=new Scene(root);
                s.setMinHeight(600);
                s.setMinWidth(1000);
                s.setScene(scene);
                s.setTitle("Libworld");
                s.show();
            }
        }
        catch (IOException e) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main_fxml.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            stage.setResizable(false);
            stage.getIcons().add(new Image("file:Data\\file.information.view"));
            scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
            stage.setTitle("Lib World");
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}