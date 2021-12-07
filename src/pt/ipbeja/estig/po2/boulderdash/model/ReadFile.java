package pt.ipbeja.estig.po2.boulderdash.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */
public class ReadFile {
    private final static String LEVEL1="src/pt/ipbeja/po2/level/1.txt";
    private final static String LEVEL2="src/pt/ipbeja/po2/level/2.txt";

    String[][] ficheiro;

    /**
     *class reads the txt files of the game levels
     */
    public ReadFile () {

    }

    public String[][] read ( String filename, String separetor) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            String[][] allData = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                allData[i] = lines.get(i).split(separetor);
            }
            return allData;
        } catch (IOException e) {
            String errorMessage = "Error reading file " + filename;
             System.out.println(errorMessage + " - Exception " + e.toString())  ;
            return new String[0][];
        }
    }

    public static String getLEVEL1 () {
        return LEVEL1;
    }

    public String[][] getFicheiro(){
        this.ficheiro = this.read(LEVEL1, " ");
        return ficheiro;
    }


    }









