package com.rock.android.rocklibrary.Utils.Image;

/**
 * Created by rock on 15/10/19.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nianxin on 15/9/22.
 */
public class CameraCore {

    //调用系统相机的Code
    public static final int REQUEST_TAKE_PHOTO_CODE = 1001;
    //拍照裁剪的Code
    public static final int REQUEST_TAKE_PHOTO_CROP_CODE = 1003;
    //调用系统图库的Code
    public static final int REQUEST_TAKE_PICTRUE_CODE = 1002;
    //调用系统图库裁剪Code
    public static final int REQUEST_TAKE_PICTRUE_CROP_CODE = 1004;
    //裁剪的Code
    public static final int REQUEST_TAKE_CROP_CODE = 1005;
    //截取图片的高度
    public static final int REQUEST_HEIGHT = 400;
    //截取图片的宽度
    public static final int REQUEST_WIDTH = 400;


    //用来存储照片的URL
    private Uri photoURL;


    //调用照片的Activity
    private Activity activity;

    //回调函数
    private CameraResult cameraResult;

    public interface CameraResult {
        void onSuccess(String path);

        void onFail(String error);
    }

    public CameraCore(CameraResult cameraResult, Activity activity) {
        this.cameraResult = cameraResult;
        this.activity = activity;
    }

    //调用系统照相机，对Intent参数进行封装
    protected Intent startTakePhoto(Uri photoURL) {
        this.photoURL = photoURL;
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURL);//将拍取的照片保存到指定URI
        return intent;
    }


    //调用系统图库,对Intent参数进行封装
    protected Intent startTakePicture(Uri photoURL) {
        this.photoURL = photoURL;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");//从所有图片中进行选择
        return intent;
    }


    //调用系统裁剪图片，对Intent参数进行封装
    protected Intent takeCropPicture(Uri photoURL, int with, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoURL, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", with);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURL);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }


    //拍照
    public void getPhotoFromCamera(Uri uri) {
        activity.startActivityForResult(startTakePhoto(uri), REQUEST_TAKE_PHOTO_CODE);
    }

    //拍照后截屏
    public void getPhotoFromCameraCrop(Uri uri) {
        Intent intent = startTakePhoto(uri);
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//将拍取的照片保存到指定URI
        activity.startActivityForResult(intent, REQUEST_TAKE_PHOTO_CROP_CODE);
    }

    //获取系统相册
    public void getPhotoFromAlbum(Uri uri) {
        activity.startActivityForResult(startTakePicture(uri), REQUEST_TAKE_PICTRUE_CODE);
    }

    //获取系统相册后裁剪
    public void getPhotoFromAlbumCrop(Uri uri) {
        activity.startActivityForResult(startTakePicture(uri), REQUEST_TAKE_PICTRUE_CROP_CODE);
    }


    //处理onActivityResult
    public void onResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                //选择系统图库
                case REQUEST_TAKE_PICTRUE_CODE:
                    //获取系统返回的照片的Uri
                    photoURL = intent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    //从系统表中查询指定Uri对应的照片
                    Cursor cursor = activity.getContentResolver().query(photoURL, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);  //获取照片路径
                    cursor.close();
                    if (!TextUtils.isEmpty(picturePath)) {
                        cameraResult.onSuccess(picturePath);
                    } else {
                        cameraResult.onFail("文件没找到");
                    }
                    break;
                //选择系统图库.裁剪
                case REQUEST_TAKE_PICTRUE_CROP_CODE:
                    photoURL = intent.getData();
                    activity.startActivityForResult(takeCropPicture(photoURL, REQUEST_WIDTH, REQUEST_HEIGHT), REQUEST_TAKE_CROP_CODE);
                    break;
                //调用相机
                case REQUEST_TAKE_PHOTO_CODE:
                    cameraResult.onSuccess(photoURL.getPath());
                    break;
                //调用相机,裁剪
                case REQUEST_TAKE_PHOTO_CROP_CODE:
                    activity.startActivityForResult(takeCropPicture(Uri.fromFile(new File(photoURL.getPath())), REQUEST_WIDTH, REQUEST_HEIGHT), REQUEST_TAKE_CROP_CODE);
                    break;
                //裁剪之后的回调
                case REQUEST_TAKE_CROP_CODE:
                    String path = getPicFromUri(photoURL, activity);
                    cameraResult.onSuccess(path);
                    break;
                default:
                    break;
            }
        }
    }


    public static String getPicFromUri(Uri contentUri, Context context) {
        try {
            Activity ac = (Activity) context;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = ac.managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    /**
     * 获得系统相册中所有图片的信息,包括绝对路径,有了绝对路径可以直接使用Picasso等工具来加载图片
     * */
    public List<Album> getAlbumData(Context ctx) {
        List<Album> albums = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.MediaColumns.DATA
        };

        // content:// style URI for the "primary" external storage volume
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Make the query.
        Cursor cur = ctx.getContentResolver().query(images,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
        );

        Log.i("ListingImages", " query count=" + cur.getCount());

        if (cur.moveToFirst()) {
            String bucket;
            String date;
            String path;
            int bucketColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int dateColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.DATE_TAKEN);

            int pathId = cur.getColumnIndex(MediaStore.MediaColumns.DATA);

            do {
                // Get the field values
                bucket = cur.getString(bucketColumn);
                date = cur.getString(dateColumn);
                path = cur.getString(pathId);
                Album a = new Album();
                a.setBucket(bucket);
                a.setPath(path);
                a.setDate(date);
                albums.add(a);
                System.out.println("path======>"+path);
            } while (cur.moveToNext());

        }
        return albums;
    }

}
