package com.ls.test.service;

import com.infinite.framework.service.PersistentObjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/spring/application-context.xml",
        "classpath:/spring/application-core-component.xml",
        "classpath:/spring/application-dao.xml",
        "classpath:/spring/application-jms.xml",
        "classpath:/spring/application-service.xml",
        "classpath:/spring/application-shiro.xml",
        "classpath:application-test.xml",
        "classpath:/spring/dispatcher-servlet.xml"
})
public class TestPersistentObjectService {

    private static final Logger log = LoggerFactory.getLogger(TestPersistentObjectService.class);

    @Autowired
    private PersistentObjectService persistentObjectService;

    @Test
    public void testPut() {
        persistentObjectService.put("appkey", "namespace", "{\n" +
                "\t\"a\": \"a\",\n" +
                "\t\"b\": {\n" +
                "\t\t\"b1\": \"b1\",\n" +
                "\t\t\"b2\": 2\n" +
                "\t},\n" +
                "\t\"c\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"c1a\": \"c1a\",\n" +
                "\t\t\t\"c1b\": \"c1b\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"c2a\": \"c2a\",\n" +
                "\t\t\t\"c2b\": \"c2b\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"d\": {\n" +
                "\t\t\"b1\": \"d1\",\n" +
                "\t\t\"b2\": 3\n" +
                "\t}\n" +
                "}");
    }


}
