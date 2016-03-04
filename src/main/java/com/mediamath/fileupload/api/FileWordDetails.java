package com.mediamath.fileupload.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mihir on 3/1/16.
 */
public class FileWordDetails {

    private String name;
    private Map<String,Integer> wordCountMap;
    private Long wordCount;


    public Long getWordCount() {
        return wordCount;
    }

    public void setWordCount(Long wordCount) {
        this.wordCount = wordCount;
    }

    public FileWordDetails(){}

    public FileWordDetails(String name){

        this.name = name;
        wordCountMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getWordCountMap() {
        return wordCountMap;
    }

    public void setWordCountMap(Map<String, Integer> wordCountMap) {
        this.wordCountMap = wordCountMap;
    }

    public String addWordCount(String word){
        Integer count = wordCountMap.get(word.toLowerCase());
        if(count == null)
            wordCountMap.put(word.toLowerCase(),1);
        else
            wordCountMap.put(word.toLowerCase(),count+1);
        return word;
    }
}
