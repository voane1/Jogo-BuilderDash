package pt.ipbeja.estig.po2.boulderdash.model;



import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static pt.ipbeja.estig.po2.boulderdash.model.ReadFile.getLEVEL1;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */
class ReadFileTest {
    AbstractPosition[][] boardPosition;
    String size[];


    @Test
    String[][] readFile ( String filename, String separetor ) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            String[][] allData = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                allData[i] = lines.get(i).split(separetor);
            }
            return allData;
        } catch (IOException e) {
            String errorMessage = "Error reading file " + filename;
            System.out.println(errorMessage + " - Exception " + e.toString());
            return new String[0][];
        }
    }

    @Test
    void testeReadFile () {
        // ReadFile read = new ReadFile()
        String ficheiro[][] = readFile("D:\\Ambiente_Trabalho\\Nova_pasta\\PO2\\Projeto\\file_teste.txt", "");
        //usar espaço para pegar os objetos móveis e sem espaço para os objetos imoveis
        System.out.println(Arrays.deepToString(ficheiro));
        int size = ficheiro.length;
        System.out.println(size);
        System.out.println(ficheiro[0][1]);


    }

    @Test
    String[] sizeWorld () {


        this.size = new String[2];
        String ficheiro[][] = readFile(getLEVEL1(), " ");
        for (int i = 0; i < 2; i++) {
            size[i] = ficheiro[0][i];
        }
        return size;

    }

    @Test
    void TestCreateBoard () {
        //int linha=0;

        String size[] = sizeWorld();

        this.boardPosition = new AbstractPosition[Integer.parseInt(size[0])][Integer.parseInt(size[1])];

        String file[][] = readFile(getLEVEL1(), "");
        //= ;
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
      /*  for (int i = 0; i < boardPosition.length; i++) {
            for (int j = 0; j < Integer.parseInt(size[1]); j++) {
                System.out.println(boardPosition[i][j].getClass());

            }
        }*/

    }

    @Test
    void TesteRockfordPosition () {
        // TestCreateBoard ();


        String file[][] = readFile(getLEVEL1(), "%");
        System.out.println(Arrays.deepToString(file));
        for (int i = 0; i < boardPosition.length; i++) {
            for (int j = 0; j < boardPosition[0].length; j++) {
      /*          switch (file[i][j]) {
                    case "J":
                        boardPosition[i][j] = Rockford.getInstance();
                        break;
                    case "D":
                        boardPosition[i][j] = new Diamont(i, j);
                        break;
                    case "L":
                        boardPosition[i][j] = new Bigorna(i, j);
                        break;
                }

               }

            }*/


                //this.boardPosition;


            }
        }
    }

   /* @Test
    void testRockford () {
        GameModel model = new GameModel();
        Rockford rockford = model.creatRockford("rockford");
        rockford.move(Direction.RIGHT, rockford.getPos() );
        rockford.move(Direction.RIGHT, rockford.getPos());
        System.out.printf(rockford.toString());




    }*/
}

