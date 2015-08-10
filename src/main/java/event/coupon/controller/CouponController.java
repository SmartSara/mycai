package event.coupon.controller;

import com.alibaba.fastjson.JSON;
import event.coupon.pojo.Coupon;
import event.coupon.pojo.Voucher;
import event.coupon.service.CouponService;
import mycai.pojo.Order;
import mycai.pojo.Product;
import mycai.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by darlingtld on 2015/8/9 0009.
 */
@Controller
@RequestMapping("/coupon")
public class CouponController {
    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private CouponService couponService;

    @RequestMapping(value = "/calc/wechatid/{wechatid}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Coupon> getCalculatedCouponList(@PathVariable("wechatid") String wechatid, @RequestParam("bill") String billJson) {
        try {
            return couponService.getCalculatedCouponList(wechatid, URLDecoder.decode(billJson, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/all/wechatid/{wechatid}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Coupon> getCouponList(@PathVariable("wechatid") String wechatid) {
        return couponService.getCouponList(wechatid);
    }

    @RequestMapping(value = "/voucher/wechatid/{wechatid}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Voucher> getVoucherList(@PathVariable("wechatid") String wechatid) {
        return couponService.getCouponList(wechatid, Voucher.class);
    }
}
