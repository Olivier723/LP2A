package Skyjo_frenic.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 */
public class CSVReader {

    /**
     * Reads the content of the CSV file at the given path and returns it as an list containing the data of each card
     * @param filePath the path to the CSV file
     * @throws IllegalArgumentException if the path is null or the file is not a CSV file
     * @return a HashMap representing the CSV file
     */
    public static ArrayList<ArrayList<String>> readCSV (String filePath) throws IllegalArgumentException {

        if(filePath == null) throw new IllegalArgumentException("[ERROR] The path given is null!");
        if(!filePath.split("\\.")[1].equals("csv")) throw new IllegalArgumentException("[ERROR] The file must be a CSV file!");

        ArrayList<ArrayList<String>> cardDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                cardDataList.add(new ArrayList<>(Arrays.asList(data)));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return cardDataList;
    }

}
