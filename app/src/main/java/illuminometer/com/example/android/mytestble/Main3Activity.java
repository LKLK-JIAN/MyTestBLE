package illuminometer.com.example.android.mytestble;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    private Button camerabutton;
    private ImageView photoimage;
    private Button albumbutton;
    String photopath;        //自定义图片存储路劲
    Uri photouri;         //图片存储url
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        camerabutton=(Button) findViewById(R.id.camerabutton);
        camerabutton.setOnClickListener(this);
        photoimage=(ImageView) findViewById(R.id.photoimage);
        albumbutton=(Button) findViewById(R.id.albumbutton);
        albumbutton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            switch (requestCode) {
                case 0:
                        setImageBitmap();
                        galleryAddPic();
                        break;
                        case 1:
                            //data中自带有返回的uri
                            photouri = data.getData();
                            //获取照片路径
                            String[] filePathColumn = {MediaStore.Audio.Media.DATA};
                            Cursor cursor = getContentResolver().query(photouri, filePathColumn, null, null, null);
                            cursor.moveToFirst();
                            photopath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
                            cursor.close();
                            //有了照片路径，之后就是压缩图片，和之前没有什么区别
                            setImageBitmap();
                            break;
                    }
                    //    Bundle bundle=data.getExtras();
                    Log.e("TAG", "onActivityResult:7777777777777777 ");

//            Bitmap bitmap=(Bitmap)bundle.get("data");
                    //photoimage.setImageBitmap(bitmap);
            }
        }

    private void galleryAddPic(){
        Intent mediaScanIntent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(photouri);
        this.sendBroadcast(mediaScanIntent);
    }

    //自定义图片名，获取照片的file
    public File createImageFile(){
        //文件名称
        String fileName="Img_"+new SimpleDateFormat("yyyymmdd").format(new java.util.Date())+".jpg";
        File dir;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            dir=Environment.getExternalStorageDirectory();
        }else{
            dir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        File file=new File(dir,fileName);
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        photopath=file.getAbsolutePath();
        return file;
    }
    /**
     * 压缩图片
     */
    private void setImageBitmap(){
        //获取imageview的宽和高
        int targetWidth=photoimage.getWidth();
        int targetHeight=photoimage.getHeight();

        //根据图片路径，获取bitmap的宽和高
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(photopath,options);
        int photoWidth=options.outWidth;
        int photoHeight=options.outHeight;

        //获取缩放比例
        int inSampleSize=1;
        if(photoWidth>targetWidth||photoHeight>targetHeight){
            int widthRatio=Math.round((float)photoWidth/targetWidth);
            int heightRatio=Math.round((float)photoHeight/targetHeight);
            inSampleSize=Math.min(widthRatio,heightRatio);
        }

        //使用现在的options获取Bitmap
        options.inSampleSize=inSampleSize;
        options.inJustDecodeBounds=false;
        Bitmap bitmap=BitmapFactory.decodeFile(photopath,options);
        photoimage.setImageBitmap(bitmap);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camerabutton:
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photouri= Uri.fromFile(createImageFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photouri);
                startActivityForResult(intent,0);
                break;
            case R.id.albumbutton:
                Intent intent1=new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1,1);
        }
    }
}
