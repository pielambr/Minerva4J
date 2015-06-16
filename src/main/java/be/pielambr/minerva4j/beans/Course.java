package be.pielambr.minerva4j.beans;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class Course {

    private final String _code;
    private final String _name;

    public Course(String code, String name) {
        _code = code;
        _name = name;
    }

    public String getCode() {
        return _code;
    }

    public String getName() {
        return _name;
    }
}
