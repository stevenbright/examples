package com.alexshabanov.jfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * JavaFX application class.
 */
public final class HelloApp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello!");

        final Group group = new Group();

        final Label label = new Label();
        label.setLayoutX(10);
        label.setLayoutY(50);
        label.setText("text");

        final Button button = new Button();
        button.setLayoutX(100);
        button.setLayoutY(200);
        button.setText("Say hello");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                label.setText("Hello, cur time = " + System.currentTimeMillis());
            }
        });
        group.getChildren().addAll(Arrays.asList(button, label));

        primaryStage.setScene(new Scene(group, 320, 240));
        primaryStage.show();
    }
}
