package test.infinite.service;

import com.infinite.framework.core.util.JsonUtil;
import com.infinite.framework.service.VirtualSensorDataService;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/application-*.xml")
public class VirtualSensorDataServiceTest {

    @Autowired
    private VirtualSensorDataService virtualSensorDataService;

    @Test
    public void testFindFieldChangeAndTimeDistance() {
        Document result = virtualSensorDataService.findFieldChangeByDays(
                "ab", "841188c2-b170-4e91-9234-5df4b255dc1c", "negtive_totoal",
                "2016-08-22", "2016-08-23"
        );
        System.out.println(result);
    }

    @Test
    public void testFindByFieldExsistAndProjection() {
        List<Document> result = virtualSensorDataService.findByFieldExsistAndProjection(
                "ab", "841188c2-b170-4e91-9234-5df4b255dc1c", 0, 0,
                Arrays.asList("image"), Arrays.asList("time", "sensor_id", "comp_type", "comp_id", "image"),
                null, null
        );
        System.out.println(JsonUtil.toJson(result));
    }

}