package cn.com.lcxy.cardanimation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import cn.com.lcxy.cardanimation.R;
import cn.com.lcxy.cardanimation.utils.SoundUtils;

public class CommitActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_college,tv_class,tv_name,tv_studentId,tv_score,tv_dateTime;
    private Spinner sp_score;
    private Button btn_commit,btn_cancel,btn_backMain;
    private List<String> mSpList;//Spinner数据源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
        initView();
        initData();
    }

    private void initView() {
        btn_commit=findViewById(R.id.btn_commit);
        btn_cancel=findViewById(R.id.btn_cancel);

    }

    private void initData() {
        btn_commit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        SoundUtils.startSound(CommitActivity.this,R.raw.notication_success);
        switch (view.getId()){

        }
    }
}
