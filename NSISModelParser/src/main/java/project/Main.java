package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    
    public static final String DATA_FOLDER = "C:\\Temp\\data";
    
    public static void main(String[] args) throws IOException, InterruptedException {

//        new Main().processModel(DATA_FOLDER + "\\NTISDATD-2017-01-24-Day1\\NTISModel-PredefinedLocations-2017-01-18-v5.15.xml");
        new Main().processData(DATA_FOLDER + "\\NTISDATD-2017-01-24-Day1\\NTISDATD-PTD-2017-01-24-Day1.dat");
    }
    
    private void processModel(String modelFilePath) throws IOException {
        
        NSISXmlParser nsisXmlParser = new NSISXmlParser();
        nsisXmlParser.readModel(modelFilePath);
        Map<Long, Node> nodeMap = nsisXmlParser.getNodeMap();
        Map<Long, Link> linkMap = nsisXmlParser.getLinkMap();
        
        CsvOutputWriter csvOutputWriter = new CsvOutputWriter();
        csvOutputWriter.writeNodesToCsv(new ArrayList<>(nodeMap.values()), DATA_FOLDER + "\\nodes.csv");
        csvOutputWriter.writeLinksToCsv(new ArrayList<>(linkMap.values()), DATA_FOLDER + "\\links.csv");
    }
    
    private void processData(String dataFilePath) throws IOException, InterruptedException {
        
        long time = System.currentTimeMillis();
        
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            
            BufferedReader br = new BufferedReader(new FileReader(dataFilePath));
            String line = br.readLine();
            
            int j = 0;
            while (line != null) {
                Callable<Void> callable = new Parser(DATA_FOLDER + "\\NTISDATD-2017-01-24-Day1\\csv\\", j, line);
                executorService.submit(callable);
                line = br.readLine();
                j++;
            }
            
            br.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(dataFilePath);
            System.err.println(e);
        }
        
        System.out.println("Done creating csv. Took " + (System.currentTimeMillis() - time) / 60000 + "min");
        System.out.println("Done");
    }
}
