package cn.com.lcxy.cardanimation.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.lcxy.cardanimation.R;
import cn.com.lcxy.cardanimation.bean.Student;
import cn.com.lcxy.cardanimation.sqdb.StudentDao;
import cn.com.lcxy.cardanimation.sqdb.StudentHelper;

/**
 * Created by 85229 on 2018/5/4.
 */

public class SeekStudentUtils {
    private static List<Student> students;
    private static StudentDao dao;
    public static Student getStudent(Context context){
        dao=new StudentDao(context);
        if (dao.isEmpty()){
            dao.setData(initStudent(context));
        }
        Map<Integer,Integer> map=dao.getStudentCount();
        students=dao.queryAllStudent();
        int min=dao.getMinCount();
        Map<Integer,Double> searchMaps= LPCMethod.getProb(map,min,students.size());
        for (Integer key:searchMaps.keySet()) {
            System.out.println("searchMaps概率为"+key+":"+searchMaps.get(key));
        }
        Student student=aliasStudent(students,searchMaps);
        System.out.println("选中你了"+student.toString());
        return student;
    }
    public static List<Student> initStudent(Context context){
        List<Student> students=new ArrayList<>();
        //读取data.json文件
        InputStream is = context.getResources().openRawResource(R.raw.data);
        try {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String json = new String(buffer, "utf-8");
            JSONArray jsonArray=new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++){
                JSONArray arr=jsonArray.getJSONArray(i);
                students.add(new Student(arr.getString(0),arr.getString(1),arr.getString(2)
                ,"信息学院",0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * alias算法抽取
     * @param students  拿到所有数据
     * @param searchMap key为已抽取的次数，value为此此数的概率
     * @return
     */
    public static Student aliasStudent(List<Student> students,Map<Integer,Double> searchMap){
        List<Double> prob = new ArrayList<Double>();    //对应students集合的概率集合
        Map<String,Student> resultMap=new HashMap<>();
        //prob.add(0.25); /* 0.01% 几率命中 */
        //prob.add(0.25); /* 50% 几率命中 */
        //prob.add(0.25); /* 50% 几率命中 */
//        prob.add(0.001);//一等奖
//        prob.add(0.05);//二等奖
//        prob.add(0.1);//三等奖
//        prob.add(0.3);
//        prob.add(0.549);
        String []str=new String[students.size()];
        for (int i=0;i<students.size();i++){
            Student student=students.get(i);
            System.out.println("概率:"+searchMap.get(student.getCount()));
            prob.add(searchMap.get(student.getCount()));
            str[i]=student.getId();
            resultMap.put(student.getId(),student);
        }
//        String[] str={"一等奖","二等奖","三等奖","四等奖","未中奖"};
//        int[] test={0,0,0,0};
        AliasMethod am = new AliasMethod(prob);
//        for(int i=0;i<100;i++){
//            System.out.println(str[am.next()]);
//        }
        Student result=resultMap.get(str[am.next()]);
        return result;
    }
    public static List<Student> getStudents(Context context){
        dao=new StudentDao(context);
        if (dao.isEmpty()){
            dao.setData(initStudent(context));
        }
        Map<Integer,Integer> map=dao.getStudentCount();
        students=dao.queryAllStudent();
        int min=dao.getMinCount();
        Map<Integer,Double> searchMaps= LPCMethod.getProb(map,min,students.size());
        for (Integer key:searchMaps.keySet()) {
            System.out.println("searchMaps概率为"+key+":"+searchMaps.get(key));
        }
        List<Student> studentList=aliasStudents(students,searchMaps);
        return studentList;
    }
    /**
     * alias算法抽取
     * @param students  拿到所有数据
     * @param searchMap key为已抽取的次数，value为此此数的概率
     * @return
     */
    public static List<Student> aliasStudents(List<Student> students,Map<Integer,Double> searchMap){
        List<Student> studentList=new ArrayList<>();
        List<Double> prob = new ArrayList<Double>();    //对应students集合的概率集合
        Map<String,Student> resultMap=new HashMap<>();
        //prob.add(0.25); /* 0.01% 几率命中 */
        //prob.add(0.25); /* 50% 几率命中 */
        //prob.add(0.25); /* 50% 几率命中 */
//        prob.add(0.001);//一等奖
//        prob.add(0.05);//二等奖
//        prob.add(0.1);//三等奖
//        prob.add(0.3);
//        prob.add(0.549);
        String []str=new String[students.size()];
        for (int i=0;i<students.size();i++){
            Student student=students.get(i);
//            System.out.println("概率:"+searchMap.get(student.getCount()));
            prob.add(searchMap.get(student.getCount()));
            str[i]=student.getId();
            resultMap.put(student.getId(),student);
        }
//        String[] str={"一等奖","二等奖","三等奖","四等奖","未中奖"};
//        int[] test={0,0,0,0};
        AliasMethod am = new AliasMethod(prob);
        for(int i=0;i<8;i++){
            Student result=resultMap.get(str[am.next()]);
            if (studentList.indexOf(result)!=-1){
                i--;
                continue;
            }
            studentList.add(resultMap.get(str[am.next()]));
        }
        studentList.add(4,new Student());
        return studentList;
    }
}
