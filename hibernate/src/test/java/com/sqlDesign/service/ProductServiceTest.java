package com.sqlDesign.service;

import com.sqlDesign.entity.ProductEntity;
import com.sqlDesign.serviceImpl.ProductServiceImpl;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Mr.Wang
 * @version 2018/10/29
 */
public class ProductServiceTest {

    private ProductService productService;

    @Test
    public void testGetAll() {
        productService = new ProductServiceImpl();
        ArrayList<String> list = productService.queryPlanList();
        list.forEach(System.out::println);
    }

    @Test
    public void testQueryAllProducts() {
        productService = new ProductServiceImpl();
        int cid = 3;
        ArrayList<ProductEntity> productEntities = productService.queryAllProducts(cid, 10);
        for (ProductEntity product:productEntities) {
            System.out.println(product.getPid() + " " + product.getPname());
        }
    }

    @Test
    public void testOrderProductNow() {
        productService = new ProductServiceImpl();
        int cid0 = 3;
//        int cid1 = 3;
        System.out.println(productService.orderProductNow(cid0, 2));
//        System.out.println(productService.orderProductNow(cid1, 4));
        ArrayList<ProductEntity> productList0 = productService.queryAllProducts(cid0, 10);
        for (ProductEntity product:productList0) {
            System.out.println(product.getPid() + " " + product.getPname());
        }
//        ArrayList<ProductEntity> productList1 = productService.queryAllProducts(cid1, 10);
//        for (ProductEntity product:productList1) {
//            System.out.println(product.getPid() + " " + product.getPname());
//        }
    }

    @Test
    public void testOrderProductNext() {
        productService = new ProductServiceImpl();
        int cid0 = 3;
        System.out.println(productService.orderProductNext(cid0, 3));
    }

    @Test
    public void testCancelProductNow() {
        productService = new ProductServiceImpl();
        int cid0 = 3;
        System.out.println(productService.cancelProductNow(cid0, 2));
//        ArrayList<ProductEntity> productList0 = productService.queryAllProducts(cid0, 10);
//        for (ProductEntity product:productList0) {
//            System.out.println(product.getPid() + " " + product.getPname());
//        }
    }

    @Test
    public void testCancelProductNext() {
        productService = new ProductServiceImpl();
        int cid0 = 3;
        System.out.println(productService.cancelProductNext(cid0, 3));
    }
}
