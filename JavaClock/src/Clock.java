import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.time.LocalDateTime;
import javafx.geometry.Bounds;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;

/**
 class Clock is a clock that tells the current time (The time when the program is run)
 */
public class Clock extends Application {
    /**
     start method creates the graphical application

     @param stage Parameter stage where the graphical elements are added/displayed
     */
    public void start(Stage stage) {
        Pane root = new Pane(); //Create the pane
        root.setPrefSize(500,500);

        Ellipse clock = new Ellipse(250,250,225,225); //Create the clock
        clock.setFill(Color.TRANSPARENT);
        clock.setStroke(Color.GRAY);
        clock.setStrokeWidth(5);
        root.getChildren().add(clock);

        LocalDateTime now = LocalDateTime.now(); //Store the current time in hours, minutes and seconds
        int hours = now.getHour()%12; // getHour() returns a number between 0 – 23
        int minute = now.getMinute();
        int seconds = now.getSecond();

        int r = 140; //Create the hour handle
        int cx = 250;
        int cy = 250;
        double angle = (hours-3)*2*Math.PI/12;
        angle = angle + minute/60.0 * Math.PI/6;
        double x2 = cx + r * Math.cos(angle);
        double y2 = cy + r * Math.sin(angle);
        Line hourHandle = new Line(cx,cy,x2,y2);
        hourHandle.setStrokeWidth(6);
        root.getChildren().add(hourHandle);

        r = 180; //Create the minute handle
        angle = (minute-15)*2*Math.PI/60;
        x2 = cx + r * Math.cos(angle);
        y2 = cy + r * Math.sin(angle);
        Line minuteHandle = new Line(cx,cy,x2,y2);
        minuteHandle.setStrokeWidth(4);
        root.getChildren().add(minuteHandle);

        r = 200; //Create the second handle
        angle = (seconds-15)*2*Math.PI/60;
        x2 = cx + r * Math.cos(angle);
        y2 = cy + r * Math.sin(angle);
        Line secondHandle = new Line(cx,cy,x2,y2);
        secondHandle.setStroke(Color.RED);
        secondHandle.setStrokeWidth(2);
        root.getChildren().add(secondHandle);

        Ellipse middleCircle = new Ellipse(250,250,7,7); //Create the middle circle
        middleCircle.setFill(Color.RED);
        root.getChildren().add(middleCircle);

        int counter = 0; //Create all lines at each 5 second/minute mark
        angle = Math.PI/2;
        for (int i = 0; i < 12; i++) {
            double px1 = 250 + 215 * Math.cos(angle);
            double py1 = 250 + 215 * Math.sin(angle);
            double px2 = 250 + 185 * Math.cos(angle);
            double py2 = 250 + 185 * Math.sin(angle);
            Line shortLine = new Line(px1,py1,px2,py2);
            if (counter % 3 == 0) {
                shortLine.setStrokeWidth(5);
            }
            else {
                shortLine.setStrokeWidth(2);
            }
            root.getChildren().add(shortLine);
            counter++;
            angle = angle + Math.PI/6;
        }

        counter = 0; //Create all circles at each second/minute mark
        angle = Math.PI/2;
        for (int i = 0; i < 60; i++) {
            double px = 250 + 215 * Math.cos(angle);
            double py = 250 + 215 * Math.sin(angle);
            if (counter % 5 != 0) {
                Ellipse smallCircle = new Ellipse(px,py,2,2);
                root.getChildren().add(smallCircle);
            }
            counter++;
            angle = angle + Math.PI/30;
        }

        angle = 3 * Math.PI/2; //Create the text CPSC 1181 and center allign it
        double x = 250 + 160 * Math.cos(angle);
        double y = 250 + 160 * Math.sin(angle);
        Font font = Font.font("Arial", 18);
        Text tmp = new Text("CPSC 1181"); // create a temporary text
        tmp.setFont(font);
        Bounds bound = tmp.getBoundsInLocal();
        Rectangle box = new Rectangle(bound.getMinX(), bound.getMinY(), bound.getWidth(),
                bound.getHeight());
        Shape intersection = Shape.intersect(tmp, box);
        Bounds boundingBox = intersection.getBoundsInLocal();
        double width = boundingBox.getWidth(); // returns width of the bounding box
        //double height = boundingBox.getHeight();// returns height of the bounding box

        Scene scene = new Scene(root); //Create the scene
        stage.setScene(scene);
        stage.setTitle("Lab: ClockFX");
        stage.show();
    }

    /**
     main method

     @param args Parameter args
     */
    public static void main(String[] args){
        launch(args);
    }
}