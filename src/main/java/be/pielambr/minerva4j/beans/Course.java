package be.pielambr.minerva4j.beans;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class Course {

    private final String _code;
    private final String _name;

    /**
     * Default constructor for a course
     * @param code The Minerva code for a course, can be found in URL
     * @param name The full name for the course
     */
    public Course(String code, String name) {
        _code = code;
        _name = name;
    }

    /**
     * Returns the course's code
     * @return The course code
     */
    public String getCode() {
        return _code;
    }

    /**
     * Returns the name of the course
     * @return The full name of the course
     */
    public String getName() {
        return _name;
    }

    /**
     * Returns the hashcode for the course
     * @return The hashcode for the course
     */
    @Override
    public int hashCode() {
        int hash = 11 * _code.hashCode();
        return 11 * hash + _name.hashCode();
    }

    /**
     * Looks if this object is equal to another
     * @param o The other object, to check for equality
     * @return Whether both objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Course course = (Course) o;
        return _code.equals(course._code)
                && _name.equals(course._name);
    }

    /**
     * Returns a basic String representation of a Course
     *
     * @return String representation of a Course
     */
    @Override
    public String toString() {
        return "Course{" +
                "name='" + _name + '\'' +
                ", code='" + _code + '\'' +
                '}';
    }
}
