package module.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductParser {
    private List<List<String>> info;

    public ProductParser(){
        this.info = new ArrayList<>();
    }

    public void parseCsv(String fileName){
        List<List<String>> info = new ArrayList<>();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try(InputStream in = loader.getResourceAsStream(fileName)){
            assert in != null;
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
                while(reader.ready()){
                    String[] words = reader.readLine().split(",");
                    ArrayList<String> line = new ArrayList<>(Arrays.asList(words));
                    for(int i = 0; i < line.size(); i++){
                        if(line.get(i).equals("")){
                            line.set(i, "ERROR");
                        }
                    }

                    if(line.size()<7){
                        line.ensureCapacity(7);
                        for(int i = line.size(); i < 7; i++ ){
                            line.add("ERROR");
                        }
                    } else if (line.size() > 7){
                        line.subList(7, line.size()).clear();
                    }
                    info.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.info = info;
    }

    public List<List<String>> getInfo() {
        return info;
    }
}
