package test;

import com.bean.Apple;
import org.junit.Test;

import java.util.*;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class SpringTest {

    @Test
    public void test(){
          Object[] obj =   new Object[]{SpringTest.class};

        System.out.println( ((Class<?>) obj[0]).getName());
        SpringTest test = new SpringTest();
        test.show(Apple.class);
    }


    public void show(Object ... obj){
            Object [] objs = obj;
            Set<String> names = new HashSet<String>();
            for(int i=0;i<objs.length;i++){
                names.add(((Class<?>)objs[i]).getName());
            }

        try {
            init(names);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(Set<String> names) throws Exception {
        List<Object> data = new ArrayList<Object>();
         for(String str :names){
            data.add(Class.forName(str));
        }
        System.out.println(names);
        System.out.println(data);

    }
}
