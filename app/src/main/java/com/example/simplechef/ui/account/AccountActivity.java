package com.example.simplechef.ui.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simplechef.ui.login.LoginActivity;
import com.example.simplechef.util.GlideApp;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.simplechef.R;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static android.widget.Toast.LENGTH_LONG;


public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "AccountActivity";
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private TextView textViewUsername;
    private TextView textViewEmail;
    private ImageButton imageButtonPhoto;
    private ImageView imageViewBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        imageViewBackground = findViewById(R.id.imageViewBackground);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);
        imageButtonPhoto = findViewById(R.id.imageButtonProfilePic);

        imageButtonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        setupToolbar();
        setupBGImage();
        setUserDataAndPhoto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        // send to firebase
        addProfilePictureToFirebase(bitmap);
    }

    private void setUserDataAndPhoto() {
        String mUsername = mCurrentUser.getDisplayName();
        String mEmail = mCurrentUser.getEmail();
        String mPhotoURL;

        textViewUsername.setText(mUsername);
        textViewEmail.setText(mEmail);

        // sets photo from URL
        if (mCurrentUser.getPhotoUrl() != null) {
            mPhotoURL = mCurrentUser.getPhotoUrl().toString();
            GlideApp
                    .with(this)
                    .load(mPhotoURL)
                    .into(imageButtonPhoto);
        } else {
            GlideApp
                    .with(this)
                    .load(R.drawable.no_photo)
                    .into(imageButtonPhoto);
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView)findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });
    }

    private void setupBGImage() {
        // Glide handles auto-scaling images down to proper resolution
        GlideApp
                .with(this)
                .load(R.drawable.signup_background)
                .centerCrop()
                .into(imageViewBackground);

    }

    private void addProfilePictureToFirebase(final Bitmap bitmap) {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference profilePicturesRef = storage.getReference().child("Users/" + mCurrentUser.getUid() + "/profile_pic.jpg");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profilePicturesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failure uploading file");
                Toast.makeText(getApplicationContext(),"There was a problem uploading the image", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "Success uploading file");

                GlideApp
                        .with(getApplicationContext())
                        .load(bitmap)
                        .into(imageButtonPhoto);


                profilePicturesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, uri.toString());
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri).build();

                        mCurrentUser.updateProfile(profileUpdates);
                    }
                });

            }
        });
    }
}




