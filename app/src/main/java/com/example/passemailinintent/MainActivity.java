package com.example.passemailinintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText etEmailAddress, etEmailSubject, etEmailMassage;
    private String emailAddress, emailSubject, emailMassage;
    private int EmailCode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmailAddress = findViewById(R.id.etEmailAddress);
        etEmailSubject = findViewById(R.id.etEmailSubject);
        etEmailMassage = findViewById(R.id.etEmailMassage);
    }

    public void buttonSand(View view) {

        if(isViewsEmpty())
        {
            sandEmail();
        }





    }

    private void sandEmail() {

        String[] emailList = emailAddress.split(",");

        for (String email: emailList)
        {
            if (!isEmailValid(email))
            {
                etEmailAddress.setError("Enter your correct email id");
                return;
            }
        }

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setType("massage/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailList);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,emailMassage);

        startActivityForResult(Intent.createChooser(emailIntent, "Choose an email client"), EmailCode);
    }

    private boolean isViewsEmpty() {

        boolean results = true;

        emailAddress = etEmailAddress.getText().toString().trim();
        emailSubject = etEmailSubject.getText().toString().trim();
        emailMassage = etEmailMassage.getText().toString().trim();

        if (emailSubject.isEmpty())
        {

            etEmailSubject.setError("Enter your email subject");
            results = false;

        }
        else if (emailMassage.isEmpty())
        {

            etEmailMassage.setError("Enter your email massage");
            results = false;

        }






        return results;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EmailCode)
        {

            if (resultCode == RESULT_OK)
            {

                etEmailAddress.setText(null);
                etEmailSubject.setText(null);
                etEmailMassage.setText(null);

            }else if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "Sum problem to sand email", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}