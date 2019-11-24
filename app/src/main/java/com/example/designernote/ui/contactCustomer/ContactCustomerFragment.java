package com.example.designernote.ui.contactCustomer;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.example.designernote.modules.FieldChecker;
import com.example.designernote.R;
import com.example.designernote.helper.InternetDetector;
import com.example.designernote.helper.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ContactCustomerFragment extends Fragment {

    private ContactCustomerViewModel contactCustomerViewModel;
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_LABELS);
    private TextView textLabels;
    private final int SELECT_PHOTO = 1;
    private FirebaseAuth mAuth;
    private InternetDetector internetDetector;
    private FirebaseUser mUser;
    FloatingActionButton sendFabButton;
    Toolbar toolbar;
    private GoogleSignInAccount googleSignInAccount;
    AuthCredential authCredential;
    ImageView attachmentImage;
    ProgressDialog mProgress;
    private Button browse;
    DialogProperties properties = new DialogProperties();
    private FieldChecker fieldChecker;
    EditText recipient,enterSubject, enterMessage;
    Uri attachment;
    private  FilePickerDialog dialog;
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactCustomerViewModel =
                ViewModelProviders.of(this).get(ContactCustomerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact_customer, container, false);
        contactCustomerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        init(root);

        sendFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldChecker.checkIfFilled(enterMessage) && fieldChecker.checkIfFilled(enterSubject))
                {
                    if(fieldChecker.checkIfEmail(recipient))
                        sendEmail(recipient.getText().toString(),enterSubject.getText().toString(),enterMessage.getText().toString(), attachment);
                    else
                        Toast.makeText(getContext(), "E-mail is not in valid format.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Message or subject field is empty!", Toast.LENGTH_SHORT).show();
            }
        });
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkPermissionsAndOpenFilePicker();
            }
        });
        dialog = new FilePickerDialog(getContext(),properties);
        dialog.setTitle("Select a File");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {

                //TODO: add file:// before path and resolve problem with internal storage
                attachmentImage.setImageURI(Uri.parse(files[0]));
                attachment = Uri.parse(files[0]);

                //files is the array of the paths of files selected by the Application User.
            }
        });
        init(root);

        /******************************part of G-mail API***********************************/
        //googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        return root;
    }

    /*CODE FROM:
    *
    *https://github.com/nbsp-team/MaterialFilePicker/blob/master/app/src/main/java/com/dimorinny/sample/MainActivity.java#L38-L69
    *
    * */
    protected void sendEmail(String email, String subject, String message, Uri attachment) {
        String[] sendTo = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        if(attachment != null)
            emailIntent .putExtra(Intent.EXTRA_STREAM, attachment);

        emailIntent.putExtra(Intent.EXTRA_EMAIL, sendTo);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            getActivity().finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                Toast.makeText(getContext(),"Permission is Required for getting list of files",Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, 1);
            }
        } else {
            openFilePicker();
        }
    }

    private void openFilePicker()
    {
        dialog.show();
    }

    private void init(View root) {

        mProgress = new ProgressDialog(getContext());
        textLabels = root.findViewById(R.id.textLabels);
        mProgress.setMessage("Sending...");
        attachment = null;
        fieldChecker = new FieldChecker();
        attachmentImage = root.findViewById(R.id.attachmentImage);
        browse = root.findViewById(R.id.browseBtn);
        recipient = root.findViewById(R.id.recipient);
        enterSubject = root.findViewById(R.id.enterSubject);
        enterMessage = root.findViewById(R.id.enterMessage);
        sendFabButton = root.findViewById(R.id.fab);
        ContextWrapper cw = new ContextWrapper(getContext());
        File directory = cw.getDir("DesignerNote", Context.MODE_PRIVATE);
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = directory;
        properties.error_dir = directory;
        properties.offset = directory;
        properties.extensions = null;

    }

    /******************************G-mail API***********************************/
    //region Gmail API for getting email-mailbox(not working)
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

        File tokenFolder = new File(Environment.getExternalStorageDirectory() +
                File.separator + TOKENS_DIRECTORY_PATH);
        if (!tokenFolder.exists()) {
            tokenFolder.mkdirs();
        }
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                JSON_FACTORY, googleSignInAccount.getId(),googleSignInAccount.getServerAuthCode(), SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(tokenFolder))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(googleSignInAccount.getId());
    }




    private void showMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    private void getResultsFromApi(View view) {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();

        }
        //change for account credentials
        /*
        else if ("" == null) {
            showMessage(view, "Account problem.");
        } else if (!internetDetector.checkMobileInternetConn()) {
            showMessage(view, "No network connection available.");
        } else if (!Utils.isNotEmpty(edtToAddress)) {
            showMessage(view, "To address Required");
        } else if (!Utils.isNotEmpty(edtSubject)) {
            showMessage(view, "Subject Required");
        } else if (!Utils.isNotEmpty(edtMessage)) {
            showMessage(view, "Message Required");
        } else {
            //pridat poslat mail
        }*/
    }
    // Method for Checking Google Play Service is Available
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(getContext());
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    // Method to Show Info, If Google Play Service is Not Available.
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(getContext());
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    // Method for Google Play Services Error Info
    void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                getActivity(),
                connectionStatusCode,
                Utils.REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utils.REQUEST_PERMISSION_GET_ACCOUNTS:
                break;
            case SELECT_PHOTO:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Utils.REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    showMessage(sendFabButton, "This app requires Google Play Services. Please install " +
                            "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi(sendFabButton);
                }
                break;
            case Utils.REQUEST_ACCOUNT_PICKER:
                break;
            case Utils.REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi(sendFabButton);
                }
                break;
            case SELECT_PHOTO:
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            attachment = Uri.parse(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
        }
    }*/
    //endregion
}

