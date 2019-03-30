package com.qianfeng.fxmall.goods.service.impl;

import com.qianfeng.fxmall.commons.info.SystemConstantsUtils;
import com.qianfeng.fxmall.goods.bean.WxbGood;
import com.qianfeng.fxmall.goods.dao.IGoodsDAO;
import com.qianfeng.fxmall.goods.dao.impl.GoodsDAOImpl;
import com.qianfeng.fxmall.goods.service.IGoodsService;

import java.util.List;

public class GoodsServiceImpl implements IGoodsService {

    private IGoodsDAO goodsDAO = new GoodsDAOImpl();

    //分页查询商品
    @Override
    public List<WxbGood> queryGoodsByPage(Integer page) {
        if(page < 1){
            throw new IndexOutOfBoundsException("页码不能小于1");
        }
        //计算起始下标
        Integer index = (page - 1) * SystemConstantsUtils.Page.PAGE_SIZE;
        List<WxbGood> wxbGoods = goodsDAO.queryGoodsByPage(index);
        return wxbGoods;
    }

    //查询所有商品
    public List<WxbGood> queryAllGoods(){
        List<WxbGood> goodList = goodsDAO.queryAllGoods();
        return goodList;
    }

    //添加商品
    public void insertGoods(WxbGood wxbGood){
        goodsDAO.insertGoods(wxbGood);
    }
}
