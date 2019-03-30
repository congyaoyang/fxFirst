package com.qianfeng.fxmall.goods.dao;

import com.qianfeng.fxmall.goods.bean.WxbGood;

import java.util.List;

/**
 * 商品管理
 */
public interface IGoodsDAO {

    //分页查询
    List<WxbGood> queryGoodsByPage(Integer page);

    //查询所有商品
    List<WxbGood> queryAllGoods();

    //添加商品
    void insertGoods(WxbGood wxbGood);
}
