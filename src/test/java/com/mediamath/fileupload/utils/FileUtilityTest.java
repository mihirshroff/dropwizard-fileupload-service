package com.mediamath.fileupload.utils;

import com.mediamath.fileupload.api.FileWordDetails;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mihir on 3/2/16.
 */
public class FileUtilityTest {

    FileUtility fileUtility;

    @Before
    public void setup()
    {
        fileUtility = new FileUtility();
    }

    @Test
    public void testReadFileToString() throws IOException,URISyntaxException
    {
        FileWordDetails details = new FileWordDetails("test.txt");
        InputStream inputStream = new FileInputStream(ClassLoader.getSystemResource("test.txt").getFile());
        assertEquals(16,fileUtility.parseFileToWordsMap(inputStream,details,"blue"," "));
        inputStream = new FileInputStream(ClassLoader.getSystemResource("test2.txt").getFile());
        assertEquals(11,fileUtility.parseFileToWordsMap(inputStream,details,"blue"," "));
    }

    @Test
    public void testEmptyFile() throws IOException,URISyntaxException
    {
        FileWordDetails details = new FileWordDetails("file2.txt");
        InputStream inputStream = new FileInputStream(ClassLoader.getSystemResource("file2.txt").getFile());
        assertEquals(0,fileUtility.parseFileToWordsMap(inputStream,details,"blue"," "));
        assertEquals(0,details.getWordCountMap().size());
    }

    @Test
    public void testExtractWords()
    {
        FileWordDetails details = new FileWordDetails("Test.txt");
        long words = fileUtility.extractWords(details,"TEST FOX RAN AWAY blueBERRIE TEST WHAT!","blue"," ");
        assertEquals(2,details.getWordCountMap().get("test").intValue());
        assertEquals(6,words);
    }

    @Test
    public void testFileSizeCheck()
    {
        assertFalse(fileUtility.validateFileSizeInMB(1048576100L,"10"));
        assertTrue(fileUtility.validateFileSizeInMB(10485760L, "10"));
        assertFalse(fileUtility.validateFileSizeInMB(0L, "10"));


    }
}
