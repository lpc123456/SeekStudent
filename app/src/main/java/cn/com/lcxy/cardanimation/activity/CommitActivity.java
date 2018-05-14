package cn.com.lcxy.cardanimation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.lcxy.cardanimation.R;
import cn.com.lcxy.cardanimation.adapter.SoundClick;
import cn.com.lcxy.cardanimation.bean.Student;
import cn.com.lcxy.cardanimation.sqdb.StudentDao;

public class CommitActivity extends AppCompatActivity {
    private TextView tv_college,tv_class,tv_name,tv_studentId,tv_score,tv_dateTime;
    private Spinner sp_score;
    private Button btn_commit,btn_reset,btn_backMain;
    private List<String> mSpList;//Spinner数据源
    private SoundClick mBtn_Sound_commit1,mBtn_Sound_commit2,mBtn_Sound_reset,mBtn_Sound_backMain;
    private Student student;//提交的学生对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
        Intent intent=getIntent();
        student= (Student) intent.getSerializableExtra("student");
        initView();
        initData();
    }

    private void initView() {
        btn_commit=findViewById(R.id.btn_commit);
        btn_reset=findViewById(R.id.btn_reset);
        btn_backMain=findViewById(R.id.btn_backMain);
        tv_college=findViewById(R.id.tv_college);
        tv_class=findViewById(R.id.tv_class);
        tv_name=findViewById(R.id.tv_name);
        tv_studentId=findViewById(R.id.tv_studentId);
        tv_score=findViewById(R.id.tv_score);
        tv_dateTime=findViewById(R.id.tv_dateTime);
        sp_score=findViewById(R.id.sp_score);
        initListener();
    }

    private void initListener() {
        mBtn_Sound_reset=new SoundClick(getApplicationContext(),R.raw.btn_sure) {
            @Override
            public void setClick(View view) {
                mBtn_Sound_reset.play();
                //返回主页重置

                MainActivity.mMainActivity.finish();
                backMain();
            }
        };
        mBtn_Sound_backMain=new SoundClick(CommitActivity.this,R.raw.btn_sure) {
            @Override
            public void setClick(View view) {
                mBtn_Sound_backMain.play();
                //返回主页
                backMain();
            }
        };
        mBtn_Sound_commit1=new SoundClick(CommitActivity.this,R.raw.notication_success) {
            @Override
            public void setClick(View view) {
                mBtn_Sound_commit1.play();
                StudentDao dao=new StudentDao(CommitActivity.this);
                int start=student.getScore();
                int current=Integer.parseInt(String.valueOf(sp_score.getSelectedItem()));
                student.setScore(start+current);
                student.setCount(student.getCount()+1);
                dao.update(student);
                String show="";
                if(current>=4){
                    show="表现不错，加油！";
                }else {
                    show="大佬也会有失误的时候加油！";
                }
                Toast.makeText(getApplicationContext(), "提交成功！"+show, Toast.LENGTH_SHORT).show();
                //返回主页重置
                MainActivity.mMainActivity.finish();
                backMain();
            }
        };

        btn_commit.setOnClickListener(mBtn_Sound_commit1);
        btn_reset.setOnClickListener(mBtn_Sound_reset);
        btn_backMain.setOnClickListener(mBtn_Sound_backMain);
    }

    private void initData() {
        initList();
        updateView();
        ArrayAdapter adap = new ArrayAdapter<String>(this, R.layout.spinner_item, mSpList);
        adap.setDropDownViewResource(R.layout.item_dialogspinselect);
        sp_score.setAdapter(adap);

    }

    private void initList() {
        mSpList=new ArrayList<>();
        for (int i=0;i<6;i++){
            mSpList.add(String.valueOf(i));
        }

    }
    public void updateView(){
        tv_name.setText(student.getName()+"");
        tv_score.setText(student.getScore()+"");
        tv_class.setText(student.getClassName()+"");
        tv_college.setText(student.getCollegeName()+"");
        tv_studentId.setText(student.getId()+"");
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        student.setCommitTime(format.format(new Date()));
        tv_dateTime.setText(format.format(new Date()));
    }
    /**
     * 返回主界面并再次抽取
     */
    private void backMain(){
        Intent intent=new Intent(CommitActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * 提交结果
     */
    public void requestPost(){

    }
}
