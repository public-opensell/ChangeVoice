package com.androiddemoax.bianshengboygirl;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.



    private Button button_one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Example of a call to a native method
//        TextView tv = binding.sampleText;
//        tv.setText(stringFromJNI());
//        FMOD.init(this)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_one=findViewById(R.id.button_one);
        button_one.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                /**
                 *  按钮跳转到另一个界面
                 */
                //Toast.makeText(MainActivity.this,"正在跳转中", Toast.LENGTH_LONG).show();
                startFloatingButtonService(view);


            }
        });
    }




    @SuppressLint("MissingSuperCall")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode == 0) {
            Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this, FloatsideSing.class);
                MainActivity.this.startService(intent);

//                startService(new Intent(MainActivity.this, FloatsideSing.class));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startFloatingButtonService(View view) {
        if (FloatsideSing.isStarted) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
            return;
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        } else {
            startService(new Intent(MainActivity.this, FloatsideSing.class));
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

//    public native String stringFromJNI();
}