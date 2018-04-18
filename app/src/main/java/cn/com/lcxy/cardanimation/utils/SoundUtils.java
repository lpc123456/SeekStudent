package cn.com.lcxy.cardanimation.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by 85229 on 2018/4/18.
 */

public class SoundUtils {
    /**
     * 播放音效
     */
    public static void startSound(Context context,int res){
        //创建MediaPlayer ,设置数据源
        MediaPlayer mediaPlayer=MediaPlayer.create(context,res);
        //设置声音流的类型
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
        try {
            mediaPlayer.prepare();
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(context, res);
            }
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }
}
