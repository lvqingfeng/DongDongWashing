package dongdongwashing.com.ui.PersonalCenter;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.getAccountInfo.GetAccountRequest;
import dongdongwashing.com.entity.getAccountInfo.GetAccountResult;
import dongdongwashing.com.entity.updateAccount.UpdateAccountRequest;
import dongdongwashing.com.entity.updateAccount.UpdateAccountResult;
import dongdongwashing.com.entity.uploadImage.UploadImageRequest;
import dongdongwashing.com.ui.MainActivity;
import dongdongwashing.com.util.DataConversionByShen;
import dongdongwashing.com.util.DialogByOneButton;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.DialogByTwoButton;
import dongdongwashing.com.util.GlobalConsts;
import dongdongwashing.com.util.ImageUtils;
import dongdongwashing.com.util.IsCameraCanUse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {

    private ImageView setHeadBack; // 返回
    private TextView settingHead; // 设置头像
    private CircleImageView setHeadPhoto; // 头像容器
    private EditText nickNameEt; // 昵称
    private EditText nameEt; // 姓名
    private EditText numberPlateEt; // 车牌
    private EditText carTypeEt; // 车型
    private EditText carColorEt; // 颜色
    private Button genderManIv, genderWomanIv; // 性别
    private Button editProfileSaveBtn;
    private DialogByTwoButton dialog;
    private DialogByOneButton dialog1;
    private DialogByProgress dialogByProgress;
    private OkHttpClient okHttpClient = new OkHttpClient();

    private SharedPreferences editUserInfoSP;
    private String nickNameString, nameString, numberPlateString, carTypeString, carColorString;
    private String genderString = "";
    private String imageString;
    private boolean isSave = false;

    private PopupWindow setHeadPW;
    private Button camera, album, cancel;
    public static String SDPATH = Environment.getExternalStorageDirectory() + "/hsyfm/recordimg/";
    public static File destDir = new File(SDPATH);
    private File imgFile;
    private boolean isShowingPW;
    private String imageMsg;
    private String id, enabled, userName, displayName, carNumber, carModel, carColor, headPicture;
    private String sex = "";

    private static final int CROP_PHOTO = 100;
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 102;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 103;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.UPLOAD_IMAGE_HANDLER: // 上传头像
                    UploadImageRequest uploadRequest = (UploadImageRequest) msg.obj;
                    if (dialogByProgress != null && dialogByProgress.isShowing()) {
                        dialogByProgress.dismiss();
                    }
                    imageMsg = uploadRequest.getErrmsg();
                    if (imageMsg.equals("上传成功")) {
                        isSave = true;
                        imageString = uploadRequest.getFileName();
                        dialog1 = new DialogByOneButton(EditProfileActivity.this, "提示", "头像修改成功", "确定");
                        dialog1.show();
                        dialog1.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialog1.dismiss();
                            }
                        });
                    }
                    break;

                case GlobalConsts.UPLOAD_ACCOUNT_HANDLER: // 上传个人资料
                    UpdateAccountRequest updateAccountRequest = (UpdateAccountRequest) msg.obj;
                    if (dialogByProgress != null && dialogByProgress.isShowing()) {
                        dialogByProgress.dismiss();
                    }
                    String updateAccountString = updateAccountRequest.getMsg();
                    if (updateAccountString.equals("用户资料完善成功!")) {
                        dialog1 = new DialogByOneButton(EditProfileActivity.this, "提示", "资料编辑成功", "确定");
                        dialog1.show();
                        dialog1.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialog1.dismiss();
                                EditProfileActivity.this.finish();
                            }
                        });
                    } else if (updateAccountString.equals("用户资料完善失败!")) {
                        dialog1 = new DialogByOneButton(EditProfileActivity.this, "提示", "资料编辑失败，请检查网络或稍后重试", "确定");
                        dialog1.show();
                        dialog1.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialog1.dismiss();
                            }
                        });
                    }
                    break;

                case GlobalConsts.GET_ACCOUNT_INFO_HANDLER:
                    GetAccountRequest getAccountRequest = (GetAccountRequest) msg.obj;
                    String getAccountstr = getAccountRequest.getMsg();
                    if (getAccountstr.equals("用户信息")) {
                        if (dialogByProgress != null && dialogByProgress.isShowing()) {
                            dialogByProgress.dismiss();
                        }
                        GetAccountResult getAccountResult = getAccountRequest.getData();
                        imageString = getAccountResult.getHeadPicture();
                        nickNameString = getAccountResult.getDisplayName();
                        nameString = getAccountResult.getUserName();
                        genderString = getAccountResult.getSex();
                        numberPlateString = getAccountResult.getCarNumber();
                        carTypeString = getAccountResult.getCarModel();
                        carColorString = getAccountResult.getCarColor();

                        if (imageString == null) {
                            setHeadPhoto.setBackgroundResource(R.mipmap.head);
                        } else {
                            Glide.with(EditProfileActivity.this).load(GlobalConsts.DONG_DONG_IMAGE_URL + imageString).into(setHeadPhoto);
                        }

                        if (!(nickNameString == null)) {
                            nickNameEt.setText(nickNameString);
                        } else {
                            nickNameEt.setText("");
                        }

                        if (!(nameString == null)) {
                            nameEt.setText(nameString);
                        } else {
                            nameEt.setText("");
                        }

                        if (!(numberPlateString == null)) {
                            numberPlateEt.setText(numberPlateString);
                        } else {
                            numberPlateEt.setText("");
                        }

                        if (!(carTypeString == null)) {
                            carTypeEt.setText(carTypeString);
                        } else {
                            carTypeEt.setText("");
                        }

                        if (!(carColorString == null)) {
                            carColorEt.setText(carColorString);
                        } else {
                            carColorEt.setText("");
                        }

                        if (!(genderString == null) && genderString.equals("1")) {
                            genderManIv.setBackgroundResource(R.mipmap.check);
                            genderWomanIv.setBackgroundResource(R.mipmap.uncheck);
                        } else if (!(genderString == null) && genderString.equals("0")) {
                            genderManIv.setBackgroundResource(R.mipmap.uncheck);
                            genderWomanIv.setBackgroundResource(R.mipmap.check);
                        }

                        if (!(nameString == null) || !(numberPlateString == null) || !(carTypeString == null) || !(carColorString == null)) {
                            editProfileSaveBtn.setText("编辑");
                        } else {
                            editProfileSaveBtn.setText("保存");
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_layout);

        dialogByProgress = new DialogByProgress(EditProfileActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        SharedPreferences spf = EditProfileActivity.this.getSharedPreferences("user_info", 0);
        id = spf.getString("uid", "");

        imgFile = new File(destDir, getPhotoFileName());

        initView();
        initData();
        initListener();
    }

    /**
     * 根据用户id获取数据并赋值
     */
    private void initData() {
        if (!id.equals("")) {
            dialogByProgress.show();
            Request request = new Request.Builder().url(GlobalConsts.GET_ACCOUNT_INFO_URL + id).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String bodyString = response.body().string();
                    Gson getAcountGson = new Gson();
                    GetAccountRequest getAccountRequest = getAcountGson.fromJson(bodyString, GetAccountRequest.class);
                    Message getAccountMsg = Message.obtain();
                    getAccountMsg.what = GlobalConsts.GET_ACCOUNT_INFO_HANDLER;
                    getAccountMsg.obj = getAccountRequest;
                    handler.sendMessage(getAccountMsg);
                }
            });
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        setHeadBack = (ImageView) findViewById(R.id.set_head_back);
        settingHead = (TextView) findViewById(R.id.setting_head);
        setHeadPhoto = (CircleImageView) findViewById(R.id.set_head_photo);
        genderManIv = (Button) findViewById(R.id.gender_man_iv);
        genderWomanIv = (Button) findViewById(R.id.gender_woman_iv);
        nickNameEt = (EditText) findViewById(R.id.nick_name_et); // 昵称
        nameEt = (EditText) findViewById(R.id.name_et); // 姓名
        numberPlateEt = (EditText) findViewById(R.id.number_plate_et); // 车牌
        carTypeEt = (EditText) findViewById(R.id.car_type_et); // 车型
        carColorEt = (EditText) findViewById(R.id.car_color_et); // 颜色
        editProfileSaveBtn = (Button) findViewById(R.id.edit_profile_save_btn);
    }

    /**
     * 设置监听
     */
    private void initListener() {
        setHeadBack.setOnClickListener(this);
        settingHead.setOnClickListener(this);
        setHeadPhoto.setOnClickListener(this);
        genderManIv.setOnClickListener(this);
        genderWomanIv.setOnClickListener(this);
        editProfileSaveBtn.setOnClickListener(this);
    }

    /**
     * 监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_head_back: // 返回
                if (isSave == true) {
                    dialog = new DialogByTwoButton(EditProfileActivity.this, "提示", "头像已经修改，请点击编辑按钮保存个人资料", "取消", "确定");
                    dialog.show();
                    dialog.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                        @Override
                        public void doNegative() {
                            dialog.dismiss();
                            EditProfileActivity.this.finish();
                        }

                        @Override
                        public void doPositive() {
                            UpdateAccount(); // 保存个人资料
                        }
                    });
                } else {
                    EditProfileActivity.this.finish();
                }
                break;

            case R.id.setting_head: // 设置头像
                if (isShowingPW = true) {
                    showSteHeadPW();
                }
                break;

            case R.id.camera: // 拍照
                if (!isSDCard()) break;
                if (IsCameraCanUse.isCameraCanUse() == true) {
                    takePhone(); // 拍照获取
                    destroyPopupWindow();
                } else {
                    Toast.makeText(EditProfileActivity.this, "请设置摄像头拍摄权限！", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.album: // 相册
                choosePhone(); // 相册获取
                destroyPopupWindow();
                break;

            case R.id.cancel: // 取消
                destroyPopupWindow();
                break;

            case R.id.gender_man_iv: // 选择男性
                genderString = "man";
                sex = "1";
                genderManIv.setBackgroundResource(R.mipmap.check);
                genderWomanIv.setBackgroundResource(R.mipmap.uncheck);
                break;

            case R.id.gender_woman_iv: // 选择女性
                genderString = "woman";
                sex = "0";
                genderManIv.setBackgroundResource(R.mipmap.uncheck);
                genderWomanIv.setBackgroundResource(R.mipmap.check);
                break;

            case R.id.edit_profile_save_btn: // 保存
                UpdateAccount();
                break;
        }
    }

    /**
     * 保存个人资料
     */
    private void UpdateAccount() {
        if (id.equals("")) {
            Toast.makeText(EditProfileActivity.this, "请先登录或者注册", Toast.LENGTH_SHORT).show();
            return;
        } else {
            displayName = nickNameEt.getText().toString().trim(); // 昵称
            userName = nameEt.getText().toString().trim(); // 姓名
            String carNumberString = numberPlateEt.getText().toString().trim(); // 车牌
            carNumber = carNumberString.toUpperCase(); // 小写转成大写
            carModel = carTypeEt.getText().toString().trim(); // 车型
            carColor = carColorEt.getText().toString().trim(); // 车颜色

            if (sex.equals("")) {
                sex = "1";
            }

            if (!(imageString == null)) {
                headPicture = imageString;
            } else if (imageString == null) {
                headPicture = "";
            }

            if (userName.equals("")) {
                Toast.makeText(EditProfileActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                return;
            } else if (carNumber.equals("")) {
                Toast.makeText(EditProfileActivity.this, "请输入车牌号", Toast.LENGTH_SHORT).show();
                return;
            } else if (DataConversionByShen.isCarNumberNO(carNumber) == false) {
                Toast.makeText(EditProfileActivity.this, "请输入正确的车牌号", Toast.LENGTH_SHORT).show();
                return;
            } else if (carModel.equals("")) {
                Toast.makeText(EditProfileActivity.this, "请输入车辆品牌", Toast.LENGTH_SHORT).show();
                return;
            } else if (carColor.equals("")) {
                Toast.makeText(EditProfileActivity.this, "请输入车辆颜色", Toast.LENGTH_SHORT).show();
                return;
            } else {
                dialog = new DialogByTwoButton(EditProfileActivity.this, "提示", "确认修改个人资料吗？", "取消", "确定");
                dialog.show();
                dialog.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                    @Override
                    public void doNegative() {
                        dialog.dismiss();
                    }

                    @Override
                    public void doPositive() {
                        dialog.dismiss();
                        dialogByProgress.show();
                        FormBody.Builder builder = new FormBody.Builder();
                        builder.add("id", id);
                        builder.add("enabled", String.valueOf(false));
                        builder.add("userName", userName);
                        builder.add("displayName", displayName);
                        builder.add("sex", sex);
                        builder.add("carNumber", carNumber);
                        builder.add("carModel", carModel);
                        builder.add("carColor", carColor);
                        builder.add("headPicture", headPicture);
                        FormBody body = builder.build();
                        Request request = new Request.Builder().url(GlobalConsts.UPLOAD_ACCOUNT_URL).post(body).build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String bodyString = response.body().string();
                                Gson updateAccountGson = new Gson();
                                UpdateAccountRequest updateAccountRequest = updateAccountGson.fromJson(bodyString, UpdateAccountRequest.class);
                                Message updateAccountMsg = Message.obtain();
                                updateAccountMsg.what = GlobalConsts.UPLOAD_ACCOUNT_HANDLER;
                                updateAccountMsg.obj = updateAccountRequest;
                                handler.sendMessage(updateAccountMsg);
                            }
                        });
                    }
                });
            }
        }
    }

    /**
     * 设置头像的pw
     */
    private void showSteHeadPW() {
        View popupView = View.inflate(EditProfileActivity.this, R.layout.setheadpop, null);
        camera = (Button) popupView.findViewById(R.id.camera);
        album = (Button) popupView.findViewById(R.id.album);
        cancel = (Button) popupView.findViewById(R.id.cancel);

        camera.setOnClickListener(this);
        album.setOnClickListener(this);
        cancel.setOnClickListener(this);

        setHeadPW = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeadPW.setFocusable(false);
        setHeadPW.setOutsideTouchable(true);
        setHeadPW.setAnimationStyle(android.R.style.Animation_InputMethod);
        setHeadPW.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    private boolean isSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            return true;
        } else {
            Toast.makeText(EditProfileActivity.this, "SD卡不存在", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 检查拍照权限
     */
    private void takePhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            takePhoto();
        }
    }

    /**
     * 使用拍照设置头像
     */
    private void takePhoto() {
        imgFile = new File(destDir, getPhotoFileName());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        startActivityForResult(intent, CROP_PHOTO);
    }

    /**
     * 设置拍照后的照片格式
     *
     * @return
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh：mm：ss.SSS");
        return sdf.format(date) + ".jpg";
    }

    /**
     * 检查访问相册权限
     */
    public void choosePhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            choosePhoto();
        }
    }

    /**
     * 从相册选取图片
     */
    public void choosePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * Intent请求回调函数
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (imgFile.exists()) {
                    startPhotoZoom(Uri.fromFile(imgFile), 300);
                }
                break;

            /**
             * 拍照的请求标志
             */
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    startPhotoZoom(Uri.fromFile(imgFile), 300);
                }
                break;

            /**
             * 从相册中选取图片的请求标志
             */
            case REQUEST_CODE_PICK_IMAGE:
                if (data != null) {
                    try {
                        startPhotoZoom(data.getData(), 300);
                    } catch (Exception e) {
                        Toast.makeText(this, "请检查网络或稍后重试", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            /**
             * 拍照或相册选择照片后的处理
             */
            case GlobalConsts.UPLOAD_IMAGE_REQUEST:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    final Bitmap bmp = bundle.getParcelable("data");
                    if (bmp != null) {
                        saveCropPic(bmp);
                        setHeadPhoto.setImageBitmap(bmp);
                    }
                }
                break;
        }
    }

    /**
     * 图片进行裁剪
     */
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, GlobalConsts.UPLOAD_IMAGE_REQUEST);
    }

    /**
     * 把裁剪后的图片保存到sdcard上
     *
     * @param bmp
     */
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        Bitmap b = ImageUtils.imageZoom(bmp);
        b.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(imgFile);
            fis.write(baos.toByteArray());
            fis.flush();
            /**
             * 将图片文件上传服务
             */
            upImgFile(imgFile);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将图片上传至服务器
     *
     * @param imgFile
     */
    private void upImgFile(File imgFile) {
        dialogByProgress.show();
        String BOUNDARY = UUID.randomUUID().toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), imgFile);
        MultipartBody multipartBody = new MultipartBody.Builder(BOUNDARY)
                .setType(MultipartBody.FORM)
                .addFormDataPart("", imgFile.getName(), body)
                .build();

        Request request = new Request.Builder().url(GlobalConsts.UPLOAD_IMAGE_URL).post(multipartBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson uploadGson = new Gson();
                UploadImageRequest uploadRequest = uploadGson.fromJson(bodyString, UploadImageRequest.class);
                Message uploadMsg = Message.obtain();
                uploadMsg.what = GlobalConsts.UPLOAD_IMAGE_HANDLER;
                uploadMsg.obj = uploadRequest;
                handler.sendMessage(uploadMsg);
            }
        });
    }

    /**
     * 判断手机权限并获取拍照和访问相册权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto(); // 拍摄
            } else {
                Toast.makeText(EditProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto(); // 访问相册
            } else {
                Toast.makeText(EditProfileActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 销毁弹窗
     */
    private void destroyPopupWindow() {
        setHeadPW.dismiss();
        isShowingPW = true;
    }
}
