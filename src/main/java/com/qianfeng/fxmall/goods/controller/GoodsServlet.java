package com.qianfeng.fxmall.goods.controller;

import com.qianfeng.fxmall.commons.info.SystemConstantsUtils;
import com.qianfeng.fxmall.goods.bean.WxbGood;
import com.qianfeng.fxmall.goods.service.IGoodsService;
import com.qianfeng.fxmall.goods.service.impl.GoodsServiceImpl;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class GoodsServlet extends BaseServlet {

    private IGoodsService goodsService = new GoodsServiceImpl();


    public void selectAllGoods(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String spageNo = req.getParameter("pageNo");
        Integer pageNo = spageNo == null ? 1 : Integer.parseInt(spageNo);
        List<WxbGood> goodsList = goodsService.queryGoodsByPage(pageNo);
        List<WxbGood> wxbGoods = goodsService.queryAllGoods();
        //数据总记录
        Integer pageCount = wxbGoods.size();
        Integer pages = pageCount / SystemConstantsUtils.Page.PAGE_SIZE;
        //总页数
        Integer pageNO = pageCount % SystemConstantsUtils.Page.PAGE_SIZE == 0 ? pages : pages + 1;
        //保存当前页
        req.setAttribute("pageNo", pageNo);
        //保存总页数
        req.setAttribute("pageNO", pageNO);
        //保存总行数
        req.setAttribute("pageCount", pageCount);
        //保存每页的行数
        req.setAttribute("pageSize", SystemConstantsUtils.Page.PAGE_SIZE);
        req.setAttribute("goodsList", goodsList);
        req.getRequestDispatcher("goods_list.jsp").forward(req, resp);
    }

    public void insertGoods(HttpServletRequest req, HttpServletResponse resp) throws IOException, FileUploadException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("UTF-8");
        WxbGood wxbGood = new WxbGood();
        String goodId = UUID.randomUUID().toString().substring(1, 9);
        wxbGood.setGoodId(goodId);
        wxbGood.setCustomerId("123456");
        wxbGood.setSkuTitle("qwe");
        wxbGood.setSkuCost("qwe");
        wxbGood.setSkuPrice("123");
        wxbGood.setSkuPmoney("123");
        wxbGood.setCopyIds("123");
        wxbGood.setState(123);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        wxbGood.setCreateTime(timestamp);
        wxbGood.setToped(123);
        wxbGood.setRecomed(123);
        wxbGood.setTopedTime(timestamp);
        wxbGood.setRecomedTime(timestamp);
        wxbGood.setSpcId("123");
        wxbGood.setZonId("123");
        wxbGood.setWebsite("qwe");
        wxbGood.setIswxpay(123);
        wxbGood.setIsfdfk(123);
        wxbGood.setLeixingId(123);
        wxbGood.setKfqq("aaa");
        //判断请求中是否有文件上传对象
        if (ServletFileUpload.isMultipartContent(req)) {
            //创建文件上传对象
            FileUpload upload = new ServletFileUpload();
            //设置文件上传大小
            upload.setSizeMax(SystemConstantsUtils.MAX_SIZE);
            //获取表单项迭代器
            FileItemIterator itr = ((ServletFileUpload) upload).getItemIterator(req);
            int i = 0;
            while (itr.hasNext()) {
                FileItemStream fis = itr.next();
                if (fis.isFormField()) {
                    String value = Streams.asString(fis.openStream(),"UTF-8");
                    switch (fis.getFieldName()) {
                        case "good_name":
                            wxbGood.setGoodName(value);
                            break;
                        case "type_id":
                            wxbGood.setTypeId((value));
                            break;
                        case "order_no":
                            wxbGood.setOrderNo(Long.parseLong(value));
                            break;
                        case "sell_num":
                            wxbGood.setSellNum(Long.parseLong(value));
                            break;
                        case "promote_desc":
                            wxbGood.setPromoteDesc((value));
                            break;
                        case "tags":
                            wxbGood.setTags((value));
                            break;
                        case "copy_desc":
                            wxbGood.setCopyDesc((value));
                            break;
                        case "forward_link":
                            wxbGood.setForwardLink((value));
                            break;

                    }
                } else {
                    String filename = fis.getName();
                    if (filename != null) {
                        //从文件中截取后缀名
                        String suffix = filename.substring(filename.lastIndexOf("."));
                        //产生唯一的文件名
                        String filename2 = UUID.randomUUID().toString() + suffix;
                        if(i == 0){
                            wxbGood.setGoodPic(filename2);
                        }
                        if(i == 1){
                            wxbGood.setGoodPic1(filename2);
                        }
                        if(i == 2){
                            wxbGood.setGoodPic2(filename2);
                        }
                        filename = SystemConstantsUtils.UPLOAD_PATH + filename2;
                        OutputStream out = new FileOutputStream(filename);
                        Streams.copy(fis.openStream(),out,true);
                        ++i;
                    }
                }
            }
            goodsService.insertGoods(wxbGood);
            selectAllGoods(req,resp);
        }
    }
}
