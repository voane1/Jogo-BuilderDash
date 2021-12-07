package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pt.ipbeja.estig.po2.boulderdash.model.Position;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 *
 * inspired by grid--animation
 */


public class GameImage extends ImageView {
    public final static int SIDE_SIZE = 50;
    private String imageName;
    private Position position;
    private static Pane pane;
    public GameImage ( String imageName, Position position) {
        this.setImage(imageName);
        this.setPositionAndXY(position);
    }

    public void setImage(String imageName)
    {
        this.imageName = imageName;
        String filename = "pt/ipbeja/po2/resources/" + this.imageName + ".png";
        Image img = new Image(filename);
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }
   public void setPositionAndXY(Position position) {
        this.position = position;
        this.setX(position.getCol() * SIDE_SIZE);
        this.setY(position.getLine() * SIDE_SIZE);
    }

}
