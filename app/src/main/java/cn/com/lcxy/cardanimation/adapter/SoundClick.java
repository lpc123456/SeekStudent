package cn.com.lcxy.cardanimation.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.View;

import cn.com.lcxy.cardanimation.R;

/**
 * 音效帮助类
 * Created by 85229 on 2018/4/17.
 */

public abstract class SoundClick implements View.OnClickListener {
    private SoundPool soundPool;    //声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID
    private Context context;
    private MediaPlayer player;

    public SoundClick(Context context,int res) {
        soundPool=new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        this.context=context;
        this.music = soundPool.load(context, res,1);
        player=new MediaPlayer();
    }

    @Override
    public void onClick(View view) {
        setClick(view);
    }

    public abstract void setClick(View view);

    public void play(){
        soundPool.play(music, 1, 1, 0, 0, 1);
    }

    //设置资源
    public void setMusic(int res) {
        this.music = soundPool.load(context, res,1);
    }
}
