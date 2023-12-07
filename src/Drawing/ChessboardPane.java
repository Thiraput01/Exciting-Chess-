package Drawing;

import GameLogic.GameLogic;
import Main.Main;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sharedObject.RenderableHolder;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class ChessboardPane extends BorderPane {

    public ChessboardPane(){
        super();
        setPrefSize(952, 768);
        Canvas canvas = new Canvas(952, 688);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.fillRect();


    }
}