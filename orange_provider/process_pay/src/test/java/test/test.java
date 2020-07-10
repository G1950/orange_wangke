package test;

import com.guangming.utils.SnowFlake;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @ClassName test
 * @Description TODO
 * @Author Gm
 * @Date 2020/7/5/005 11:51
 * @Version 1.0
 **/
public class test {
    public static void main(String[] args) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        gc.add(Calendar.DAY_OF_WEEK, 1);//表示月份加减
        GregorianCalendar gc1 = new GregorianCalendar();
        gc1.setTime(new Date());
        gc1.add(Calendar.DAY_OF_WEEK, 1);//表示月份加减
        System.out.println(gc.getTime().compareTo(new Date()));
    }
}
