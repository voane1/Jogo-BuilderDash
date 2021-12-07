package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;
import pt.ipbeja.estig.po2.boulderdash.model.Diamond;
import pt.ipbeja.estig.po2.boulderdash.model.Direction;
import pt.ipbeja.estig.po2.boulderdash.model.FreeTunnel;
import pt.ipbeja.estig.po2.boulderdash.model.GameModel;
import pt.ipbeja.estig.po2.boulderdash.model.Gate;
import pt.ipbeja.estig.po2.boulderdash.model.OccupiedTunnel;
import pt.ipbeja.estig.po2.boulderdash.model.Position;
import pt.ipbeja.estig.po2.boulderdash.model.Rockford;
import pt.ipbeja.estig.po2.boulderdash.model.Wall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */

public class GameBoard extends Application implements View {

    private GameModel model = new GameModel();
    private Pane pane;
    private GameImage rockfordImage;
    private GameImage diamondImage;
    private GameImage gateImage;
    private static final Map<KeyCode, Direction> directionMap = new HashMap<>();
    private Label label;
    private AbstractPosition[][] board;


    /**
     * nspired by FifteenJavaFx
     */
    static {
        directionMap.put(KeyCode.UP, Direction.UP);
        directionMap.put(KeyCode.DOWN, Direction.DOWN);
        directionMap.put(KeyCode.LEFT, Direction.LEFT);
        directionMap.put(KeyCode.RIGHT, Direction.RIGHT);
    }
    /**
     * calls the model reset
     */
    void cleanup(){
        model.resetGame();
    }
    /**
     * restart the grid
     */
    void restart(Stage stage){
        cleanup();
        start(stage);
    }
    /**
     * inspired by FifteenJavaFx
     */
    @Override
    public void start ( Stage stage ) {
        this.createModel();
        Scene scnMain = this.createScene(stage);
        stage.setTitle("Minecraft - BoulderDash");
        stage.setScene(scnMain);
        stage.show();
    }

    /**
     * Executed on exit to stop all threads
     */
    @Override
    public void stop() {
        System.out.println("END");
        System.exit(0);
    }
    /**
     * inspired by FifteenJavaFx
     */
    private void createModel(){

        this.model = new GameModel(this);
    }

    /**
     * inspired by FifteenJavaFx
     */

    private Scene createScene(Stage stage) {
        VBox vbxMain = new VBox();
        Button button = new Button("Restart");
        button.setOnAction(e -> {
            restart(stage);

        });
        this.label = new Label(String.valueOf(GameModel.getScore()));
        Pane gridUI = this.createGridUI();
        vbxMain.getChildren().addAll(button, label,gridUI);
        Scene scene = new Scene(vbxMain);
        this.setKeyHandle(scene);
        return scene;
    }
    /**
     * captura o movimento do teclado
     */
    void setKeyHandle(Scene scnMain) {
        scnMain.setOnKeyPressed( new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                model.keyPressed(directionMap.get(event.getCode()));
            };
        });
    }

    /**
     * creates the board in the grid
     */
    private Pane createGridUI() {
        this.pane = new Pane();
        this.board = model.getBoardPosition();
        for (int line = 0; line < board.length ; line++) {
            for (int col = 0; col < board[0].length; col++) {
                Position pos = new Position(line, col);
                GameImage pi;
                if (board[line][col].equals(new Wall(line, col))) {
                    pi = new GameImage("wall", pos);
                    this.pane.getChildren().add(pi); // add to gui
                }
                else if (board[line][col].equals(new OccupiedTunnel(line, col))) {
                    pi = new GameImage("terra", pos);
                    this.pane.getChildren().add(pi); // add to gui
                }
                else if (board[line][col].equals(new FreeTunnel(line, col))) {
                    pi = new GameImage("free", pos);
                    this.pane.getChildren().add(pi); // add to gui
                }
            }
        }
        rockfordInitial();
        diamondInitial();
        gate();
        return pane;
    }

    /**
     * initialization of the Rockford
     */
    public void rockfordInitial(){
        Rockford rockfordInitial = model.createRockford();
        this.rockfordImage = new GameImage("rockford",rockfordInitial.getPos());
        this.pane.getChildren().add(rockfordImage);
    }

    /**
     * initialization of the Diamond
     */
    private void diamondInitial () {
        List<Diamond> diamondInitial = model.createDiamond();

        for (int i = 0; i < diamondInitial.size(); i++) {
            Diamond diam = diamondInitial.get(i);
            this.diamondImage = new GameImage("diamante",diam.getPos());
            this.pane.getChildren().add(diamondImage);
        }
    }

    /**
     * shows the gate at the end of the game
     */
    public void gate(){

        if(model.checkEndGame().equals(false)){
            Gate gate = model.gate();
            Position positionGate = new Position(gate.getLinha(),gate.getColuna());
            this.gateImage=new GameImage("gate", positionGate);
            this.pane.getChildren().add(gateImage);
        }
    }

    /**
     * shows the gate at the end of the game
     */
    public void updateAfterMove (){
            Position alterPos = Position.getBeginPos();
            GameImage free = new GameImage("free",alterPos);
            this.pane.getChildren().add(free);
            rockfordImage.toFront();
    }

    public static void main ( String[] args ) {
        launch(args);
    }

    /**
     * makes updates on the view after checking the player's moves
     * and whether the player has already won
     */
    @Override
    public void notifyView ( Position newPosition , Boolean gamewin) {
        Platform.runLater(() -> {
            this.rockfordImage.setPositionAndXY(newPosition);
            this.label.setText(String.valueOf(GameModel.getScore()));
            this.updateAfterMove();
            model.gameEnd();
            gate();
            if (gamewin.equals(false)){
               new Alert(Alert.AlertType.INFORMATION,"You win!").showAndWait();
            }
        });
    }
}