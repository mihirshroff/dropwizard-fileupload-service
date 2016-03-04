package com.mediamath.fileupload;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.ClassRule;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

/**
 * Created by mihir on 3/4/16.
 */
public class ApplicationBaseTest {

    private static final String TARGET_URL_FILE_UPLOAD = "http://localhost:8888/file/processUpload";
    private static final String TARGET_URL_GET_ALL = "http://localhost:8888/file/all";

    @ClassRule
    public final static DropwizardAppRule<FileUploadServiceConfiguration> RULE =
            new DropwizardAppRule<>(FileUploadService.class, ResourceHelpers.resourceFilePath("fileupload-test.yml"));

    protected Response executeFileUpload(String fileName) throws IOException {
        Client client = ClientBuilder.newClient().register(MultiPartFeature.class);//new JerseyClientBuilder(RULE.getEnvironment()).build("test client").register(MultiPartFeature.class);
        WebTarget webTarget = client.target(TARGET_URL_FILE_UPLOAD);
        MultiPart multiPart = new MultiPart();
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file",
                new File(ClassLoader.getSystemResource(fileName).getFile()), MediaType.APPLICATION_OCTET_STREAM_TYPE);
        multiPart.bodyPart(fileDataBodyPart);

        Response response = webTarget.request(
                MediaType.APPLICATION_JSON).post(
                Entity.entity(multiPart, multiPart.getMediaType()));
        return response;
    }

    protected Response executeGetAll() throws IOException {
        Client client = ClientBuilder.newClient().register(MultiPartFeature.class);//new JerseyClientBuilder(RULE.getEnvironment()).build("test client").register(MultiPartFeature.class);
        WebTarget webTarget = client.target(TARGET_URL_GET_ALL);
        Response response = webTarget.request(
                MediaType.APPLICATION_JSON).get();
        return response;
    }
}
