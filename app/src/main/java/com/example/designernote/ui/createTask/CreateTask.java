package com.example.designernote.ui.createTask;

public class CreateTask
{
    private String spinnerPeopleText;
    private String projectNameText;
    private String saveOnlineText;
    private String chooseTypeOfWOrkText;


    public CreateTask(String spinnerPeopleText, String projectNameText, String saveOnlineText, String chooseTypeOfWOrkText) {
        this.spinnerPeopleText = spinnerPeopleText;
        this.projectNameText = projectNameText;
        this.saveOnlineText = saveOnlineText;
        this.chooseTypeOfWOrkText = chooseTypeOfWOrkText;
    }

    public String getSpinnerPeopleText() {
        return spinnerPeopleText;
    }

    public String getProjectNameText() {
        return projectNameText;
    }

    public String getSaveOnlineText() {
        return saveOnlineText;
    }

    public String getChooseTypeOfWOrkText() {
        return chooseTypeOfWOrkText;
    }

}
