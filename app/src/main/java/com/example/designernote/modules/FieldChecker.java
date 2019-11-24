package com.example.designernote.modules;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class FieldChecker
{

    public boolean checkIfFilled(EditText editText)
    {
        if(editText.getText().toString().matches(""))
            return false;
        else
            return true;
    }

    /*https://stackoverflow.com/questions/12947620/email-address-validation-in-android-on-edittext*/
    public boolean checkIfEmail(EditText editText)
    {
        CharSequence charSequence = editText.getText().toString();
        return (!TextUtils.isEmpty(charSequence) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches());
    }
    public boolean checkIfPhone(EditText editText)
    {
        return android.util.Patterns.PHONE.matcher(editText.getText().toString()).matches();
    }

    public boolean checkTextLong(EditText editText, int size)
    {
        if((editText.getText().toString()).length() > size)
            return false;
        else
            return true;
    }
}
