package com.mediamath.fileupload.resources;

import com.mediamath.fileupload.api.FileWordDetails;
import com.mediamath.fileupload.utils.FileUtility;
import com.mediamath.fileupload.views.FileView;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by mihir on 3/1/16.
 */
@Path("/file")
public class FileIO {

    private final String maxFileUploadSize;
    private final ConcurrentHashMap<String,FileWordDetails> cacheResponses;
    private final FileUtility fileUtility;

    public FileIO(String maxFileSizeInMB, FileUtility fileUtils){
        this.maxFileUploadSize = maxFileSizeInMB;
        this.cacheResponses = new ConcurrentHashMap<>();
        this.fileUtility = fileUtils;
    }

    private final Logger LOGGER = LoggerFactory.getLogger(FileIO.class);

    @GET
    @Path("/uploadFile")
    public FileView uploadFile() {
        return new FileView();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFilesUploaded() {
        if(cacheResponses.size() <= 0){
            return Response.ok("No files uploaded yet").build();
        }else {
            return Response.ok(cacheResponses.values().stream().collect(Collectors.toList())).build();
        }
    }

    @POST
    @Path("/processUpload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
                                    @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
        LOGGER.info("Size of file:"+fileDetail.getSize());
        if(fileUtility.validateFileSizeInMB(fileDetail.getSize(), maxFileUploadSize)) {
            FileWordDetails details = new FileWordDetails(fileDetail.getFileName());
            details.setWordCount(fileUtility.parseFileToWordsMap(uploadedInputStream, details, "blue", " "));
            cacheResponses.put(fileDetail.getFileName(),details);
            return Response.ok(details,MediaType.APPLICATION_JSON_TYPE).build();
        }
        else{
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR: File size has to be greater than 0 and less than " + maxFileUploadSize + "MB").build();
        }

    }
}
