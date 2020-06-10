package com.example.park_mate;

import java.util.List;

public class Survey {


    private String question;
    private List<String> options;
    public Survey()
    {

    }
       public Survey(String question, List<String>options)
       {
            this.question=question;
            this.options=options;

       }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
