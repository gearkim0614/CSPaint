/*
In order to help learn course concepts, I worked on the homework with [TAs],
discussed homework topics and issues with [TAs],
and/or consulted related material that can be found at:
- https://docs.oracle.com/javafx/2/ui_controls/radio-button.htm
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import static javafx.scene.paint.Color.LIGHTGREY;

/**
 * This class uses javafx to demonstrate what mspaint does
 * Extra Features: Make Square, Dotted lines(when you move cursor fast), Clear canvas, Mystery Button
 * @author Kibeom Kim
 * @version CS1331 HW9 v1
 */
public class CSPaint extends Application {

    // Defaults

    private int shapeCount = 0;
    private Color myColor = Color.BLACK;

    @Override
    public void start(Stage primaryStage) {
        // Canvas
        Canvas myCanvas = new Canvas(650, 450);

        // RadioButtons
        final ToggleGroup group = new ToggleGroup();
        RadioButton buttonDraw = new RadioButton("Draw");
        buttonDraw.setToggleGroup(group);
        buttonDraw.setSelected(true);
        RadioButton buttonErase = new RadioButton("Erase");
        buttonErase.setToggleGroup(group);
        RadioButton buttonCircle = new RadioButton("Circle");
        buttonCircle.setToggleGroup(group);
        RadioButton buttonSquare = new RadioButton("Square");
        buttonSquare.setToggleGroup(group);
        RadioButton buttonDots = new RadioButton("Dotted line");
        buttonDots.setToggleGroup(group);

        // Textbox
        TextField text1 = new TextField();

        // Button
        Button buttonColor = new Button("Change Color");
        Button buttonClear = new Button("Clear canvas");
        Button buttonNaughty = new Button("DO NOT CLICK! ;)");

        Label welcome = new Label("Choose your tool :)");
        Label botText = new Label("(0.0, 0.0)");
        Label colorInputBox = new Label("What's your favorite color?");
        Label botShape = new Label();
        buttonClear.setTranslateY(5);
        GraphicsContext draw = myCanvas.getGraphicsContext2D();

        // When button is clicked
        buttonColor.setOnAction(e -> {
                // Try Catch
                try {
                    myColor = Color.valueOf(text1.getText());
                    // draw.setFill(Color.valueOf(text1.getText()));

                } catch (IllegalArgumentException ee) {
                    // Alert box
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Invalid Color");
                    alert.setContentText("Invalid color entered!");
                    alert.showAndWait();
                } catch (Exception ee) {
                    System.out.println("There are other exceptions to handle");
                }
            });

        buttonNaughty.setOnAction(e -> {
                Alert wow = new Alert(AlertType.CONFIRMATION);
                wow.setTitle("I FELL OFF MY CHAIR");
                wow.setHeaderText("YOU CLICKED IT..?! uwu");
                wow.setContentText("Oh you naughty");
                wow.showAndWait();
            });

        buttonClear.setOnAction(e -> {
                draw.clearRect(0, 0, 650, 450);
                shapeCount = 0;
            });

        // Mouse Moved
        myCanvas.setOnMouseMoved(e -> {
                botText.setText(String.format("(%.1f, %.1f)", e.getX(), e.getY()));
                botShape.setText("Number of Shapes: 0" + shapeCount);
            });

        // Mouse Dragged
        myCanvas.setOnMouseDragged(e -> {
                if (e.getX() >= 0 && e.getX() < 650 && e.getY() >= 0 && e.getY() < 650) {
                    botText.setText(String.format("(%.1f, %.1f)", e.getX(), e.getY()));
                }

                if (buttonDraw.isSelected()) {
                    // draw something
                    draw.lineTo(e.getX(), e.getY());
                    draw.stroke();

                } else if (buttonErase.isSelected()) {
                    // erase something
                    draw.setFill(Color.WHITESMOKE);
                    draw.fillOval(e.getX() - 10, e.getY() - 10, 20, 20); // radius 10

                } else if (buttonDots.isSelected()) {
                    // dotted lines
                    // draw.setFill(Color.BLACK);
                    draw.setFill(myColor);
                    draw.fillOval(e.getX() - 2, e.getY() - 2, 4, 4); // 2 pix in radius
                }
            });

        // Mouse Released
        myCanvas.setOnMouseReleased(e -> {
                if (buttonDraw.isSelected()) {
                    draw.closePath();
                }
            });

        // Mouse Pressed
        myCanvas.setOnMousePressed(e -> {
                if (buttonCircle.isSelected()) {
                    // make circle shape
                    draw.setFill(myColor);
                    draw.fillOval(e.getX() - 15, e.getY() - 15, 30, 30); // 15 radius
                    shapeCount = shapeCount + 1;
                    botShape.setText("Number of Shapes: 0" + shapeCount);

                } else if (buttonSquare.isSelected()) {
                    // make square shape
                    draw.setFill(myColor);
                    draw.fillRect(e.getX() - 15, e.getY() - 15, 30, 30); // same as circle
                    shapeCount = shapeCount + 1;
                    botShape.setText("Number of Shapes: 0" + shapeCount);

                } else if (buttonDraw.isSelected()) {
                    // draw something
                    draw.beginPath();
                    draw.setLineWidth(4);
                    draw.setStroke(myColor);
                    draw.setLineCap(StrokeLineCap.ROUND);
                    draw.lineTo(e.getX(), e.getY());
                    // draw.stroke();
                }
            });

        // V & H Box
        VBox topLeft = new VBox(5, welcome, buttonDraw, buttonErase, buttonCircle, buttonSquare,
                buttonDots, colorInputBox, text1, buttonColor, buttonClear, buttonNaughty);
        topLeft.setBackground(new Background(new BackgroundFill(LIGHTGREY, null, null)));

        HBox bottomText = new HBox(5, botText, botShape);
        bottomText.setBackground(new Background(new BackgroundFill(LIGHTGREY, null, null)));

        // BorderPane
        BorderPane myBorderPane = new BorderPane();
        myBorderPane.setCenter(myCanvas);
        myBorderPane.setLeft(topLeft);
        myBorderPane.setBottom(bottomText);

        // Scene
        Scene myScene = new Scene(myBorderPane);
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("CSPaint");
        primaryStage.show();
    }
}
