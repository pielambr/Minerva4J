package be.pielambr.minerva4j.beans;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class Document {

    private final String _id;
    private final String _filename;

    /**
     * Default constructor for a document
     * @param id The id for this document
     * @param filename The filename for this document
     */
    public Document(String id, String filename) {
        _id = id;
        _filename = filename;
    }

    /**
     * Returns the id of this document
     * @return The id of this document
     */
    public String getId() {
        return _id;
    }

    /**
     * Returns the filename for this document
     * @return The filename for this document
     */
    public String getFilename() {
        return _filename;
    }

    /**
     * Checks if two objects are the same
     * @param o The object to compare to
     * @return Whether these two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Document document = (Document) o;
        return _id.equals(document._id)
                &&_filename.equals(document._filename);
    }

    /**
     * Returns a hashcode for this document
     * @return A hashcode for this document
     */
    @Override
    public int hashCode() {
        int result = _id.hashCode();
        return 11 * result + _filename.hashCode();
    }
}
