import executor.BoltCypherExecutor;
import executor.CypherExecutor;
import org.neo4j.helpers.collection.Iterators;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static org.neo4j.helpers.collection.MapUtil.map;

//import org.neo4j.helpers.collection.Iterators;

//import static org.neo4j.helpers.collection.MapUtil.map;

/**
 * Created by Igal Kraisler on 12/01/2017.
 */
public class TrafficRepository {

    private final CypherExecutor cypher;

    public TrafficRepository(String uri) {
        cypher = createCypherExecutor(uri);
    }

    private CypherExecutor createCypherExecutor(String uri) {
        try {
            String auth = new URL(uri.replace("bolt", "http")).getUserInfo();
            if (auth != null) {
                String[] parts = auth.split(":");
                return new BoltCypherExecutor(uri, parts[0], parts[1]);
            }
            return new BoltCypherExecutor(uri);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Neo4j-ServerURL " + uri);
        }
    }

    public Map findNode(Integer id) {

        if (id == null) {
            return Collections.emptyMap();
        }


        return Iterators.singleOrNull(cypher.query(
                "MATCH (node:Node {id:{id}})" +
                        " RETURN node LIMIT 1",
                map("id", id)));
    }

    public Map findLink(Integer id) {

        if (id == null) {
            return Collections.emptyMap();
        }

        return Iterators.singleOrNull(cypher.query(
                "MATCH (link:Link {id:{id}})" +
                        " RETURN link LIMIT 1",
                map("id", id)));
    }

    /**
     * LOAD CSV FROM "file:///nodes.csv" AS line
     * CREATE (n:Node {id:toInt(line[0]), lat:toFloat(line[1]), lon:toFloat(line[2])})
     */
    public void loadDataFromCsv(String csvFolder) throws IOException {

//        cypher.query("LOAD CSV FROM \"file:///data//data1.csv\" AS line" +
//                        " CREATE (d:Data {date:line[0], time:toInt(line[1]), " +
//                        "id:toInt(line[2]), travelTime:toInt(line[3]), " +
//                        "speed:toInt(line[4]), expectedTime:toInt(line[5])})",
//                map("file","data1.csv"));
        Path path = Paths.get(csvFolder);

        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {

            Stream<Path> dirStream = Files.list(path);
            dirStream.forEach(file -> {
//                    String fileName = "file:///"+ file.toAbsolutePath().toString().replaceAll("\\\\","/");
                String fileName = "file:///data/" + file.getFileName();
//
                cypher.query("LOAD CSV FROM {file} AS line" +
                                " CREATE (d:Data {date:line[0], time:toInt(line[1]), " +
                                "id:toInt(line[2]), travelTime:toInt(line[3]), " +
                                "speed:toInt(line[4]), expectedTime:toInt(line[5])})",
                        map("file", fileName));

                System.out.println("Loaded " + fileName);
            });
        }
    }
}
