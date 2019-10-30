package com.example.designernote.models;

public class CreateTask
{
    private String spinnerPeopleText;
    private String projectNameText;
    private String saveOnlineText;
    private String chooseTypeOfWOrkText;
    private String[] checkBoxes;
    private String createTaskButText;



    public CreateTask(String spinnerPeopleText, String projectNameText, String saveOnlineText, String chooseTypeOfWOrkText, String createTaskButText, String[] checkBoxes) {
        this.spinnerPeopleText = spinnerPeopleText;
        this.projectNameText = projectNameText;
        this.saveOnlineText = saveOnlineText;
        this.chooseTypeOfWOrkText = chooseTypeOfWOrkText;
        this.createTaskButText = createTaskButText;
        this.checkBoxes = checkBoxes;
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

    public String getCreateTaskButText() {
        return createTaskButText;
    }

    public String[] getCheckBoxes() {
        return checkBoxes;
    }
}
