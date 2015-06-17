package be.pielambr.minerva4j.parsers;

import be.pielambr.minerva4j.beans.Course;
import be.pielambr.minerva4j.beans.Document;
import be.pielambr.minerva4j.parsers.json.JSONDocument;
import be.pielambr.minerva4j.utility.Constants;
import com.google.gson.Gson;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.jerry.Jerry;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class DocumentParser {

    private final static Pattern DOCUMENT_REGEX = Pattern.compile("(var document_tree = )([^\\n]+)");

    /**
     * Returns a list of documents for the given course
     * @param browser An instance of the Jerry HttpBrowser
     * @param course The course of which the documents need to be retrieved
     * @return A list of documents
     */
    public static List<Document> getDocuments(HttpBrowser browser, Course course){
        HttpRequest request = HttpRequest.get(Constants.COURSE_URL + course.getCode() + Constants.DOCUMENT);
        browser.sendRequest(request);
        Jerry page;
        try {
            page = Jerry.jerry(new String(browser.getHttpResponse().bodyBytes(), "UTF8"));
        } catch (UnsupportedEncodingException ex){
            page = Jerry.jerry(browser.getHttpResponse().body());
        }
        String head = page.$("head").first().html();
        if(head != null){
            Matcher m = DOCUMENT_REGEX.matcher(head);
            if(m.find()){
                String json = m.group(2).substring(0, m.group(2).length() - 1);
                return parseDocuments(json);
            }
        }
        return new ArrayList<Document>();
    }

    /**
     * Parses a JSON string and returns the documents found within
     * @param json A JSON string containing documents
     * @return A list of documents
     */
    private static List<Document> parseDocuments(String json) {
        List<Document> documents = new ArrayList<Document>();
        JSONDocument jsonDocument = new Gson().fromJson(json, JSONDocument.class);
        if(jsonDocument!= null && jsonDocument.getType().equals(Constants.TYPE_ROOT)){
            if(jsonDocument.getItems() != null) {
                documents.addAll(parseDirectory(jsonDocument.getItems()));
            }
        }
        return documents;
    }

    /**
     * Parses a directory and searches for documents within
     * @param items A map of documents in a directory
     * @return A list of documents
     */
    private static List<Document> parseDirectory(Map<String, JSONDocument> items) {
        List<Document> documents = new ArrayList<Document>();
        for(JSONDocument document : items.values()) {
            if(document.getType().equals(Constants.TYPE_FILE)) {
                Document doc = new Document(document.getId(), document.getFilename());
            } else if(document.getType().equals(Constants.TYPE_FOLDER) && document.getItems() != null) {
                documents.addAll(parseDirectory(document.getItems()));
            }
        }
        return documents;
    }
}
