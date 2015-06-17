package be.pielambr.minerva4j.beans;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class Document {

    private final String _id;
    private final String _filename;

    public Document(String id, String filename) {
        _id = id;
        _filename = filename;
    }

    public String getId() {
        return _id;
    }

    public String getFilename() {
        return _filename;
    }
}
