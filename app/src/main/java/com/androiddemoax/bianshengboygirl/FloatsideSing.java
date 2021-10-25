package com.androiddemoax.bianshengboygirl;


import android.app.Service;
import android.content.Intent;

import android.graphics.PixelFormat;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;

import android.os.Environment;


import android.os.IBinder;

import android.provider.Settings;


import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;


public class FloatsideSing extends Service {
    static {
        System.loadLibrary("changeVoice");
    }
    // 录音功能相关
    //private final static String LPATH="file:///android_asset/pioxy.mp3";
    public static boolean isStarted = false;
    private Button btn_control;
    private Button btn_boy;
    private Button btn_girl;
    private Button btn_play;
    private Changevoices mQqFixUtile;


    private boolean isStart = false;
    private MediaRecorder mr = null;
    private MediaPlayer mediaPlayer;
    private boolean mIsPlaying = false;
    private  File soundFile;

    private File dir;




    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;





    private void startRecord(){
        if(mr == null){
            dir = new File(Environment.getExternalStorageDirectory(),"Aboutsounds");
            if(!dir.exists()){
                dir.mkdirs();
            }
            soundFile = new File(dir,System.currentTimeMillis()+".mp3");
            if(!soundFile.exists()){
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);  //音频输入源
            mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);   //设置输出格式
            mr.setAudioEncodingBitRate(96000);
            mr.setAudioSamplingRate(48000);//44100
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);   //设置编码格式
            mr.setOutputFile(soundFile.getAbsolutePath());
            try {
                mr.prepare();
                mr.start();  //开始录制
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void stopRecord(){
        if(mr != null){
            mr.stop();
            mr.release();
            mr = null;
        }
    }

    private void selecteDeleteVoice(){//删除上一条音频

       File[] files= soundFile.getParentFile().listFiles();

        for(int i=0;i<=files.length;i++){

            if(files.length>=50){
                soundFile.getParentFile().delete();
            }



        }

    }


    private void doPlay(File audioFile) {
        try {
            //配置播放器 MediaPlayer
            mediaPlayer = new MediaPlayer();
            //设置声音文件
            mediaPlayer.setDataSource(audioFile.getAbsolutePath());
            //配置音量,中等音量
            mediaPlayer.setVolume(1,1);

            //播放是否循环
            mediaPlayer.setLooping(false);

            //设置监听回调 播放完毕
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    stopPlayer();
                    Toast.makeText(FloatsideSing.this,"播放失败",Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            //设置播放
            mediaPlayer.prepare();
            mediaPlayer.start();

            //异常处理，防止闪退

        } catch (Exception e) {
            e.printStackTrace();
            stopPlayer();
        }


    }

    private void stopPlayer(){
        mIsPlaying=false;
        mediaPlayer.release();
        mediaPlayer=null;
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
        org.fmod.FMOD.close();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        org.fmod.FMOD.init(this);
        mQqFixUtile = new Changevoices();
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = 700;
        layoutParams.height = 180;
        layoutParams.x = 0;
        layoutParams.y = 0;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
//            button = new Button(getApplicationContext());
//            button.setText("原声");

            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View displayView = layoutInflater.inflate(R.layout.change_layout, null);


            btn_control = displayView.findViewById(R.id.m_btn_normal);

            btn_control.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    // 开始录音
                    if(!isStart){
                        startRecord();
                        btn_control.setText("停止录音");

                        isStart = true;
                    }else{
                        stopRecord();
                        btn_control.setText("开始录音");
                        isStart = false;

                    }

                }
            });


            btn_play=displayView.findViewById(R.id.m_btn_play);

            btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   try{
                       doPlay(soundFile);
                       //soundFile.delete();
                      //selecteDeleteVoice();
                       //Changevoices.fixVoice(0,LPATH);


                   }
                   catch(Exception e) {
                       e.printStackTrace();
                       //stopPlayer();
                   }
                    //Changevoices.fixVoice(0,LPATH);

                    //



                }
            });



            btn_boy=displayView.findViewById(R.id.m_btn_boy);
            btn_boy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FloatsideSing.this,"播放文件已删除",Toast.LENGTH_SHORT).show();
                    try {

                        String singToken=soundFile.getAbsolutePath();



                        Changevoices.fixVoice(2,singToken);

                        //soundFile.delete();
                        //selecteDeleteVoice();
                    }catch (Exception e) {
                        e.printStackTrace();
                        //stopPlayer();
                    }


                }


            });

            btn_girl=displayView.findViewById(R.id.m_btn_girl);
            btn_girl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FloatsideSing.this,"播放文件已删除",Toast.LENGTH_SHORT).show();
                    try {

                        String singToken=soundFile.getAbsolutePath();

                       

                        Changevoices.fixVoice(1,singToken);

                        //soundFile.delete();

                       // selecteDeleteVoice();
                    }catch (Exception e) {
                        e.printStackTrace();
                        //stopPlayer();
                    }


                }


            });




            //imageView.setImageResource(images[imageIndex]);

            windowManager.addView(displayView, layoutParams);
            displayView.setOnTouchListener(new FloatingOnTouchListener());



        }

    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }



}
