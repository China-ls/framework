package test.infinite.dao;

import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.entity.EmployeeDutyLinePatrol;
import com.infinite.eoa.entity.EntityConst;
import com.infinite.eoa.entity.RoutingInspectionCard;
import com.infinite.eoa.entity.RoutingInspectionMobile;
import com.infinite.eoa.persistent.EmployeeDAO;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:/spring/application-context.xml",
        "classpath:/spring/application-dao.xml"
})
public class EmployeeDAOTest {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Test
    public void testSave() {
        Employee employee = new Employee();
        employee.setType(EntityConst.EmployeeType.LINE_PATROL);
        employee.setName("测试巡线员");
        employee.setAddress("上海市");
        employee.setEmail("414057419@qq.com");
        employee.setPhone("13127557403");
        employee.setSex(EntityConst.EmployeeSex.FEMALE);
        EmployeeDutyLinePatrol duty = new EmployeeDutyLinePatrol();
        duty.setId(ObjectId.get());
        RoutingInspectionMobile device = new RoutingInspectionMobile();
        device.setName("巡检卡");
        device.setChannel("channel");
        device.setCreateTime(new Date());
        device.setModel("model");
        device.setSim("sim");
        device.setModifyTime(new Date());
        duty.addDevice(device);
        employee.addDuty(duty);
        Key<Employee> key = employeeDAO.save(employee);
        System.out.println(((ObjectId) key.getId()).toHexString());
    }

    @Test
    public void testModifyAddDuty() {
        EmployeeDutyLinePatrol duty = new EmployeeDutyLinePatrol();
        duty.setId(ObjectId.get());
        RoutingInspectionMobile device = new RoutingInspectionCard();
        device.setName("巡检卡1");
        device.setChannel("channel1");
        device.setCreateTime(new Date());
        device.setModel("model1");
        device.setSim("sim1");
        device.setModifyTime(new Date());
        duty.addDevice(device);

        employeeDAO.update(
                employeeDAO.createQuery().filter("_id", new ObjectId("57e4b8f32dbcae7aa82d2f3b")),
                employeeDAO.createUpdateOperations().add("duties", duty)
        );
    }

    @Test
    public void testModifySetDuty() {
        EmployeeDutyLinePatrol duty = new EmployeeDutyLinePatrol();
        duty.setId(ObjectId.get());
        RoutingInspectionMobile device = new RoutingInspectionCard();
        device.setName("巡检卡2");
        device.setChannel("channel2");
        device.setCreateTime(new Date());
        device.setModel("model2");
        device.setSim("sim2");
        device.setModifyTime(new Date());
        duty.addDevice(device);

        employeeDAO.update(
                employeeDAO.createQuery().filter("_id", new ObjectId("57e4b8f32dbcae7aa82d2f3b")),
                employeeDAO.createUpdateOperations().set("duties", duty)
        );
    }

    @Test
    public void testGet() {
        Employee employee = employeeDAO.findById("57e4b8f32dbcae7aa82d2f3b");
        System.out.println(JsonUtil.toJson(employee));
    }


}
