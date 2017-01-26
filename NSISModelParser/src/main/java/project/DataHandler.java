package project;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Igal Kraisler on 18/12/2016.
 */
public class DataHandler extends DefaultHandler {

    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<Data> dataList = new ArrayList<>();
    private Data currData;
    private Date time;

    private boolean isInD2LogicalModel;
    private boolean isInPayloadPublication;
    private boolean isInTimeDefault;
    private boolean isInElaboratedData;
    private boolean isInPredefinedLocationReference;
    private boolean isInAverageVehicleSpeed;
    private boolean isInSpeed;
    private boolean isInTravelTime;
    private boolean isInDuration;
    private boolean isInNormallyExpectedTravelTime;

    private int line = 0;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        line++;

        if (qName.equalsIgnoreCase("d2lm:d2LogicalModel")) {
            isInD2LogicalModel = true;
        } else if (isInD2LogicalModel && (qName.equalsIgnoreCase("d2lm:payloadPublication"))) {
            isInPayloadPublication = true;
        } else if (isInPayloadPublication && (qName.equalsIgnoreCase("d2lm:timeDefault"))) {
            isInTimeDefault = true;
        } else if (isInPayloadPublication && (qName.equalsIgnoreCase("d2lm:elaboratedData"))) {
            isInElaboratedData = true;
        } else if (isInElaboratedData && (qName.equalsIgnoreCase("d2lm:predefinedLocationReference"))) {
            String idStr = attributes.getValue("id");
            long id = Long.parseLong(idStr);
            if (currData == null || currData.getId() != id) {
//            System.out.println("id=" + Long.parseLong(id));

                if ((currData != null) && currData.isFull()) {
                    dataList.add(currData);
                }
//                System.out.println("size=" + dataList.size() + ", element no=" + line);
                currData = new Data();
                currData.setId(id);
                currData.setDate(time);
                currData.setTime(time.getTime());
            }

            isInPredefinedLocationReference = true;
        } else if (isInElaboratedData && (qName.equalsIgnoreCase("d2lm:averageVehicleSpeed"))) {
            isInAverageVehicleSpeed = true;
        } else if (isInAverageVehicleSpeed && (qName.equalsIgnoreCase("d2lm:speed"))) {
            isInSpeed = true;
        } else if (isInElaboratedData && (qName.equalsIgnoreCase("d2lm:travelTime"))) {
            isInTravelTime = true;
        } else if (isInElaboratedData && (qName.equalsIgnoreCase("d2lm:normallyExpectedTravelTime"))) {
            isInNormallyExpectedTravelTime = true;
        } else if ((isInTravelTime || isInNormallyExpectedTravelTime) && (qName.equalsIgnoreCase("d2lm:duration"))) {
            isInDuration = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (qName.equalsIgnoreCase("d2lm:d2LogicalModel")) {
            isInD2LogicalModel = false;
        } else if (isInD2LogicalModel && (qName.equalsIgnoreCase("d2lm:payloadPublication"))) {
            isInPayloadPublication = false;
        } else if (isInPayloadPublication && (qName.equalsIgnoreCase("d2lm:timeDefault"))) {
            isInTimeDefault = false;
        } else if (isInPayloadPublication && (qName.equalsIgnoreCase("d2lm:elaboratedData"))) {
            isInElaboratedData = false;
        } else if (isInElaboratedData && (qName.equalsIgnoreCase("d2lm:predefinedLocationReference"))) {
            isInPredefinedLocationReference = false;
        } else if (isInElaboratedData && (qName.equalsIgnoreCase("d2lm:averageVehicleSpeed"))) {
            isInAverageVehicleSpeed = false;
        } else if (isInAverageVehicleSpeed && (qName.equalsIgnoreCase("d2lm:speed"))) {
            isInSpeed = false;
        } else if (isInElaboratedData && (qName.equalsIgnoreCase("d2lm:travelTime"))) {
            isInTravelTime = false;
        } else if (isInElaboratedData && (qName.equalsIgnoreCase("d2lm:normallyExpectedTravelTime"))) {
            isInNormallyExpectedTravelTime = false;
        } else if ((isInTravelTime || isInNormallyExpectedTravelTime) && (qName.equalsIgnoreCase("d2lm:duration"))) {
            isInDuration = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if (isInTravelTime && isInDuration) {
            String travelTime = new String(ch, start, length);
            currData.setTravelTime(Float.parseFloat(travelTime));
//            System.out.println("travelTime=" + travelTime);
        } else if (isInNormallyExpectedTravelTime && isInDuration) {
            String normallyExpectedTime = new String(ch, start, length);
            currData.setNormallyExpectedTravelTime(Float.parseFloat(normallyExpectedTime));
//            System.out.println("normallyExpectedTime=" + normallyExpectedTime);
        } else if (isInAverageVehicleSpeed && isInSpeed) {
            String speed = new String(ch, start, length);
            currData.setSpeed(Float.parseFloat(speed));
//            System.out.println("speed=" + speed);
        } else if (isInTimeDefault) {
            String timeStr = new String(ch, start, length);
            try {
                time = parser.parse(timeStr.replace('T', ' ').substring(0, 19));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Data> getDataList() {
        return dataList;
    }
}
