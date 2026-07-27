package com.example.s26g5;

import android.content.Context;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public final class UploadImagePicker implements DefaultLifecycleObserver {

    public interface Callback {

        void onImageSelected(Uri imageUri);
        void onUploadSuccess(String publicUrl);
        void onError(String message);
        void onSelectionCancelled();
    }

    private final ActivityResultRegistry registry;
    private final String registryKey;
    private final SupabaseImageUploader supabaseUploader;
    private final Callback callback;
    private ActivityResultLauncher<PickVisualMediaRequest> pickerLauncher;
    private String pendingLotNumber;
    private boolean busy;

    public UploadImagePicker(
            Context context,
            ActivityResultRegistry registry,
            Lifecycle lifecycle,
            String registryKey,
            Callback callback
    ) {
        this.registry = registry;
        this.registryKey = registryKey;
        this.callback = callback;

        supabaseUploader = new SupabaseImageUploader(context.getApplicationContext());

        lifecycle.addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        pickerLauncher = registry.register(
                registryKey,
                owner,
                new ActivityResultContracts.PickVisualMedia(),
                this::handleSelectedImage
        );
    }

    public void selectAndUpload(String lotNumber) {
        if (lotNumber == null || lotNumber.trim().isEmpty()) {
            callback.onError("Lot number cannot be empty.");
            return;
        }
        if (pickerLauncher == null) {
            callback.onError("Image picker is not ready.");
            return;
        }
        if (busy) {
            callback.onError("An image selection or upload is already running.");
            return;
        }

        pendingLotNumber = lotNumber.trim();
        busy = true;

        PickVisualMediaRequest request = new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build();

        pickerLauncher.launch(request);
    }

    private void handleSelectedImage(Uri imageUri) {
        if (imageUri == null) {
            busy = false;
            pendingLotNumber = null;
            callback.onSelectionCancelled();
            return;
        }

        callback.onImageSelected(imageUri);

        supabaseUploader.uploadImage(
                imageUri,
                pendingLotNumber,
                new SupabaseImageUploader.UploadCallback() {
                    @Override
                    public void onSuccess(String publicUrl) {
                        busy = false;
                        pendingLotNumber = null;

                        callback.onUploadSuccess(publicUrl);
                    }

                    @Override
                    public void onError(String message) {
                        busy = false;
                        pendingLotNumber = null;

                        callback.onError(message);
                    }
                }
        );
    }
}