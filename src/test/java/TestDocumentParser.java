import be.pielambr.minerva4j.beans.Announcement;
import be.pielambr.minerva4j.beans.Document;
import be.pielambr.minerva4j.parsers.DocumentParser;
import jodd.jerry.Jerry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Pieterjan Lambrecht on 17/06/2015.
 */
public class TestDocumentParser {

    private String html;

    /**
     * Load an HTML example page with courses
     */
    @Before
    public void loadProperties() {
        FileInputStream in = null;
        try {
            in = new FileInputStream("src/test/resources/documents_example.html");
            StringBuilder builder = new StringBuilder();
            int ch;
            while((ch = in.read()) != -1){
                builder.append((char)ch);
            }
            html = new String(builder.toString().getBytes(), "UTF8");
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("HTML file not found");
        } catch (IOException e) {
            System.out.println("Error closing HTML file");
        }
    }

    @Test
    public void testGettingDocuments() throws ParseException {
        Jerry jerry = Jerry.jerry(html);
        Method method = null;
        try {
            method = DocumentParser.class.getDeclaredMethod("parseDocuments", Jerry.class);
            method.setAccessible(true);
            List<Document> documents = (List<Document>) method.invoke(null, jerry);
            Assert.assertEquals(13, documents.size());
            Assert.assertEquals(documents.get(0).getFilename(), "Ecolizer.pdf");
            Assert.assertEquals(documents.get(0).getId(), "13889121");
            Assert.assertEquals(documents.get(12).getFilename(), "Materialendecreet.mp4");
            Assert.assertEquals(documents.get(12).getId(), "13889171");
        } catch (NoSuchMethodException e) {
            System.out.println("Method to be tested was not found");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
