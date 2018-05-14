package cn.com.lcxy.cardanimation.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import cn.com.lcxy.cardanimation.R;
import cn.com.lcxy.cardanimation.bean.Prize;
import cn.com.lcxy.cardanimation.bean.Student;
import cn.com.lcxy.cardanimation.utils.SeekStudentUtils;
import cn.com.lcxy.cardanimation.view.LotteryView;

/**
 * 九宫格方式抽取
 */
public class Main2Activity extends AppCompatActivity {

    private List<String> listUrl;
    private List<Student> studentList;
    private List<Prize> list;
    private List<Bitmap> listBitmap;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                lotteryView.setPrizes(list);
                lotteryView.surfaceCreated(lotteryView.getHolder());
            }
        }
    };
    private LotteryView lotteryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        ToastUtils.getInstance().initToast(this);

        lotteryView = findViewById(R.id.lotteryView);
//        lottery_et = findViewById(R.id.lottery_et);

        listUrl = new ArrayList<>();
        list = new ArrayList<>();
        listBitmap = new ArrayList<>();

        listUrl.add("https://souget.oss-cn-hangzhou.aliyuncs.com/img/farm/lo_muwu@3x.png");
        listUrl.add("https://souget.oss-cn-hangzhou.aliyuncs.com/img/farm/lo_thanks@3x.png");
        listUrl.add("https://souget.oss-cn-hangzhou.aliyuncs.com/img/farm/lo_20nl@3x.png");

        listUrl.add("https://souget.oss-cn-hangzhou.aliyuncs.com/img/farm/lo_shuwa@3x.png");
        listUrl.add("https://souget.oss-cn-hangzhou.aliyuncs.com/img/farm/lo_chouyici@3x.png");
        listUrl.add("https://souget.oss-cn-hangzhou.aliyuncs.com/img/farm/lo_xiyi@3x.png");

        listUrl.add("https://souget.oss-cn-hangzhou.aliyuncs.com/img/farm/lo_10nl@3x.png");
        listUrl.add("https://souget.oss-cn-hangzhou.aliyuncs.com/img/farm/lo_thanks@3x.png");
        listUrl.add("https://souget.oss-cn-hangzhou.aliyuncs.com/img/farm/lo_500nl@3x.png");
        studentList= SeekStudentUtils.getStudents(Main2Activity.this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 9; i++) {
                    Prize lottery = new Prize();
//                    lottery.setName("奖品下标：" + i);
                    lottery.setName(studentList.get(i).getName());
                    lottery.setId(i + 1);
                    lottery.setStudent(studentList.get(i));
                    lottery.setBgColor(BitmapFactory.decodeResource(getResources(), R.mipmap.bg));
                    Bitmap bitmap = null;
                    try {
                        bitmap = Glide.with(Main2Activity.this).asBitmap().load(listUrl.get(i)).submit().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    lottery.setIcon(bitmap);
                    list.add(lottery);
                }
                handler.sendEmptyMessage(1);
            }
        }).start();

        lotteryView.setOnStartClickListener(new LotteryView.OnStartClickListener() {
            @Override
            public int onStartClick() {
//                String number = lottery_et.getText().toString();
                Random random=new Random();
                int number=random.nextInt(9);
                while (number==4){
                    number=random.nextInt(9);
                }
                return number;
            }
        });

        lotteryView.setOnTransferWinningListener(new LotteryView.OnTransferWinningListener() {
            @Override
            public void onWinning(int position) {
//                ToastUtils.onSuccessShowToast(list.get(position).getName());
                final Student student=list.get(position).getStudent();
                Toast.makeText(Main2Activity.this, list.get(position).getName(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog=new AlertDialog.Builder(Main2Activity.this);
                dialog.setTitle("结果");
                dialog.setMessage(student.getClassName()+"\n"+student.getId()+"\n"+student.getName());
                dialog.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(Main2Activity.this,CommitActivity.class);
                        intent.putExtra("student",student);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

    }
}
