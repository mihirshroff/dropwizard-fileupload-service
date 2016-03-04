package com.mediamath.fileupload;

import com.mediamath.fileupload.api.FileWordDetails;
import org.junit.*;

import javax.ws.rs.core.Response;
import java.io.IOException;
/**
 * Created by mihir on 3/2/16.
 */
public class FileUploadServiceTest extends ApplicationBaseTest{

    @Test
    public void fileUploadTest() throws IOException {
        Response response = executeFileUpload("test.txt");
        FileWordDetails expected = response.readEntity(FileWordDetails.class);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(16, expected.getWordCount().intValue());
        Assert.assertEquals(3, expected.getWordCountMap().get("test").intValue());
    }

    @Test
    public void fileUploadWithBlueWordsTest() throws IOException {
        Response response = executeFileUpload("test3.txt");
        FileWordDetails expected = response.readEntity(FileWordDetails.class);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(4, expected.getWordCount().intValue());
        Assert.assertEquals(1, expected.getWordCountMap().get("away").intValue());
    }

    @Test
    public void largeFileFailureTest() throws IOException {
        Response response = executeFileUpload("test-too-large.txt");
        String expected = response.readEntity(String.class);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals("ERROR: File size has to be greater than 0 and less than 10MB", expected);
    }

    @Test
    public void zeroSizeFileEmptyTest() throws IOException {
        Response response = executeFileUpload("file2.txt");
        FileWordDetails expected = response.readEntity(FileWordDetails.class);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(0, expected.getWordCount().intValue());
        Assert.assertEquals(0, expected.getWordCountMap().size());
    }

    @Test
    public void exact10MBTest() throws IOException {
        Response response = executeFileUpload("test-words.txt");
        Assert.assertEquals(200, response.getStatus());
    }

}
