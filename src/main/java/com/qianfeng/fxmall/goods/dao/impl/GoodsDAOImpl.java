package com.qianfeng.fxmall.goods.dao.impl;

import com.qianfeng.fxmall.commons.info.SystemConstantsUtils;
import com.qianfeng.fxmall.commons.mybatis.MyBatisSessionFactoryUtils;
import com.qianfeng.fxmall.goods.bean.WxbGood;
import com.qianfeng.fxmall.goods.dao.IGoodsDAO;
import com.qianfeng.fxmall.goods.mapper.GoodsMapper;

import java.util.List;

/**
 * 商品数据访问层
 */
public class GoodsDAOImpl implements IGoodsDAO {

    //分页查询商品
    @Override
    public List<WxbGood> queryGoodsByPage(Integer page) {
        GoodsMapper mapper = MyBatisSessionFactoryUtils.getsession().getMapper(GoodsMapper.class);
        List<WxbGood> wxbGoods = mapper.queryGoodsByPage(page, SystemConstantsUtils.Page.PAGE_SIZE);
        return wxbGoods;
    }

    //查询所有商品
    @Override
    public List<WxbGood> queryAllGoods() {
        GoodsMapper mapper = MyBatisSessionFactoryUtils.getsession().getMapper(GoodsMapper.class);
        List<WxbGood> goodList = mapper.queryAllGoods();
        return goodList;
    }

    //添加商品
    @Override
    public void insertGoods(WxbGood wxbGood) {
        GoodsMapper mapper = MyBatisSessionFactoryUtils.getsession().getMapper(GoodsMapper.class);
        mapper.insertGoods(wxbGood);
        MyBatisSessionFactoryUtils.getsession().commit();
    }
}
