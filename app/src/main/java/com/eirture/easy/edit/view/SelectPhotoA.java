package com.eirture.easy.edit.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.eirture.easy.BuildConfig;
import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;
import com.google.common.base.Strings;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.eirture.rxcommon.base.StorageUtils.isExternalStorageWritable;

/**
 * Created by eirture on 17-1-15.
 */

public class SelectPhotoA extends BaseActivity {
    private static final int REQUEST_CAMERA_CODE = 1;
    private static final int REQUEST_GALLERY_CODE = 2;
    public static final String RESULT_PATH_KEY = "photo_path";

    private static final String PICTURE_NAME_FORMAT = "JPEG_%s_";
    private static final SimpleDateFormat PICTURE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");


    private ListView lvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lvContent = new ListView(this);
        ViewGroup.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lvContent.setLayoutParams(params);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.add_photo));
        lvContent.setAdapter(adapter);
        setContentView(lvContent);

        lvContent.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startCamera();
                    break;
                case 1:
                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUEST_GALLERY_CODE);
                    break;
            }
        });
    }

    private String currentImagePath;

    private void startCamera() {
        if (!isExternalStorageWritable()) {
            Toast.makeText(this, "外部存储不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                currentImagePath = photoFile.getAbsolutePath();
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                photoFile));
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            finish();
            return;
        }
        if (requestCode == REQUEST_GALLERY_CODE) {
            Uri uri = data.getData();
            try {
                currentImagePath = getPathFromContentPath(uri);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        returnPath();
    }

    private String getPathFromContentPath(Uri contentURI) throws IOException {
        String result = "";
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor != null) { // Source is Dropbox or other similar local file path
            cursor.moveToFirst();
            int idx_path = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx_path);
            cursor.close();
        }
        return result;
    }

    private String copy2PrivateStorage(String path) {
        File from = new File(path);
        File to = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), from.getName());
        try {
            Files.copy(from, to);
        } catch (IOException e) {
            e.printStackTrace();
            return from.getPath();
        }
        return to.getPath();
    }

    private static File createImageFile() throws IOException {
        File image = File.createTempFile(
                String.format(PICTURE_NAME_FORMAT, PICTURE_DATE_FORMAT.format(new Date())),
                ".jpg",
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        return image;
    }


    private void returnPath() {
        if (!Strings.isNullOrEmpty(currentImagePath)) {
            Intent intent = new Intent();
            intent.putExtra(RESULT_PATH_KEY, "file://" + copy2PrivateStorage(currentImagePath));
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    public static void start(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(activity, SelectPhotoA.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void start(@NonNull Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), SelectPhotoA.class);
        fragment.startActivityForResult(intent, requestCode);
    }
}
