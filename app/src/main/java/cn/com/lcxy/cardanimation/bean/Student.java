package cn.com.lcxy.cardanimation.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 85229 on 2018/4/17.
 */

public class Student implements Serializable{
    private String id;     //学号
    private String name;    //姓名
    private String className;   //班级
    private String collegeName; //学院
    private int score;
    private String commitTime;
    private int count;

    public Student() {
    }

    public Student(String id, String name, String className, String collegeName,int count) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.collegeName = collegeName;
        this.count=count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", score=" + score +
                ", commitTime='" + commitTime + '\'' +
                ", count=" + count +
                '}';
    }
}
