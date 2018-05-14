package cn.com.lcxy.cardanimation;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Map<Integer,Double> resultMaps=new HashMap<>();
        Double v = Math.pow(Double.valueOf(1)/Double.valueOf(38),1+1);
        resultMaps.put(1,v);
        Double vMin=Double.valueOf(1)/Double.valueOf(38);
        System.out.println("vMin=:"+vMin);
        Double dd=vMin+Double.valueOf((Double.valueOf(1)/Double.valueOf(38)-v)*1);
        Double d= Double.valueOf((Double.valueOf(1)/Double.valueOf(38)-v)*1);
        System.out.println("d=:"+d);
        System.out.println("vMin+d=:"+(d+vMin));
        System.out.println("dd=:"+dd);
//        System.out.println("v=="+v);
//        System.out.println("vMin=="+vMin);
        resultMaps.put(0,dd);
        System.out.println("result:"+resultMaps.get(0));
        assertEquals(4, 2 + 2);
    }
}