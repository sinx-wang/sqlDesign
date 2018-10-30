package com.sqlDesign;

import com.sqlDesign.entity.ProductEntity;
import com.sqlDesign.service.ProductService;
import com.sqlDesign.service.UserService;
import com.sqlDesign.serviceImpl.ProductServiceImpl;
import com.sqlDesign.serviceImpl.UserServiceImpl;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class Main
{
    private ProductService productService;
    private UserService userService;

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Main main = new Main();

        int cid = 3;
        int month = 10;
        int pid = 2; //通话套餐
        int pNextId = 3; //短信套餐

        //得到所有可选套餐列表
        System.out.println("All plans: ");
        ArrayList<String> planList = main.getPlanList();
        planList.forEach(System.out::println);

        ArrayList<String> orderedProducts = new ArrayList<>();
        if (true) {
            System.out.println("All ordered plans: ");
            //得到当月所有 用户订阅过的套餐
            orderedProducts = main.getAllProducts(cid, month);
            orderedProducts.forEach(System.out::println);
        }

        if (true) {
            System.out.println("Using: ");
            //得到当月所有 用户正在使用的套餐
            orderedProducts = main.getUsingProducts(cid);
            orderedProducts.forEach(System.out::println);
        }

        if (true) {
            System.out.println("Next month: ");
            //得到用户订阅的下个月套餐
            orderedProducts = main.getNextMonthProducts(cid);
            orderedProducts.forEach(System.out::println);
        }

        if (true) {
            //订购套餐立即生效
            System.out.println(main.orderProductNow(cid, pid));
        }
        if (true) {
            //订购套餐下月生效
            System.out.println(main.orderProductNext(cid, pNextId));
        }
        if (true) {
            //取消套餐立即生效
            System.out.println(main.cancelProductNow(cid, pid));
        }
        if (true) {
            //取消套餐下月生效
            System.out.println(main.cancelProductNext(cid, pNextId));
        }
        if (true) {
            //一次通话资费生成

            Date dateStart = new Date();
            Date dateEnd = new Date();
            String timeStartStr = String.valueOf(2018) + "/" + String.valueOf(10) + "/1 10:23:01";
            String timeEndStr = String.valueOf(2018) + "/" + String.valueOf(10) + "/1 11:25:05";
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            try {
                dateStart = sdf.parse(timeStartStr);
                dateEnd = sdf.parse(timeEndStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Timestamp tsStart = new Timestamp(dateStart.getTime());
            Timestamp tsEnd = new Timestamp(dateEnd.getTime());
            System.out.println("fee of call: " + main.chargeOfCall(cid, tsStart, tsEnd));
        }
        if (true) {
            //一次流量资费生成

            //设消耗本地流量3000M，全国流量3000M
            double localNum = 3000;
            double countryNum = 3000;
            System.out.println("fee of flow: " + main.chargeOfFlow(cid, localNum, true));
            System.out.println("fee of flow: " + main.chargeOfFlow(cid, countryNum, false));
        }
        if (true) {
            //短信资费生成

            int messagesNum = 110;
            System.out.println("fee of messages: " + main.chargeOfSms(cid, messagesNum));
        }
        if (true) {
            //月账单

            ArrayList<String> monthCharge = main.chargeOfMonth(cid, month);
            monthCharge.forEach(System.out::println);
        }

    }

    private ArrayList<String> getPlanList() {
        productService = new ProductServiceImpl();
        return productService.queryPlanList();
    }

    /**
     * 套餐的查询（包括历史记录）
     * @param cid 客户编号
     * @param month 月份
     * @return ArrayList<ProductEntity>
     * @author Mr.Wang
     */
    private ArrayList<String> getAllProducts(int cid, int month) {
        productService = new ProductServiceImpl();
        ArrayList<ProductEntity> productEntities = productService.queryAllProducts(cid, month);
        ArrayList<String> result = new ArrayList<>();
        productEntities.forEach(product->{
            String str = product.getPid() + " " + product.getPname() + " " + product.getBase();
            result.add(str);
        });
        return result;
    }

    /**
     * 查询正在使用的套餐
     *
     * @param cid 客户编号
     * @return ArrayList<ProductEntity>
     * @author Mr.Wang
     */
    private ArrayList<String> getUsingProducts(int cid) {
        productService = new ProductServiceImpl();
        ArrayList<ProductEntity> productEntities = productService.queryUsingProducts(cid);
        ArrayList<String> result = new ArrayList<>();
        productEntities.forEach(product->{
            String str = product.getPid() + " " + product.getPname() + " " + product.getBase();
            result.add(str);
        });
        return result;
    }

    /**
     * 得到下个月的套餐
     * @author Mr.Wang
     * @param cid 客户编号
     * @return java.util.ArrayList<java.lang.String>
     */
    private ArrayList<String> getNextMonthProducts(int cid) {
        productService = new ProductServiceImpl();
        ArrayList<ProductEntity> productEntities = productService.queryNextProducts(cid);
        ArrayList<String> result = new ArrayList<>();
        productEntities.forEach(product->{
            String str = product.getPid() + " " + product.getPname() + " " + product.getBase();
            result.add(str);
        });
        return result;
    }

    /**
     * 订购套餐立即生效
     * @author Mr.Wang
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return java.lang.String
     */
    private String orderProductNow(int cid, int pid) {
        productService = new ProductServiceImpl();
        if (productService.orderProductNow(cid, pid)) {
            return "ordered successful!";
        }
        return "ordered failed";
    }

    /**
     * 订购套餐下月生效
     * @author Mr.Wang
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return java.lang.String
     */
    private String orderProductNext(int cid, int pid) {
        productService = new ProductServiceImpl();
        if (productService.orderProductNext(cid, pid)) {
            return "ordered successful!";
        }
        return "ordered failed";
    }

    /**
     * 取消套餐立即生效
     * @author Mr.Wang
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return java.lang.String
     */
    private String cancelProductNow(int cid, int pid) {
        productService = new ProductServiceImpl();
        if (productService.cancelProductNow(cid, pid)) {
            return "canceled successful!";
        }
        return "canceled failed";
    }

    /**
     * 取消套餐立即生效
     * @author Mr.Wang
     * @param cid 客户编号
     * @param pid 套餐编号
     * @return java.lang.String
     */
    private String cancelProductNext(int cid, int pid) {
        productService = new ProductServiceImpl();
        if (productService.cancelProductNext(cid, pid)) {
            return "canceled successful!";
        }
        return "canceled failed";
    }

    /**
     * 一次通话资费生成
     * @param cid         客户编号
     * @param createdTime 通话开始时间
     * @param endTime     通话结束时间
     * @return double
     * @author Mr.Wang
     */
    private double chargeOfCall(int cid, Timestamp createdTime, Timestamp endTime) {
        userService = new UserServiceImpl();
        return userService.chargeOfCall(cid, createdTime, endTime);
    }

    /**
     * 一段流量资费生成
     * @param cid     客户编号
     * @param num     流量数额
     * @param isLocal 是否本地
     * @return double
     * @author Mr.Wang
     */
    private double chargeOfFlow(int cid, double num, boolean isLocal) {
        userService = new UserServiceImpl();
        return userService.chargeOfFlow(cid, num, isLocal);
    }

    /**
     * 一次短信资费生成
     * @param cid 客户编号
     * @param num 短信条数
     * @return double
     * @author Mr.Wang
     */
    private double chargeOfSms(int cid, int num) {
        userService = new UserServiceImpl();
        return userService.chargeOfSms(cid, num);
    }

    /**
     * 月账单
     * @param cid   客户编号
     * @param month 月份
     * @return ArrayList<String>
     * @author Mr.Wang
     */
    private ArrayList<String> chargeOfMonth(int cid, int month) {
        userService = new UserServiceImpl();
        return userService.chargeOfMonth(cid, month);
    }
}
