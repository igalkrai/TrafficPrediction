package project;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igal Kraisler on 04/01/2017.
 */
public class CsvOutputWriter {

    private static final Object[] DATA_HEADER = {"Date", "Time", "Id", "Travel time", "Speed", "NormallyExpectedTravelTime"};
    private static final Object[] NODE_HEADER = {"Id", "Lat", "Lon"};
    private static final Object[] LINK_HEADER = {"Id", "From node", "To node", "Name", "Description"};

    public void writeDataToCsv(List<Data> dataList, String outputFile) throws IOException {

        FileWriter fileWriter = new FileWriter(outputFile);
        CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);

        csvFilePrinter.printRecord(DATA_HEADER);

        for (Data data : dataList) {
            if (data != null) {
                List<String> dataRow = new ArrayList<>();
                dataRow.add(data.getDate() != null ? data.getDate().toString() : "");
                dataRow.add(data.getTime() != null ? data.getTime().toString() : "");
                dataRow.add(data.getId() != null ? data.getId().toString() : "");
                dataRow.add(data.getTravelTime() != null ? data.getTravelTime().toString() : "");
                dataRow.add(data.getSpeed() != null ? data.getSpeed().toString() : "");
                dataRow.add(data.getNormallyExpectedTravelTime() != null ? data.getNormallyExpectedTravelTime().toString() : "");

                csvFilePrinter.printRecord(dataRow);
            }
        }
    }

    public void writeNodesToCsv(List<Node> nodes, String outputFile) throws IOException {

        FileWriter fileWriter = new FileWriter(outputFile);
        CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);

        csvFilePrinter.printRecord(NODE_HEADER);

        for (Node node : nodes) {
            List<String> nodeRow = new ArrayList<>();
            nodeRow.add(node.getId().toString());
            nodeRow.add(node.getLat().toString());
            nodeRow.add(node.getLon().toString());

            csvFilePrinter.printRecord(nodeRow);
        }
    }

    public void writeLinksToCsv(List<Link> links, String outputFile) throws IOException {

        FileWriter fileWriter = new FileWriter(outputFile);
        CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);

        csvFilePrinter.printRecord(LINK_HEADER);

        for (Link link : links) {
            List<String> linkRow = new ArrayList<>();
            linkRow.add(link.getId().toString());
            linkRow.add(link.getFromNodeId().toString());
            linkRow.add(link.getToNodeId().toString());
            linkRow.add(link.getRoadName());
            linkRow.add(link.getDescription());

            csvFilePrinter.printRecord(linkRow);
        }
    }
}
