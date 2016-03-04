package com.mediamath.fileupload;


import com.mediamath.fileupload.resources.FileIO;
import com.mediamath.fileupload.utils.FileUtility;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.ext.MessageBodyWriter;

/**
 * Created by mihir on 3/1/16.
 */
public class FileUploadService extends Application<FileUploadServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new FileUploadService().run(args);
    }

    @Override
    public void initialize(Bootstrap<FileUploadServiceConfiguration> bootstrap) {
        //Nothing to do yet
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(FileUploadServiceConfiguration configuration, Environment environment) throws Exception {
        final FileIO resource = new FileIO(configuration.getMaxFileSizeInMB(),new FileUtility());
        environment.jersey().register(resource);
        environment.jersey().register(MultiPartFeature.class);
    }

}
