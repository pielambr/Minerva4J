package be.pielambr.minerva4j.parsers.json;

import java.util.Map;

/**
 * Created by Pieterjan Lambrecht on 17/06/2015.
 */
public class JSONDocument {

    private String type;
    private String id;
    private String filename;
    private Map<String, JSONDocument> items;

    /**
     * Default constructor for a JSONDocument
     */
    public JSONDocument() {

    }

    /**
     * Returns the type of this document
     * @return The type of this document
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the id of this document
     * @return The id of this document
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the filename of this document
     * @return the filename of this document
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Returns a map of items if the document is a directory
     * @return A map of items if the document is a directory
     */
    public Map<String, JSONDocument> getItems() {
        return items;
    }
}
