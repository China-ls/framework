package test.infinite.dao;

import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.persistent.VirtualSensorDataDAO;
import com.infinite.eoa.schedule.CencusComponentWorkTimeSchedule;
import com.infinite.eoa.service.ComponentWorkTimeCencusService;
import com.infinite.eoa.service.impl.VirtualSensorDataServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:/spring/application-context.xml",
        "classpath:/spring/application-schedule.xml",
        "classpath:/spring/application-service.xml",
        "classpath:/spring/application-jms.xml",
        "classpath:/spring/application-thread-pool.xml",
        "classpath:/spring/application-dao.xml"
})
public class SensorDAOTest {

    @Autowired
    private VirtualSensorDAO sensorDAO;
    @Autowired
    private VirtualSensorDataDAO sensorDataDAO;
    private VirtualSensorDataServiceImpl sensorDataService;
    @Autowired
    private ComponentWorkTimeCencusService cencusService;
    @Autowired
    private CencusComponentWorkTimeSchedule cencusSchedule;


    @Test
    public void testGet() {
        VirtualSensor sensor = sensorDAO.findById("841188c2-b170-4e91-9234-5df4b255dc1c");
        System.out.println(sensor);
//        System.out.println(JsonUtil.toJson(sensor));
    }

    @Test
    public void test() {
//        cencusSchedule.cencus();
        System.out.println(
                cencusService.getDayAndMonthBySensorId("988b743c-3a73-4485-931e-379653f0593f")
        );
    }

}