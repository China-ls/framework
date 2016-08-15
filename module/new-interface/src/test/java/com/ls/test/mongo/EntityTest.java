package com.ls.test.mongo;

import com.google.gson.reflect.TypeToken;
import com.infinite.framework.core.util.JsonUtil;
import com.infinite.framework.entity.VirtualSensor;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class EntityTest {

    @Test
    public void testConverVS() throws IOException {
        InputStream jsonIs = EntityTest.class.getClassLoader().getResourceAsStream("xsensor.json");
        String json = IOUtils.toString(jsonIs);
//        System.out.println( json );
        VirtualSensor[] vs = JsonUtil.fromJson(json, new TypeToken<VirtualSensor[]>(){}.getType());
        IOUtils.closeQuietly(jsonIs);
        System.out.println(Arrays.toString(vs));
    }

}
