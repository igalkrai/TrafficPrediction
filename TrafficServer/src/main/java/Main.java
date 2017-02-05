import java.io.IOException;

/**
 * Created by Igal Kraisler on 16/01/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        TrafficRepository repository = new TrafficRepository("bolt://neo4j:123456@localhost");

//        repository.loadDataFromCsv("C://Users//user//Documents//Neo4j//default.graphdb//import//data");
        repository.loadDataFromCsv("C://Temp//data//db//import//data");

        System.out.println("Done");
    }
}
