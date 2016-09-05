package test.infinite.water.service;

import com.infinite.water.service.VirtualSensorService;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author hx on 16-8-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {
        "classpath:/spring/application-context.xml",
        "classpath:/spring/application-core-component.xml",
        "classpath:/spring/application-service.xml"}
)
public class VirtualSensorServiceTest {

    @Autowired
    private VirtualSensorService virtualSensorService;

    @Test
    public void getAllVirtualSensor() throws Exception {
        List<Document> sensors = virtualSensorService.getAllVirtualSensor();
        System.out.println(sensors);
    }

    @Test
    public void getVirtualSensorImage() throws Exception {
        System.out.println(
                virtualSensorService.getSensorImage("57c349da1337ce3707648629")
        );
    }

}