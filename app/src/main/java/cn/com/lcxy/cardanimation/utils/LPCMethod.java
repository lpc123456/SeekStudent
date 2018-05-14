package cn.com.lcxy.cardanimation.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 根据次数获得抽取概率
 * Created by 85229 on 2018/5/5.
 */

public class LPCMethod {
    /**
     * 根据传递过来的参数计算得到概率
     * @param values 抽取次数:出现次数总条数
     * @param  min 最小值
     * @param  sum 总条数（总个数）
     * @return  出现次数:概率
     */
    public static Map<Integer,Double> getProb(Map<Integer,Integer> values,int min,int sum){
        Map<Integer,Double> resultMaps=new HashMap<>();
        Set<Integer> keys=values.keySet();
        //初始化最小值
        Double d_sum= Double.valueOf(sum);
        resultMaps.put(min, (Double.valueOf(1)/d_sum));
        for (int key:keys) {
            if((key-min)==0){//如果与最小值相等
//                resultMaps.put(key, (double) (1/sum));
            }else {//否则大于最小值
                Double v = Math.pow(Double.valueOf(1)/Double.valueOf(d_sum),key-min+1);
                resultMaps.put(key,v);
                Double vMin=resultMaps.get(min);
                vMin=vMin+Double.valueOf((Double.valueOf(1)/Double.valueOf(sum)-v)*values.get(key));
                resultMaps.put(min,vMin);
            }
        }
        return resultMaps;
    };
}
