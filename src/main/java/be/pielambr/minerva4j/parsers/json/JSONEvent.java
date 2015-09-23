package be.pielambr.minerva4j.parsers.json;

public class JSONEvent {

    private String id;
    private String title;
    private String description;
    private Long start;
    private Long end;

    /**
     * Default constructor for a JSONEvent
     */
    public JSONEvent() {

    }

    /**
     * Returns the ID for this event
     * @return The ID for this event
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the title for this event
     * @return The title for this event
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description for this event
     * @return The description for this event
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the start epoch for this event
     * @return The start epoch for this event
     */
    public Long getStart() {
        return start;
    }

    /**
     * Returns the end epoch for this event
     * @return The end epoch for this event
     */
    public Long getEnd() {
        return end;
    }
}