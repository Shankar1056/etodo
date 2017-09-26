package apextechies.etodo.dummy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import apextechies.etodo.R;

/**
 * Created by Shankar on 9/23/2017.
 */

public class Test extends AppCompatActivity implements View.OnClickListener{
    EditText editTextSubject, editTextMessage;
    TextView TextEmail;
    Button btnSend, btnAttachment;
    String email, subject, message, attachmentFile;
    Uri URI = null;
    private static final int PICK_FROM_GALLERY = 101;
    int columnIndex;
    ImageView image_view;
    private static final int REQUEST_CODE = 0x11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        TextEmail = (TextView) findViewById(R.id.TextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        btnAttachment = (Button) findViewById(R.id.buttonAttachment);
        btnSend = (Button) findViewById(R.id.buttonSend);
        image_view = (ImageView) findViewById(R.id.image_view);

        btnSend.setOnClickListener(this);
        btnAttachment.setOnClickListener(this);

        String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE"};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE); // without sdk version check
    }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == REQUEST_CODE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // save file
                } else {
                    Toast.makeText(getApplicationContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
                }
            }
        }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    image_view.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(Test.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(Test.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    @Override
    public void onClick(View v) {

        if (v == btnAttachment) {
            openGallery();

        }
        if (v == btnSend) {
            try {
                email = TextEmail.getText().toString();
                subject = editTextSubject.getText().toString();
                message = editTextMessage.getText().toString();

                final Intent emailIntent = new Intent(
                        android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[] { email });
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        subject);
                if (URI != null) {
                    emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                }
                emailIntent
                        .putExtra(android.content.Intent.EXTRA_TEXT, message);
                this.startActivity(Intent.createChooser(emailIntent,
                        "Sending email..."));

            } catch (Throwable t) {
                Toast.makeText(this,
                        "Request failed try again: " + t.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    public void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_FROM_GALLERY);

    }

}