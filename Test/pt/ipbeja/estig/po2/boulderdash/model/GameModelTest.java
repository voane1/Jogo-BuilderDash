package pt.ipbeja.estig.po2.boulderdash.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */

class GameModelTest {

    GameModel gameModel = new GameModel();

    @Test
    /**
     * 1.Movimento para posição livre.
     */
    void testeA() {
        //posição inicial---
        Rockford rockford = Rockford.getInstance("steve", new Position(1,2));
        //Rockford move
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        Position newPos = rockford.getPos();
        //posição a direita
       Position testPos = new Position(1,3);
       assertEquals(testPos, newPos);
    }
    @Test
    /**
     * 2.Tentativa de movimento para posição ocupada por parede.
     */
    void testeB(){
        //posição inicial---
        Rockford rockford = Rockford.getInstance("steve", new Position(1,2));
        //Rockford move
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT,rockford.getPos() ));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        System.out.println(rockford.getPos());
        Position newPos = rockford.getPos();
        //posição a direita
        Position testPos = new Position(1,6);//posição q tem q parar pois na posição 1,7 tem W
        assertEquals(testPos, newPos);
    }
    @Test
    /**
     * 3.Tentativa de movimento para posição ocupada por bigorna.
     */
    void testeC(){
        //posição inicial---
        Rockford rockford = Rockford.getInstance("steve", new Position(1,3));
        //Bigorna na pos ( 1,4 e 2,3)
            GameModel.addAnvil("1",new Position(1,4));
            GameModel.addAnvil("2",new Position(2,3));
        //Rockford move
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
                System.out.println(rockford.getPos());

        assertEquals(new Position(1,3),rockford.getPos());
    }
    @Test
    /**
     *4. Movimento do Rockford para uma posição com um diamante; este
     * deve desaparecer e pontuação deve aumentar
     */
    void testeD() {//
        //posição inicial---
        Rockford rockford = Rockford.getInstance("steve", new Position(1,3));
        //Score inicial
        System.out.println(GameModel.getScore());
        //Diamantes na pos ( 1,2 e 1,1)
        GameModel.addDiamond("D1",new Position(1,2));
            GameModel.addDiamond("D2",new Position(1,1));
        //Rockford move
        rockford.moveToMobile(rockford.move(Direction.LEFT,rockford.getPos() ));
        rockford.moveToMobile(rockford.move(Direction.LEFT,rockford.getPos() ));
        rockford.moveToMobile(rockford.move(Direction.LEFT, rockford.getPos()));
        //Score depois dos moves
        System.out.println(GameModel.getScore());
        assertEquals(400, GameModel.getScore());
    }
    @Test
    /**
     * 5. Movimento do Rockford para uma posição que faz um diamante cair,
     * atrás de si, até à última posição livre (túnel livre)
     */

    void testeE() {
        Rockford rockford = Rockford.getInstance("steve", new Position(1, 3));
        //Diamantes na pos (1,6)
        GameModel.addDiamond("D1", new Position(1, 4));
        GameModel.addDiamond("D2", new Position(1, 6));

        //Rockford move
        rockford.moveToMobile(rockford.move(Direction.DOWN, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));

        //Ver todos os diamantes
        List<Diamond> d = GameModel.getDiamonds();
        for (Diamond di : d) {
            di.checkFreeDown();
        }
            //Testes
            assertEquals(new Position(2, 4),GameModel.getDiamonds().get(0).getPos());
            assertEquals(new Position(2, 6),GameModel.getDiamonds().get(1).getPos());
    }
    @Test
    /**
     * 6. Movimento para o portão e que termina o jogo com sucesso
     */

    void testeF() {
        Rockford rockford = Rockford.getInstance("steve", new Position(1, 3));
        GameModel gameModel = new GameModel();
        GameModel.addDiamond("D2", new Position(1, 4));
        rockford.moveToMobile(rockford.move(Direction.LEFT, rockford.getPos()));
        System.out.println(rockford.getPos());
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        gameModel.gameEnd();
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.RIGHT, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.DOWN, rockford.getPos()));
        rockford.moveToMobile(rockford.move(Direction.DOWN, rockford.getPos()));

        rockford.moveToMobile(rockford.move(Direction.DOWN, rockford.getPos()));

        System.out.println(rockford.getPos());
        assertEquals(false, GameModel.getFlagGame());
    }
}