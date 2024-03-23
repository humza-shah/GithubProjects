import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import java.util.Random;

/**
 class Lab8B provides an animation for creating an amount of circles and then "deleting" each one in a given amount of time.
 */
public class CirclesAnimation extends Application {
    private Text label1 = new Text("");

    private TextField numOfRotate;
    private TextField numOfCircles;
    private TextField duration;

    private Pane display;

    private final int WIDTH=800;
    private final int HEIGHT=600;
    private final int MIN_RADIUS = 50;
    private final int MAX_RADIUS = 100;

    @Override
    /**
     start method for javafx
     @param primaryStage the primary stage
     */
    public void start(Stage primaryStage) {

        display = new Pane();
        display.setPrefWidth(WIDTH);
        display.setPrefHeight(HEIGHT);
        Rectangle clip = new Rectangle(0,0,WIDTH,HEIGHT);
        display.setClip(clip);

        VBox leftMenu = createLeftMenu();

        BorderPane root = new BorderPane();

        root.setCenter(display);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root);
        primaryStage.setTitle("CPSC1181 Lab8B");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     private method for creating the left menu and simplifying the code in the start method
     @return Returns a vbox for the left hand side of the menu (panel)
     */
    private VBox createLeftMenu(){

        numOfCircles = new TextField("10");
        numOfCircles.setPrefColumnCount(2);
        duration = new TextField("500");
        duration.setPrefColumnCount(3);

        GridPane gridPane= new GridPane();

        gridPane.add(new Text("Number of Circles: "),0,1);
        gridPane.add(numOfCircles,1,1);

        gridPane.add(new Text("Duration(ms) : "),0,2);
        gridPane.add(duration,1,2);
        gridPane.setPadding(new Insets(25,25,5,10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button start = new Button("   Start    ");
        ButtonListener listener = new ButtonListener();
        start.setOnAction(listener);

        VBox leftMenu = new VBox(30,gridPane,start);
        leftMenu.setAlignment(Pos.TOP_CENTER);
        return leftMenu;

    }

    /**
     Defines what button clicks will do. It will create many circles and then "delete" each one sequentially in a scale animation.
     */
    private class ButtonListener implements EventHandler<ActionEvent> {

        /**
         Handles the button click event described in the class definition
         @param e The button
         */
        public void handle(ActionEvent e) {
            SequentialTransition seq = new SequentialTransition();
            Ellipse[] ellipse = new Ellipse[Integer.parseInt(numOfCircles.getText())];
            ScaleTransition[] scales = new ScaleTransition[Integer.parseInt(numOfCircles.getText())];
            for (int i = 0; i < ellipse.length; i++) {
                int randomCenterX = (int)(Math.random() * WIDTH);
                int randomCenterY = (int)(Math.random() * HEIGHT);
                int randomRadius = (int)(Math.random() * (MAX_RADIUS-MIN_RADIUS+1) + MIN_RADIUS);
                ellipse[i] = new Ellipse(randomCenterX, randomCenterY, randomRadius, randomRadius);
                ellipse[i].setFill(randomColor());
                display.getChildren().add(ellipse[i]);

                scales[i] = new ScaleTransition();
                scales[i].setDuration(Duration.millis(Integer.parseInt(duration.getText())));
                scales[i].setFromX(1);
                scales[i].setFromY(1);
                scales[i].setToX(0.001);
                scales[i].setToY(0.001);
                scales[i].setCycleCount(3);
                scales[i].setAutoReverse(true);
                scales[i].setNode(ellipse[i]);

                seq.getChildren().add(scales[i]);
            }
            seq.play();
        }
    }

    /**
     private method for creating a random colour. We can then fill the circle with this colour.
     @return Returns a randomly generated colour
     */
    private Color randomColor() {
        Random r = new Random();
        return Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }

    /**
     main method for our javafx application
     @param args string arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
