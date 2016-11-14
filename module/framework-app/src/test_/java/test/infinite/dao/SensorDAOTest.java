package test.infinite.dao;

import com.infinite.eoa.entity.Component;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.VirtualSensorData;
import com.infinite.eoa.entity.aggregation.SensorMaxMinAggregation;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.persistent.VirtualSensorDataDAO;
import com.infinite.eoa.schedule.CencusComponentWorkTimeSchedule;
import com.infinite.eoa.service.ComponentWorkTimeCencusService;
import com.infinite.eoa.service.impl.VirtualSensorDataServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.Group;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;

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

    @Test
    public void testAggregateElectricUseage() {
        Datastore ds = sensorDataDAO.getDatastore();
        AggregationPipeline aggregationPipeline = ds.createAggregation(VirtualSensorData.class);
        Query<VirtualSensorData> query = sensorDataDAO.createQuery();
        aggregationPipeline
                .match(query.filter("comp_type", "electricmeter_sensor")
                        .field("power").greaterThan(0))
                .group(Group.id(Group.grouping("sensor_id"), Group.grouping("comp_id")), Group.grouping("max", Group.max("power")), Group.grouping("min", Group.min("power")));
        Iterator<SensorMaxMinAggregation> iterator = aggregationPipeline.aggregate(SensorMaxMinAggregation.class);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testSensorHighWaterLevelCount() {
        for (VirtualSensor sensor : sensorDAO.find()) {
            for (Component component : sensor.getComponents()) {
                if (component.getInstance_type() != 4105) {
                    continue;
                }
                String sensor_id = sensor.getSensor_id();
                String comp_id = component.getComp_id();

                Iterator<VirtualSensorData> dataIterator = sensorDataDAO.find(
                        sensorDataDAO.createQuery()
                                .filter("sensor_id", sensor_id)
                                .filter("comp_id", comp_id)
                                .order("time")
                ).iterator();
                int count = 0;
                boolean isOnoff = false;
                if (dataIterator.hasNext()) {
                    isOnoff = dataIterator.next().isOnoff();
                }
                while (dataIterator.hasNext()) {
                    VirtualSensorData data = dataIterator.next();
                    if (isOnoff == data.isOnoff()) {
                        continue;
                    }
                    isOnoff = data.isOnoff();
                    count++;
                }
                System.out.println(String.format("[sensor:%s, comp:%s, count:%d]", sensor_id, comp_id, count));
            }
        }


    }

}
