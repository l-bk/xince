package com.xc.test;

import com.xc.test.dao.XcTestAnswerDao;
import com.xc.test.entity.XcTestAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml","classpath:spring-redis.xml"})
public class XcTestAnswerTest {

    @Resource
    private XcTestAnswerDao xcTestAnswerDao;

    @Test
    public void selectAnswerByPoint(){
        XcTestAnswer newAnswer =new XcTestAnswer();
        newAnswer.setTestId(73);
        newAnswer.setPoint(10);
    }
}
