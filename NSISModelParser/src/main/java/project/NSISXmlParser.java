package project;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Igal Kraisler on 21/11/2016.
 */
public class NSISXmlParser {

    private LogicalModelHandler logicalModelHandler = new LogicalModelHandler();

    public void readModel(String path) {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            saxParser.parse(path, logicalModelHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Data> readData(InputStream inputStream) {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            SAXParser saxParser = factory.newSAXParser();

            DataHandler handler = new DataHandler();

            saxParser.parse(inputStream, handler);

            return handler.getDataList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Long, Link> getLinkMap() {
        return logicalModelHandler.getLinkMap();
    }

    public Map<Long, Node> getNodeMap() {
        return logicalModelHandler.getNodeMap();
    }

    //    public List<Data> readData(String path) {
//
//        try {
//
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            factory.setValidating(false);
//            SAXParser saxParser = factory.newSAXParser();
//
//            DataHandler logicalModelHandler = new DataHandler();
//
//            saxParser.parse(path, logicalModelHandler);
//
//            return logicalModelHandler.getDataList();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
