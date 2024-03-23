import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

/**
 class lab8A allows the user to create many circles.
 */
public class CircleCreator extends Application {
    private Ellipse ellipse;
    private Line line;
    private Pane root;
    private static boolean check = true;

    @Override
    /**
     start method for javafx
     @param primaryStage the primary stage
     */
    public void start(Stage primaryStage) {
        root = new Pane();
        line = new Line(-10,-10,-10,-10);// create a dummy line
        line.setStroke(Color.RED);
        root.getChildren().add(line);

        root.setOnMouseClicked(new MouseClickEventHandler());
        root.setOnMouseMoved(new MouseMoveEventHandler());

        Scene scene = new Scene(root, 300, 400);
        primaryStage.setTitle("Circles");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     Defines what clicks will do. The first click will set the center of the circle while the second click provides the radius away from the center.
     */
    private class MouseClickEventHandler implements EventHandler<MouseEvent> {
        @Override
        /**
         Handles the mouse event described in the class definition
         @param e The mouse (cursor)
         */
        public void handle(MouseEvent e) {
            if (check) {
                ellipse = new Ellipse(e.getX(), e.getY(), 0, 0);
                ellipse.setFill(Color.TRANSPARENT);
                ellipse.setStroke(Color.RED);
                root.getChildren().add(ellipse);
                line.setStartX(e.getX());
                line.setEndX(e.getX());
                line.setStartY(e.getY());
                line.setEndY(e.getY());
                line.setVisible(true);
                check = false;
            }
            else {
                ellipse.setStroke(Color.BLUE);
                line.setVisible(false);
                check = true;
            }
        }
    }

    /**
     Defines what moving the mouse will do. Only after the first click, moving the mouse will change the radius of the circle to the new mouse location.
     */
    private class MouseMoveEventHandler implements EventHandler<MouseEvent> {
        @Override
        /**
         Handles the mouse event described in the class definition
         @param e The mouse (cursor)
         */
        public void handle(MouseEvent e) {
            if (check == false) {
                //x and y radius of ellipse need to equal the length of the line:
                double a = line.getEndX() - line.getStartX();
                double b = line.getEndY() - line.getStartY();
                double lengthOfLine = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
                ellipse.setRadiusX(lengthOfLine);
                ellipse.setRadiusY(lengthOfLine);
                line.setEndX(e.getX());
                line.setEndY(e.getY());
            }
        }
    }

    /**
     main method for our javafx application
     @param args string arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}