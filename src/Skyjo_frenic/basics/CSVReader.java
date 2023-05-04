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
     * Reads a CSV file and returns a HashMap with the keys being the first line of the CSV file and the values being a list containing the values of the respective column.
     * @param filePath the path to the CSV file
     * @return a HashMap representing the CSV file
     */
    public static HashMap<String, ArrayList<String>> readCSV (String filePath) throws IllegalArgumentException {

        if(filePath == null) throw new IllegalArgumentException("[ERROR] The path given is null!");
        if(!filePath.split("\\.")[1].equals("csv")) throw new IllegalArgumentException("[ERROR] The file must be a CSV file!");

        HashMap<String, ArrayList<String>> records = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] keys = line.split(",");

            //Initialize each field of the hashmap with an empty arraylist
            Arrays.stream(keys).forEach(k -> records.put(k, new ArrayList<>()));
            String[] values;
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                int i = 0;
                for(final var value : values){
                    records.get(keys[i]).add(value);
                    ++i;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        records.forEach((k, v) -> System.out.println(k + " : " + v));
        return records;
    }
}
