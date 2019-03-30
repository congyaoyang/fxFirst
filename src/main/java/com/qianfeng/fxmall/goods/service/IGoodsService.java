package com.qianfeng.fxmall.goods.service;

import com.qianfeng.fxmall.goods.bean.WxbGood;

import java.util.List;

public interface IGoodsService {

    //分页查询商品
    List<WxbGood> queryGoodsByPage(Integer page);

    //查询所有商品
    List<WxbGood> queryAllGoods();

    //添加商品
    void insertGoods(WxbGood wxbGood);
}
