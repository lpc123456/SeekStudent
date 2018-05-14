package cn.com.lcxy.cardanimation.sqdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 85229 on 2018/5/4.
 */

public class StudentHelper extends SQLiteOpenHelper {

    public StudentHelper(Context context) {
        super(context, "data.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建该数据库的同时新建一个info表，表中有_id,name这两个字段
        db.execSQL("create table student (_id varchar(20) primary key, name varchar(20),className varchar(20)," +
                "collegeName varchar(20),score Integer,commitTime varchar(40),count Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
