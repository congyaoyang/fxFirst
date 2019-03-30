package com.qianfeng.fxmall.commons.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisSessionFactoryUtils {

    /**
     * 饿汉单例模式创建
     */
    public static SqlSessionFactory sqlSessionFactory;
    public static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();

    static{
        initSessionFactory();
    }

    //初始化SqlSessionFactory对象
    public static void initSessionFactory(){
        try {
            //1、配置文件只需要加载一次（全局）
            InputStream inputStream = Resources.getResourceAsStream("mybatis.cfg.xml");
            //2、全局唯一
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获得SqlSessionFactory对象对象
    public static SqlSessionFactory getSqlSessionFactory(){
        if(sqlSessionFactory == null){
           initSessionFactory();
        }
        return sqlSessionFactory;
    }

    //获取SqlSession对象
    public static SqlSession getsession(){
        SqlSession session = threadLocal.get();
        if(session == null){
            session = sqlSessionFactory.openSession();
            threadLocal.set(session);
        }
        return session;
    }
}
