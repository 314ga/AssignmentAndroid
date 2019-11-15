package com.example.designernote.models;

public class ParameterPass {

    private String[] parameters;
    TaskName task;
    public ParameterPass()
    {
    }
    public enum TaskName{

        GETCUSTOMERNAMES, GETCUSTOMERID, GETBASICINFOPROJECTS;

    }
    public void setParameters(String[] parms)
    {
        parameters = parms;
    }
    public String[]  getParameters()
    {
        return parameters;
    }

    public void setGetCustomerId()
    {
        task = TaskName.GETCUSTOMERID;
    }
    public void setGetCustomerNames()
    {
        task = TaskName.GETCUSTOMERNAMES;
    }

    public TaskName getTaskName()
    {
        return task;
    }
}
