package project;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Igal Kraisler on 21/11/2016.
 */
public class LogicalModelHandler extends DefaultHandler {

    private Link currLink;
    private Map<Long, Link> linkMap = new HashMap<>();

    private boolean inLinksPredefinedLocationContainer = false;
    private boolean inPredefinedLocation = false;
    private boolean inLinearWithinLinearElement = false;
    private boolean inFromPoint = false;
    private boolean inFromReferent = false;
    private boolean inReferentIdentifier = false;
    private boolean inRoadName = false;
    private boolean inPredefinedLocationName = false;
    private boolean inDescription = false;

    private boolean inToPoint = false;
    private Node currNode;
    private Map<Long, Node> nodeMap = new HashMap<>();
    private boolean inNodesPredefinedLocationContainer = false;
    private boolean inNodeLat = false;
    private boolean inNodeLon = false;


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

//        System.out.println("Start Element :" + qName);

        if (qName.equalsIgnoreCase("d2lm:predefinedLocationContainer")) {
            String type = attributes.getValue("xsi:type");
            String containerId = attributes.getValue("id");

            if (type != null && containerId != null) {
                if (type.equalsIgnoreCase("d2lm:PredefinedNonOrderedLocationGroup") &&
                        containerId.equalsIgnoreCase("NTIS_Network_Links")) {
                    inLinksPredefinedLocationContainer = true;
                } else if (type.equalsIgnoreCase("d2lm:PredefinedNonOrderedLocationGroup") &&
                        containerId.equalsIgnoreCase("NTIS_Network_Nodes")) {
                    inNodesPredefinedLocationContainer = true;
                }
            }
        }
        if (inLinksPredefinedLocationContainer && qName.equalsIgnoreCase("d2lm:predefinedLocation")) {
            String id = attributes.getValue("id");
            currLink = new Link();
            currLink.setId(Long.parseLong(id));

            inPredefinedLocation = true;

        } else if (inNodesPredefinedLocationContainer && qName.equalsIgnoreCase("d2lm:predefinedLocation")) {
            String id = attributes.getValue("id");
            currNode = new Node();
            currNode.setId(Long.parseLong(id));

            inPredefinedLocation = true;
        }

        if (inPredefinedLocation && qName.equalsIgnoreCase("d2lm:LinearWithinLinearElement")) {
            inLinearWithinLinearElement = true;
        } else if (inPredefinedLocation && qName.equalsIgnoreCase("d2lm:latitude")) {
            inNodeLat = true;
        } else if (inPredefinedLocation && qName.equalsIgnoreCase("d2lm:longitude")) {
            inNodeLon = true;
        } else if (inPredefinedLocation && qName.equalsIgnoreCase("d2lm:roadNumber")) {
            inRoadName = true;
        } else if (inPredefinedLocation && qName.equalsIgnoreCase("d2lm:predefinedLocationName")) {
            inPredefinedLocationName = true;
        }

        if (inPredefinedLocationName && qName.equalsIgnoreCase("d2lm:value")) {
            inDescription = true;
        }
        if (inLinearWithinLinearElement && qName.equalsIgnoreCase("d2lm:fromPoint")) {
            inFromPoint = true;
        }
        if (inLinearWithinLinearElement && qName.equalsIgnoreCase("d2lm:toPoint")) {
            inToPoint = true;
        }
        if ((inToPoint || inFromPoint) && qName.equalsIgnoreCase("d2lm:fromReferent")) {
            inFromReferent = true;
        }
        if (inFromReferent && qName.equalsIgnoreCase("d2lm:ReferentIdentifier")) {
            inReferentIdentifier = true;
        }
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {


        if (qName.equalsIgnoreCase("d2lm:predefinedLocationContainer")) {
            if (inLinksPredefinedLocationContainer) {
                inLinksPredefinedLocationContainer = false;
            } else if (inNodesPredefinedLocationContainer) {
                inNodesPredefinedLocationContainer = false;
            }
        } else if (inLinksPredefinedLocationContainer && qName.equalsIgnoreCase("d2lm:predefinedLocation")) {

            linkMap.put(currLink.getId(), currLink);
            inPredefinedLocation = false;

        } else if (inNodesPredefinedLocationContainer && qName.equalsIgnoreCase("d2lm:predefinedLocation")) {

            nodeMap.put(currNode.getId(), currNode);
            inPredefinedLocation = false;
        } else if (inPredefinedLocation && qName.equalsIgnoreCase("d2lm:LinearWithinLinearElement")) {
            inLinearWithinLinearElement = false;
        } else if (inPredefinedLocation && qName.equalsIgnoreCase("d2lm:latitude")) {
            inNodeLat = false;
        } else if (inPredefinedLocation && qName.equalsIgnoreCase("d2lm:longitude")) {
            inNodeLon = false;
        } else if (inLinearWithinLinearElement && qName.equalsIgnoreCase("d2lm:fromPoint")) {
            inFromPoint = false;
        } else if (inLinearWithinLinearElement && qName.equalsIgnoreCase("d2lm:toPoint")) {
            inToPoint = false;
        } else if (inLinearWithinLinearElement && qName.equalsIgnoreCase("d2lm:roadNumber")) {
            inRoadName = false;
        } else if ((inToPoint || inFromPoint) && qName.equalsIgnoreCase("d2lm:fromReferent")) {
            inFromReferent = false;
        } else if (inFromReferent && qName.equalsIgnoreCase("d2lm:ReferentIdentifier")) {
            inReferentIdentifier = false;
        } else if (inPredefinedLocationName && qName.equalsIgnoreCase("d2lm:value")) {
            inDescription = false;
        } else if (inPredefinedLocation && qName.equalsIgnoreCase("d2lm:predefinedLocationName")) {
            inPredefinedLocationName = false;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (inFromPoint && inReferentIdentifier) {
            String fromNodeId = new String(ch, start, length);
            currLink.setFromNodeId(Long.parseLong(fromNodeId));
        } else if (inToPoint && inReferentIdentifier) {
            String toNodeId = new String(ch, start, length);
            currLink.setToNodeId(Long.parseLong(toNodeId));
        } else if (inNodeLat) {
            String nodeLatStr = new String(ch, start, length);
            currNode.setLat(Double.parseDouble(nodeLatStr));
        } else if (inNodeLon) {
            String nodeLonStr = new String(ch, start, length);
            currNode.setLon(Double.parseDouble(nodeLonStr));
        } else if (inRoadName) {
            String roadName = new String(ch, start, length);
            currLink.setRoadName(roadName);
        } else if (inDescription) {
            String descName = new String(ch, start, length);
            currLink.setDescription(descName);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();

        System.out.println("Parsed " + linkMap.size() + " links");
        System.out.println("Parsed " + nodeMap.size() + " nodes");

    }

    public Map<Long, Link> getLinkMap() {
        return linkMap;
    }

    public Map<Long, Node> getNodeMap() {
        return nodeMap;
    }
}