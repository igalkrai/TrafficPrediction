package project;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Igal Kraisler on 12/01/2017.
 */
public class Parser implements Callable<Void> {

    private NSISXmlParser nsisXmlParser = new NSISXmlParser();
    private CsvOutputWriter csvOutputWriter = new CsvOutputWriter();

    private int startLine;
    private String line;
    private String outputFolder;

    public Parser(String outputFolder, int startLine, String line) {
        this.outputFolder = outputFolder;
        this.startLine = startLine;
        this.line = line;
    }

    @Override
    public Void call() throws Exception {
        InputStream is = new ByteArrayInputStream(line.getBytes(Charset.defaultCharset()));

        System.out.println("read line " + startLine + " thread-" + Thread.currentThread().getId());
        List<Data> dataList = nsisXmlParser.readData(is);
        csvOutputWriter.writeDataToCsv(dataList,
                outputFolder + "data" + startLine + ".csv");

        System.out.println("saved line " + startLine);

        return null;
    }
}