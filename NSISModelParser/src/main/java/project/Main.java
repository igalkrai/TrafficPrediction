package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

//        new Main().processModel();
        new Main().processData();
    }

    private void processModel() throws IOException {

        NSISXmlParser nsisXmlParser = new NSISXmlParser();
        nsisXmlParser.readModel("C:\\temp\\EN_data\\NTISModel-PredefinedLocations-2016-06-08-v4.5.xml");
        Map<Long, Node> nodeMap = nsisXmlParser.getNodeMap();
        Map<Long, Link> linkMap = nsisXmlParser.getLinkMap();

        CsvOutputWriter csvOutputWriter = new CsvOutputWriter();
        csvOutputWriter.writeNodesToCsv(new ArrayList<>(nodeMap.values()), "C:\\temp\\EN_data\\nodes.csv");
        csvOutputWriter.writeLinksToCsv(new ArrayList<>(linkMap.values()), "C:\\temp\\EN_data\\links.csv");
    }

    private void processData() throws IOException, InterruptedException {

        String fileName = "C:\\temp\\EN_data\\NTISDATD-PTD-2016-07-06-Day8.dat"; //_wrapped.xml

        long time = System.currentTimeMillis();

        try {
            ExecutorService executorService = Executors.newCachedThreadPool();

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();

            int j = 0;
            while (line != null) {
                Callable<Void> callable = new Parser("C:\\temp\\EN_data\\NTISDATD-PTD-2016-07-06-Day8\\", j, line);
                executorService.submit(callable);
                line = br.readLine();
                j++;
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(fileName);
            System.err.println(e);
        }

        System.out.println("Done creating csv. Took " + (System.currentTimeMillis() - time) / 60000 + "min");
        System.out.println("Done");
    }
}
