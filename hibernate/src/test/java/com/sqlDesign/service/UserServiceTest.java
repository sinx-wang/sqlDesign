package com.sqlDesign.service;

import com.sqlDesign.serviceImpl.ProductServiceImpl;
import com.sqlDesign.serviceImpl.UserServiceImpl;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mr.Wang
 * @version 2018/10/29
 * @program hibernate
 * @description
 */
public class UserServiceTest {

    private UserService userService;
    private ProductService productService;

    @Test
    public void testChargeOfCall() {
        userService = new UserServiceImpl();
        Date dateStart = new Date();
        Date dateEnd = new Date();
        Date longStart = new Date();
        Date longEnd = new Date();
        String timeStartStr = String.valueOf(2018) + "/" + String.valueOf(10) + "/1 10:23:01";
        String timeEndStr = String.valueOf(2018) + "/" + String.valueOf(10) + "/1 10:25:05";
        String longStartStr = String.valueOf(2018) + "/" + String.valueOf(10) + "/1 12:00:00";
        String longEndStr = String.valueOf(2018) + "/" + String.valueOf(10) + "/1 14:00:00";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            dateStart = sdf.parse(timeStartStr);
            dateEnd = sdf.parse(timeEndStr);
            longStart = sdf.parse(longStartStr);
            longEnd = sdf.parse(longEndStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp tsStart = new Timestamp(dateStart.getTime());
        Timestamp tsEnd = new Timestamp(dateEnd.getTime());
        Timestamp tsLongStart = new Timestamp(longStart.getTime());
        Timestamp tsLongEnd = new Timestamp(longEnd.getTime());
        int cid = 1;
        double chargeOfCall1 = userService.chargeOfCall(cid, tsStart, tsEnd);
        double chargeOfCall2 = userService.chargeOfCall(cid, tsLongStart, tsLongEnd);
        System.out.println(chargeOfCall1);
        System.out.println(chargeOfCall2);
    }

    @Test
    public void testOrderNewCall() {
        productService = new ProductServiceImpl();
        userService = new UserServiceImpl();
        int cid = 1;
        System.out.println(productService.orderProductNow(1, 2));

        Date dateStart = new Date();
        Date dateEnd = new Date();
        String timeStartStr = String.valueOf(2018) + "/" + String.valueOf(10) + "/2 11:05:01";
        String timeEndStr = String.valueOf(2018) + "/" + String.valueOf(10) + "/2 11:25:05";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            dateStart = sdf.parse(timeStartStr);
            dateEnd = sdf.parse(timeEndStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp tsStart = new Timestamp(dateStart.getTime());
        Timestamp tsEnd = new Timestamp(dateEnd.getTime());
        double chargeOfCall = userService.chargeOfCall(cid, tsStart, tsEnd);
        System.out.println(chargeOfCall);
    }
}
