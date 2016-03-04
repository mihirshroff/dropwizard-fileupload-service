package com.mediamath.fileupload.utils;

import com.mediamath.fileupload.api.FileWordDetails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Created by mihir on 3/2/16.
 */
public class FileUtility {


    public boolean validateFileSizeInMB(long size, String maxFileUploadSize) {
        Integer maxMBs = Integer.parseInt(maxFileUploadSize);
        if(size <= FileUtils.ONE_MB * maxMBs){
            return true;
        }
        return false;
    }

    // save uploaded file to new location
    public long parseFileToWordsMap(InputStream uploadedInputStream,FileWordDetails details,String filterWord, String delimitter) throws IOException {
        LineIterator lineIterator = IOUtils.lineIterator(uploadedInputStream, "ASCII");
        long wordCount = 0;
        while(lineIterator.hasNext()){
            String s = lineIterator.nextLine();
            if(!StringUtils.isEmpty(s)) {
                wordCount = wordCount + extractWords(details, s.replaceAll("[^A-Za-z ]", ""), filterWord, delimitter);
            }
        }
        return wordCount;
    }

    public long extractWords(FileWordDetails details, String fileRead, String filterWord, String delimitter) {
        return Arrays.asList(fileRead.trim().split(delimitter))
                .stream()
                .filter(s -> !s.toLowerCase().contains(filterWord.toLowerCase()) && !s.equals(fileRead))
                .map(s -> details.addWordCount(s))
                .count();
    }
}
