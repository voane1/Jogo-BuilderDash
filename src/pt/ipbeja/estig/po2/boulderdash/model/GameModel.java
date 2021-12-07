package pt.ipbeja.estig.po2.boulderdash.model;
import pt.ipbeja.estig.po2.boulderdash.gui.View;

import java.util.*;
import static pt.ipbeja.estig.po2.boulderdash.model.ReadFile.getLEVEL1;
/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */
public class GameModel {
    private static Boolean flagGame = true;
    private static int Score = 0;
    private final ReadFile readfile = new ReadFile();
    private static  AbstractPosition[][] boardPosition;
    private static final List<Anvil> anvils = new ArrayList<>();
    private static final List<Diamond> diamonds = new ArrayList<>();
    private View view;
    private Rockford rockford;
    private List<String> mobile;
    private static Gate gate = new Gate(99,99);


    private int lineWorld, columWorld;


    public GameModel(View view) {
        this.view = view;
        this.createBoard();
    }
    public GameModel () {
        this.createBoard();
    }


    public static Boolean getFlagGame() {
        return flagGame;
    }

    public static Gate getGate() {
        return gate;
    }



    public static void setFlagGame(Boolean flagGame) {
        GameModel.flagGame = flagGame;
    }

    public int getLineWorld () {
        return lineWorld;
    }

    public int getColumWorld () {
        return columWorld;
    }

    /**
     * inspired by FifteenJavaFx
     */
    public void keyPressed(Direction direction) {
        rockford.moveToMobile(rockford.move(direction,rockford.getPos()));

        notifyViews(rockford.getPos(), flagGame);
    }

    public Rockford getRock(){

        return this.rockford;
    }

    /**
     * sends to view the method notifyView updates
     */

    private void notifyViews ( Position newPosition, Boolean gamewin ) {
        this.view.notifyView(newPosition,gamewin);
    }

    /**
     * creates an object anvil and stores the created objects in a list
     */
    public static Anvil addAnvil(String name, Position freePos) {
        Anvil anvil = new Anvil(name, freePos);
        anvils.add(anvil);
        boardPosition[freePos.getLine()][freePos.getCol()] = new FreeTunnel(freePos.getLine(),freePos.getCol());
        return anvil;
    }
    /**
     * creates an object diamond and stores the created objects in a list
     */
    public static Diamond addDiamond(String name, Position freePos) {
        Diamond diamond = new Diamond(name, freePos);
        diamonds.add(diamond);
        boardPosition[freePos.getLine()][freePos.getCol()] = new FreeTunnel(freePos.getLine(),freePos.getCol());
        return diamond;
    }

    /**
     * determines the size of the level
     */
    String[] sizeWorld () {
        String[] size = new String[2];
        String[][] ficheiro = readfile.read(getLEVEL1(), " ");
        System.arraycopy(ficheiro[0], 0, size, 0, 2);
        return size;
    }
    /**
     * creates the fixed board
     */
    private void createBoard () {
        String[] size = sizeWorld();
        this.lineWorld =Integer.parseInt(size[0]);
        this.columWorld = Integer.parseInt(size[1]);
        boardPosition = new AbstractPosition[lineWorld][columWorld];
        String[][] file = readfile.read(getLEVEL1(), "");
        for (int i = 1; i < boardPosition.length + 1; i++) {
            for (int j = 0; j < boardPosition[0].length; j++) {
                switch (file[i][j]) {
                    case "W":
                        boardPosition[i - 1][j] = new Wall(i - 1, j);
                        break;
                    case "O":
                        boardPosition[i - 1][j] = new OccupiedTunnel(i - 1, j);
                        break;
                    case "L":
                        boardPosition[i - 1][j] = new FreeTunnel(i - 1, j);
                        break;
                }
            }
        }
    }

    /**
     * adds to the list all diamonds read from the txt file
     */

    public List<Diamond> createDiamond(){
        this.mobile = listPositionMobile("D");
        for (int i = 0; i < mobile.size(); i+=2) {
            String row = mobile.get(i);
            String col = mobile.get(i+1);
            Position posInitial = new Position( Integer.parseInt(row), Integer.parseInt(col));//1 2
            diamonds.add( new Diamond("Diamente", posInitial));
        }
        return diamonds;
    }

    /**
     * creates the rockford from the coordinates found in the file
     */
    public Rockford createRockford() {

        this.mobile = listPositionMobile("J");

        for (int i = 0; i < mobile.size(); i+=2) {
            String row = mobile.get(i);
            String col = mobile.get(i + 1);
            Position posInitial = new Position(Integer.parseInt(row), Integer.parseInt(col));//1 2
            this.rockford = Rockford.getInstance("rockford", posInitial);
        }
        return this.rockford;
    }

    /**
     * scrolls through the txt file and adds all movable objects to a list
     */
    public List<String> listPositionMobile(String str ) {

        this.mobile = new ArrayList<>();
        ReadFile read = new ReadFile();
        String[][] ficheiro = read.getFicheiro();

        for (int i = 0; i < ficheiro.length; i++) {
              if (ficheiro[i][0].equals(str)) {
                for (int j = 1; j < ficheiro[i].length; j++) {
                    if (ficheiro[i][j].equals("%")) {
                        break;
                    } else if (!ficheiro[i][j].equals(" ")) {
                        mobile.add(ficheiro[i][j]);
                    }
                }
            }
       }

      return mobile;

    }

    public Rockford getRockford() {
        return rockford;
    }


    /**
     * reset the GameModel
     */
    public void resetGame(){
        rockford.resetRockford();
        Score = 0;
        setFlagGame(true);
        createBoard();
        createRockford();
        createDiamond();

    }

    /**
     * Verify if all diamont get found
     */

    public Boolean checkEndGame (){
        boolean flagend = false;
        for (Diamond diamon: getDiamonds()) {
            if (diamon.getOnMap().equals(true)) {
                flagend = true;
                break;
            }
        }
        return flagend ;
    }

    public static List<Diamond> getDiamonds() {
        return diamonds;
    }
    public static AbstractPosition[][] getBoardPosition() {
        return boardPosition;
    }
    public static List<Anvil> getAnvil () {
        return anvils;
    }
    public static int getScore() {
        return Score;
    }
    public static void setScore(int score) {
        Score = score;
    }

    /**
     * inserted a free tunnel in the previous Rockford position
     */

    public static void lastPos ( Position position) {

        boardPosition[position.getLine()][position.getCol()] = new FreeTunnel(position.getLine(),position.getCol());
    }


    public void gameEnd(){
        if(checkEndGame().equals(false)){
            gate = gate();

        }
    }

    /**
     * puts all occupiedTunnel positions in a list and then always selects the last of them to create a gate
     */
    public Gate gate (){
        List<AbstractPosition> posOt = new ArrayList<>();
        for (int i = 0; i < boardPosition.length; i++) {
            for (int j = 0; j < boardPosition[0].length; j++) {
                if(boardPosition[i][j].equals(new OccupiedTunnel(i,j))){
                    posOt.add(new Gate(i,j));
                }
            }
        }
        int line = posOt.get(posOt.size()-1).getLinha();
        int colum = posOt.get(posOt.size()-1).getColuna();
        return new Gate(line,colum);
    }


}
