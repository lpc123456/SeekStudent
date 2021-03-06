package cn.com.lcxy.cardanimation.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import cn.com.lcxy.cardanimation.R;
import cn.com.lcxy.cardanimation.adapter.SoundClick;
import cn.com.lcxy.cardanimation.bean.Student;
import cn.com.lcxy.cardanimation.utils.SeekStudentUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private FrameLayout mCardMainContainer;
    private LinearLayout mCardFontContainer, mCardBackContainer;

    private Button btn_manual,btn_setting,btn_result;
    private TextView tv_help,tv_class,tv_name,tv_score,tv_college,tv_studentId;

    private AnimatorSet mRightOutAnimatorSet, mLeftInAnimatorSet;

    private boolean mIsShowBack = false;  //是否显示背面

    private SoundClick mCardSClick;//点击卡片时

    private SoundClick mBtnSClick1,mBtnSClick2,mBtnSClick3;  //点击按钮时

    private int[] soundRes;

    private int randomId;//随机选择声音

    private Random random;

    private Student mStudent;//抽中的对象

    public static AppCompatActivity mMainActivity;

    private int countCard;//旋转次数标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mCardMainContainer = (FrameLayout) findViewById(R.id.card_main_container);
        mCardFontContainer = (LinearLayout) findViewById(R.id.card_font_container);
        mCardBackContainer = (LinearLayout) findViewById(R.id.card_back_container);
        btn_result=findViewById(R.id.btn_result);
        btn_manual=findViewById(R.id.btn_manual);
        btn_setting=findViewById(R.id.btn_setting);
        tv_help=findViewById(R.id.tv_help);
        tv_class=findViewById(R.id.tv_class);
        tv_name=findViewById(R.id.tv_name);
        tv_score=findViewById(R.id.tv_score);
        tv_college=findViewById(R.id.tv_college);
        tv_studentId=findViewById(R.id.tv_studentId);
        setAnimators(); // 设置动画
        setCameraDistance(); // 设置镜头距离
    }

    private void initData() {
        random=new Random();
        soundRes=new int[]{R.raw.card_logozhenhan,R.raw.card_zhuanchang,R.raw.card_jiezongdashi,R.raw.card_air,R.raw.card_hero
                ,R.raw.card_jiantou,R.raw.card_liuyun,R.raw.card_world
        };
        randomId=random.nextInt(soundRes.length);
        mCardSClick=new SoundClick(getApplicationContext(),soundRes[randomId]) {
            @Override
            public void setClick(View view) {
                MainActivity.this.onClick(view);
            }
        };
        mBtnSClick1=new SoundClick(getApplicationContext(),R.raw.btn_sure) {
            @Override
            public void setClick(View view) {
                MainActivity.this.onClick(view);
            }
        };
        mBtnSClick2=new SoundClick(getApplicationContext(),R.raw.btn_sure) {
            @Override
            public void setClick(View view) {
                MainActivity.this.onClick(view);
            }
        };
        mBtnSClick3=new SoundClick(getApplicationContext(),R.raw.btn_gaokeji) {
            @Override
            public void setClick(View view) {
                MainActivity.this.onClick(view);
            }
        };
    }
    private void initEvent() {
        mCardMainContainer.setOnClickListener(mCardSClick);
        btn_setting.setOnClickListener(mBtnSClick1);
        btn_manual.setOnClickListener(mBtnSClick2);
        btn_result.setOnClickListener(mBtnSClick3);
        tv_help.setOnClickListener(this);
    }

    private void setAnimators() {
        mRightOutAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_right_out);
        mLeftInAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_left_in);

        // 设置点击事件
        mRightOutAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCardMainContainer.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btn_result.setVisibility(View.VISIBLE);
            }
        }
        );
        mLeftInAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCardMainContainer.setClickable(true);
            }
        });
    }

    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFontContainer.setCameraDistance(scale);
        mCardBackContainer.setCameraDistance(scale);
    }

    private void flipCard() {
        if (!mIsShowBack) {  // 正面朝上
            mCardSClick.play();
            mStudent=SeekStudentUtils.getStudent(MainActivity.this);
            updateView(mStudent);
            mRightOutAnimatorSet.setTarget(mCardFontContainer);
            mLeftInAnimatorSet.setTarget(mCardBackContainer);
//            mLeftInAnimatorSet.setDuration(mTimeAnimation);
            mRightOutAnimatorSet.start();
//            mLeftInAnimatorSet.setDuration(mTimeAnimation);
            mLeftInAnimatorSet.start();
            mIsShowBack = true;
        } else { // 背面朝上
            //不执行任何操作
            return;
//            mRightOutAnimatorSet.setTarget(mCardBackContainer);
//            mLeftInAnimatorSet.setTarget(mCardFontContainer);
//            mRightOutAnimatorSet.start();
//            mLeftInAnimatorSet.start();
//            mIsShowBack = false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_main_container:
                flipCard();
                break;
            case R.id.btn_manual://手动点名
                mBtnSClick1.play();
                Intent intent2=new Intent(MainActivity.this,ManualActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_setting://班级设置
                mBtnSClick2.play();
                Toast.makeText(MainActivity.this, "功能未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_help://使用帮助
                Intent intent=new Intent(this,FlashActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_result://提交按钮
                mBtnSClick3.play();
                Intent intent1=new Intent(this,CommitActivity.class);
                intent1.putExtra("student",mStudent);
                mMainActivity=MainActivity.this;
                startActivity(intent1);
                break;
        }
    }
    public void updateView(Student student){
        tv_name.setText(student.getName()+"");
        tv_score.setText(student.getScore()+"");
        tv_class.setText(student.getClassName()+"");
        tv_college.setText(student.getCollegeName()+"");
        tv_studentId.setText(student.getId()+"");

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: .........");
        mBtnSClick1.release();
        mCardSClick.release();
        mBtnSClick2.release();
        mBtnSClick3.release();
    }
}
