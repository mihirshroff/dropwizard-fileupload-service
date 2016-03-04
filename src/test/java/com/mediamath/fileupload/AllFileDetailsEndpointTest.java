package com.mediamath.fileupload;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediamath.fileupload.api.FileWordDetails;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mihir on 3/4/16.
 */
public class AllFileDetailsEndpointTest extends ApplicationBaseTest {

    @Test
    public void getAllDetailsTest() throws IOException {
        Response fileUpload1 = executeFileUpload("test.txt");
        Response fileUpload2 = executeFileUpload("test2.txt");
        Response getAll = executeGetAll();

        assertEquals(200,fileUpload1.getStatus());
        assertEquals(200,fileUpload2.getStatus());
        assertEquals(200,getAll.getStatus());

        ObjectMapper mapper = new ObjectMapper();
        List<FileWordDetails> listDetails = mapper.readValue(getAll.readEntity(String.class), new TypeReference<List<FileWordDetails>>(){});
        assertEquals(2,listDetails.size());
        for(FileWordDetails details:listDetails){
            if(details.getName().equals("test.txt")){
                assertEquals(16,details.getWordCount().intValue());
            }

            if(details.getName().equals("test2.txt")){
                assertEquals(11,details.getWordCount().intValue());
            }
        }

    }
}
