package com.mediamath.fileupload;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by mihir on 3/1/16.
 */
public class FileUploadServiceConfiguration extends Configuration {
    @NotEmpty
    private String maxFileSizeInMB;

    public String getMaxFileSizeInMB() {
        return maxFileSizeInMB;
    }

    public void setMaxFileSizeInMB(String maxFileSizeInMB) {
        this.maxFileSizeInMB = maxFileSizeInMB;
    }
}
