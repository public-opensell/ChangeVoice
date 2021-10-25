package com.androiddemoax.bianshengboygirl;

import android.util.Log;

import java.io.File;

public class Changevoices {



    public static final int BAIDU_BOY = 19;

    public static final int BAIDU_GIRL = 18;

    public static final int BAIDU_MENGMEI = 20;

    public static final int BAIDU_XIAOGEGE = 21;

    public static final int MODE_BOY = 8;

    public static final int MODE_CHORUS = 15;

    public static final int MODE_CLASSROOM = 10;

    public static final int MODE_CUSTOM = 9;

    public static final int MODE_DASHU = 2;

    public static final int MODE_DISTORTION = 16;

    public static final int MODE_GAOGUAI = 4;

    public static final int MODE_HALL = 7;

    public static final int MODE_JINGSONG = 3;

    public static final int MODE_KONGLING = 5;

    public static final int MODE_LIVEPERFORMANCE = 12;

    public static final int MODE_LUOLI = 1;

    public static final int MODE_MACHINE = 17;

    public static final int MODE_MINIONS = 13;

    public static final int MODE_NORMAL = 0;

    public static final int MODE_NVSHENG = 11;

    public static final int MODE_SLOWLY = 14;

    public static final int MODE_VALLEY = 6;
    public boolean playing = false;
    static {
        System.loadLibrary("fmod");
        System.loadLibrary("fmodL");
        System.loadLibrary("changeVoice");
    }

    /**
     * 包房声音
     * @param path 声音路径
     * @param type 播放类型
     */
    public static native void fixVoice(int type, String path);

    /**
     * 专门提供给JNI使用
     * @param flag
     */
    private void setPlaying(boolean flag){
        Log.d("yanjin","播放状态-"+flag);
        playing = flag;
    }

    /**
     * 用来判断是否正在播放，如果是就不能再播放
     * @return
     */
    public boolean isPlaying() {
        return playing;
    }
}
