package cn.com.lcxy.cardanimation.sqdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.lcxy.cardanimation.bean.Student;

/**
 * Created by 85229 on 2018/5/4.
 */

public class StudentDao {
    private static StudentHelper helper;
    private SQLiteDatabase database;

    public StudentDao(Context context){
        helper=new StudentHelper(context);
    }
    //判断数据库是否为空
    public boolean isEmpty(){
        database=helper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from student",new String[]{});
        if (cursor==null||cursor.getCount()<1){
            //数据库为空
            cursor.close();
            database.close();
            return true;
        }else {
            cursor.close();
            database.close();
            return false;
        }
    }
    //设置数据库的数据
    public void setData(List<Student> values){

        String sql = "INSERT INTO student (_id, name,className,collegeName,count) VALUES (?,?,?,?,?)";
        database=helper.getWritableDatabase();
        database.beginTransaction();

        SQLiteStatement stmt = database.compileStatement(sql);
        for (int i = 0; i < values.size(); i++) {
            //index 为1开始索引，value为入库的值
            //bingXXX为插入XXX类型
            stmt.bindString(1,values.get(i).getId());
            stmt.bindString(2, values.get(i).getName());
            stmt.bindString(3, values.get(i).getClassName());
            stmt.bindString(4, values.get(i).getCollegeName());
            stmt.bindLong(5, values.get(i).getCount());
            stmt.execute();
            stmt.clearBindings();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }
    //得到所有的数据
    public List<Student> queryAllStudent(){
        List<Student> students=new ArrayList<>();
        database=helper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from student",new String[]{});
//        System.out.println("cursor.size:"+cursor.getCount());
        while (cursor.moveToNext()){
            Student student=new Student();
            student.setId(cursor.getString(0));
            student.setName(cursor.getString(1));
            student.setClassName(cursor.getString(2));
            student.setCollegeName(cursor.getString(3));
            student.setScore(cursor.getInt(4));
            student.setCount(cursor.getInt(6));
            students.add(student);
        }
//        System.out.println("]]]]]"+students);
        cursor.close();
        database.close();
        return students;
    }

    /**
     * 拿到抽取次数
     * @return
     */
    public Map<Integer,Integer> getStudentCount(){
        Map<Integer,Integer> values=new HashMap<>();
        database=helper.getReadableDatabase();
//        Cursor cursor=database.query("student",new String[]{"count(*)","count"},
//                null,null,"count",null,null);
        Cursor cursor=database.rawQuery("select count(*),count from student group by count",new String[]{});
        while (cursor.moveToNext()){
            values.put(cursor.getInt(1),cursor.getInt(0));
        }
        cursor.close();
        database.close();
        return values;
    }
    public int getMinCount(){
        database=helper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select min(count) from student",new String[]{});
        cursor.moveToNext();
        int min=cursor.getInt(0);
        cursor.close();
        database.close();
        return min;
    }
    //更新数据库
    public void update(Student student){
        database=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("count",student.getCount());
        values.put("score",student.getScore());
        values.put("commitTime",student.getCount());
        database.update("student",values,"_id=?",new String[]{student.getId()});
        database.close();
    }
}
