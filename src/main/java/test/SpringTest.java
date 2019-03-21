package test;

import com.bean.Apple;
import com.bean.Product;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class SpringTest {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        Product product = (Product) applicationContext.getBean("product");

        product.say();

    }

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


    public void testXml(){
        String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><rd><fl>1</fl><msg>查验成功</msg><fp><fpdm>3100172130</fpdm><fphm>37438347</fphm><sj><![CDATA[上海]]></sj><kprq>2017-10-17</kprq><gfmc><![CDATA[百望股份有限公司]]></gfmc><gfsh>110108339805094</gfsh><gfdzdh><![CDATA[北京市海淀区马连洼竹园住宅小区综合楼7层713 010-84782665]]></gfdzdh><gfyhzh><![CDATA[中国建设银行北京苏州桥支行11001079800053014887]]></gfyhzh><xfmc><![CDATA[上海凤罗投资有限公司]]></xfmc><xfsh>91310112MA1GB8EUX4</xfsh><xfdzdh><![CDATA[上海市闵行区吴中路453号宜必思古北酒店 021-64026687]]></xfdzdh><xfyhzh><![CDATA[中国工商银行股份有限公司上海市张虹支行1001166409900091089]]></xfyhzh><bz></bz><jqbm>661611813113</jqbm><fplx>01</fplx><jym>46396598384135479596</jym><zfbz>N</zfbz><sph><xh>1</xh><spmc><![CDATA[住宿费]]></spmc><ggxh></ggxh><jldw></jldw><sl>1</sl><dj>910.6796116504854</dj><je>910.68</je><slv>3%</slv><se>27.32</se></sph><je>910.68</je><se>27.32</se><jshj>938.00</jshj></fp></rd>";

    }
}
