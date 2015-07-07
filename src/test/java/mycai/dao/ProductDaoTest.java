package mycai.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import mycai.crawler.ImageCrawler;
import mycai.pojo.Category;
import mycai.pojo.Order;
import mycai.pojo.Product;
import mycai.pojo.Type;
import mycai.service.OrderService;
import mycai.service.ProductService;
import mycai.util.ImageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by darlingtld on 2015/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class ProductDaoTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    public void testProductSave() {
        Product product = new Product();
        product.setName("青菜");
        product.setDescription("最好吃的青菜");
        product.setCategory(Category.YECAILEI);
        product.setPrice(10.9);
        int id = productService.save(product);
        System.out.println(id);
    }

    @Test
    public void updateProductImages() {
        List<Product> productList = productService.getAll();
        for (Product product : productList) {
            if (null != product.getPicurl() && product.getPicurl().contains("pic")) {
                continue;
            }
            String picUUID = product.generatePicurlHash();
            try {
                ImageCrawler.getProductImg(product, picUUID);
                product.setPicurl("images/" + picUUID + ".jpg");
                productService.upsert(product);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
//            break;
//
        }
    }

    @Test
    public void updateProductImageById() {
//        for (int i = 127; i < 212; i++) {
        try {
            Product product = productService.getById(211);
            String picUUID = product.generatePicurlHash();
            ImageCrawler.getProductImg(product, picUUID);
            product.setPicurl("images/" + picUUID + ".jpg");
            productService.upsert(product);
        } catch (Exception e) {
            e.printStackTrace();
//                continue;
        }
//        }
    }

    @Test
    public void importImages() {
        String imgSrcDir = "C:\\Users\\darlingtld\\IdeaProjects\\mycai_main\\src\\main\\webapp\\product_images";
        File file = new File(imgSrcDir);
        File[] imgFiles = file.listFiles();
        for (File imgFile : imgFiles) {
            System.out.println(imgFile.getName());
            Product product = new Product();
            product.setName(imgFile.getName().substring(0, imgFile.getName().indexOf(".")));
            product.setType(Type.SHUCAISHUIGUO);
            product.setCategory(Category.YECAILEI);
            product.setDescription("精选" + imgFile.getName().substring(0, imgFile.getName().indexOf(".")));
            product.setPrice(2.68);
            product.setUnit("斤");
            product.setPicurl("product_images/" + product.generatePicurlHash() + ".jpg");
            product.setDataChangeLastTime(new Timestamp(System.currentTimeMillis()));
            System.out.println(product);
            imgFile.renameTo(new File(imgSrcDir + "\\" + product.generatePicurlHash() + ".jpg"));
            productService.save(product);
//            break;
        }
    }

    @Test
    public void getProductByFavourites() {
        String category = "yecailei";
        String wechatId = "o5Irvt5957jQ4xmdHmDp59epk0UU";
        List<Product> productList = productService.getList(category);
        for (Product product : productList) {
            System.out.println(product);
        }
        List<Order> orderList = orderService.getLatestList(wechatId, 5);
        Map<String, AtomicInteger> boughtItemsMap = new HashMap<>();

        for (Order order : orderList) {
            System.out.println(order);
            JSONObject jsonObject = JSON.parseObject(order.getBill());
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.size(); i++) {
                String productName = jsonArray.getJSONObject(i).getString("productName");
                if (boughtItemsMap.containsKey(productName)) {
                    boughtItemsMap.get(productName).incrementAndGet();
                } else {
                    boughtItemsMap.put(productName, new AtomicInteger(1));
                }
            }

        }
        List<Map.Entry<String, AtomicInteger>> sortedProductList = new ArrayList<>(boughtItemsMap.entrySet());
        Collections.sort(sortedProductList, new Comparator<Map.Entry<String, AtomicInteger>>() {

            @Override
            public int compare(Map.Entry<String, AtomicInteger> o1, Map.Entry<String, AtomicInteger> o2) {
                return o1.getValue().intValue() - o2.getValue().intValue();
            }
        });

        for (Map.Entry<String, AtomicInteger> entry : sortedProductList) {
            for (Product product : productList) {
                if (product.getName().equals(entry.getKey())) {
                    productList.set(0, product);
                    break;
                }
            }
        }
        System.out.println(sortedProductList);
        System.out.println(productList);
    }
}

