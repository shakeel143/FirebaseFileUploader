package com.shakeel.firebasefileuploade;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class FirebaseFileUploader {

    public interface OnUploadCompleteListener {
        void onUploadSuccess(String downloadUrl);

        void onUploadFailure(String errorMessage);
    }

    public static void uploadFile(Upload upload, String storagePath, boolean showProgress,
                                  String progressTitle, String progressMessage,
                                  Context context, OnUploadCompleteListener listener) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(storagePath);

        UploadTask uploadTask;

        if (upload.getUri() != null) {
            uploadTask = storageRef.putFile(upload.getUri());
        } else if (upload.getBytes() != null) {
            uploadTask = storageRef.putBytes(upload.getBytes());
        } else if (upload.getStream() != null) {
            byte[] data;
            try {
                data = new byte[upload.getStream().available()];
                upload.getStream().read(data);
                uploadTask = storageRef.putBytes(data);
            } catch (IOException e) {
                listener.onUploadFailure("Error reading stream");
                return;
            }
        } else {
            listener.onUploadFailure("Unsupported upload type");
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(context);
        if (showProgress) {
            progressDialog.setTitle(progressTitle);
            progressDialog.setMessage(progressMessage);
            progressDialog.setCancelable(false);
            progressDialog.show();

            uploadTask.addOnProgressListener(taskSnapshot -> {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setProgress((int) progress);
            });
        }

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                if (showProgress) {
                    progressDialog.dismiss();
                }
                listener.onUploadSuccess(uri.toString());
            });
        }).addOnFailureListener(exception -> {
            if (showProgress) {
                progressDialog.dismiss();
            }
            listener.onUploadFailure(exception.getMessage());
        });
    }
}
