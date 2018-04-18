package cn.com.lcxy.cardanimation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.com.lcxy.cardanimation.R;
import cn.com.lcxy.cardanimation.adapter.SoundClick;

public class FlashActivity extends AppCompatActivity {
    private TextView tv_description;
    private Button btn_start;
    private SoundClick mSClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        initView();
        initData();

    }

    private void initView() {
        tv_description=findViewById(R.id.tv_description);
        btn_start=findViewById(R.id.btn_start);

    }

    private void initData() {
        String desc="点击中间卡牌随机抽童鞋回答问题" +
                "，回答完成后点击成绩记录按钮，进入提交" +
                "界面，对回答问题结果进行提交。";
        tv_description.setText(desc);
        mSClick=new SoundClick(getApplicationContext(),R.raw.btn_gaokeji) {
            @Override
            public void setClick(View view) {
                mSClick.play();
                Intent intent=new Intent(FlashActivity.this,MainActivity.class);
                startActivity(intent);
                FlashActivity.this.finish();
            }
        };
        btn_start.setOnClickListener(mSClick);
    }
}
