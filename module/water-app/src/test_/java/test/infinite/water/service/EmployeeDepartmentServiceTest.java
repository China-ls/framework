package test.infinite.water.service;

import com.infinite.water.entity.EmployeeDepartment;
import com.infinite.water.service.EmployeeDepartmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hx on 16-8-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {
        "classpath:/spring/application-context.xml",
        "classpath:/spring/application-core-component.xml",
        "classpath:/spring/application-service.xml"}
)
public class EmployeeDepartmentServiceTest {

    @Autowired
    private EmployeeDepartmentService employeeDepartmentService;

    @Test
    public void getAll() throws Exception {
        System.out.println(
                employeeDepartmentService.getAll()
        );
    }

    @Test
    public void getTree() throws Exception {
        System.out.println(
                employeeDepartmentService.getTreeRoot()
        );
    }

    @Test
    public void add() throws Exception {
        EmployeeDepartment dpt = new EmployeeDepartment();
        dpt.setName("21号楼邹叔");
        dpt.setParent_id("57c672c42dbcae180b2d37b6");
        dpt.setPath(",57c658472dbcae180b2d37ab,57c658692dbcae180b2d37ad,57c672c42dbcae180b2d37b6,");
        dpt.setSort(0);
        dpt.setLevel(3);
        System.out.println(
                employeeDepartmentService.add(dpt)
        );
    }

}