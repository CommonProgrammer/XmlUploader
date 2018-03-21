/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entities.Newspaper;
import java.io.File;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Mariusz Kohut
 */
public class NewspaperFacadeTest {

    NewspaperFacade newspaperFacade = new NewspaperFacade();
    
    @Test
    public void parseValidSchema() throws Exception {
        System.out.println("validateXMLSchema");  
        InputStream path = NewspaperFacadeTest.class.getResourceAsStream("/xml/abb.xml");
        File file = File.createTempFile("tempfile", ".tmp");
        FileUtils.copyInputStreamToFile(path, file);
        Newspaper result = newspaperFacade.parseSchema(file);
        assertNotNull(result);
        assertEquals("abb", result.getNewspaperName());
        assertEquals("160", result.getDpi());
        assertEquals(file.getName(), result.getFilename());
        assertEquals("752", result.getHeight());
        assertEquals("1280", result.getWidth());
        assertNotNull(result.getInsertDate()); 
    }

    @Test
    public void parseInvalidSchema() throws Exception {
        System.out.println("validateXMLSchema");  
        InputStream path = NewspaperFacadeTest.class.getResourceAsStream("/xml/invalid.xml");
        File file = File.createTempFile("tempfile2", ".tmp");
        FileUtils.copyInputStreamToFile(path, file);
        Newspaper result = newspaperFacade.parseSchema(file);
        assertNull(result);
    }
}
