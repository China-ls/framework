//package com.ls.test.morphia;
//
//import com.infinite.framework.entity.Account;
//import com.infinite.framework.entity.Application;
//import com.infinite.framework.persistent.AccountDAO;
//import com.infinite.framework.persistent.ApplicationDAO;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mongodb.morphia.Key;
//import org.mongodb.morphia.query.UpdateResults;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.HashMap;
//import java.util.List;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//        "classpath:/spring/application-context.xml",
//        "classpath:/spring/application-core-component.xml",
//        "classpath:/spring/application-dao.xml",
//        "classpath:/spring/application-jms.xml",
//        "classpath:/spring/application-service.xml",
//        "classpath:/spring/application-shiro.xml",
//        "classpath:application-test.xml",
//        "classpath:/spring/dispatcher-servlet.xml"
//})
//public class TestMorphia {
//
//    private static final Logger log = LoggerFactory.getLogger(TestMorphia.class);
//
//    @Autowired
//    private AccountDAO accountDAO;
//    @Autowired
//    private ApplicationDAO applicationDAO;
//
//    @Autowired
//    private VDAO vdao;
//
//    @Test
//    public void dropCollection() {
//        accountDAO.getCollection().drop();
//        applicationDAO.getCollection().drop();
//    }
//
//    @Test
//    public void insert() {
//        log.debug("dbName : {}", accountDAO.getDatastore().getDB().getName());
//        log.debug("collectionName : {}", accountDAO.getCollection().getName());
//        Account account = new Account();
//        account.setId("abc");
//        account.setName("test_account_name");
//
//        Application application = new Application();
//        application.setId("app");
//        application.setName("app name");
//        Key<Application> key = applicationDAO.save(application);
//
//        account.addApplications(application);
//
//        application = new Application();
//        application.setId("app1");
//        application.setName("name1");
//        applicationDAO.save(application);
//
//        account.addApplications(application);
//
//        accountDAO.save(account);
//    }
//
//    @Test
//    public void findAll() {
//        log.debug("dbName : {}", accountDAO.getDatastore().getDB().getName());
//        log.debug("collectionName : {}", accountDAO.getCollection().getName());
//        Iterable<Account> it = accountDAO.createQuery().fetch();
//        while (it.iterator().hasNext()) {
//            log.debug("fetch: {}", it.iterator().next());
//        }
//    }
//
//    @Test
//    public void findOneApplication() {
//        log.debug("dbName : {}", applicationDAO.getDatastore().getDB().getName());
//        log.debug("collectionName : {}", applicationDAO.getCollection().getName());
//        Application applcation = applicationDAO.findOne("id", "app");
//        log.debug("find one: {}", applcation);
////        Iterable<Application> it = applicationDAO.createQuery();
////        while (it.iterator().hasNext()) {
////            log.debug("fetch: {}", it.iterator().next());
////        }
//    }
//
//    @Test
//    public void updateOneAccount() {
//        log.debug("dbName : {}", accountDAO.getDatastore().getDB().getName());
//        log.debug("collectionName : {}", accountDAO.getCollection().getName());
//        UpdateResults results = accountDAO.update(accountDAO.createQuery().filter("id", "abc"),
//                accountDAO.createUpdateOperations().set("username", "username").set("password", "password"));
//        log.debug("updateOneAccount , updatedCount: {}", results.getUpdatedCount());
////        Application application = applicationDAO.findById("id", "app");
////        if (null != application) {
////            application.setAccountId("abc");
////            applicationDAO.save(application);
////        }
////        Iterable<Application> it = applicationDAO.createQuery();
////        while (it.iterator().hasNext()) {
////            log.debug("fetch: {}", it.iterator().next());
////        }
//    }
//
//    @Test
//    public void updateOneApplication() {
//        log.debug("dbName : {}", applicationDAO.getDatastore().getDB().getName());
//        log.debug("collectionName : {}", applicationDAO.getCollection().getName());
//        UpdateResults results = applicationDAO.update(applicationDAO.createQuery().filter("id", "app"),
//                applicationDAO.createUpdateOperations().set("accountId", "abc"));
//        log.debug("update one app , updatedCount: {}", results.getUpdatedCount());
////        Application application = applicationDAO.findById("id", "app");
////        if (null != application) {
////            application.setAccountId("abc");
////            applicationDAO.save(application);
////        }
////        Iterable<Application> it = applicationDAO.createQuery();
////        while (it.iterator().hasNext()) {
////            log.debug("fetch: {}", it.iterator().next());
////        }
//    }
//
//    @Test
//    public void getByUsername() {
//        log.debug("findByUsername : {}", accountDAO.findByUsername("username"));
//    }
//
//    @Test
//    public void testUserNameExsist() {
//        log.debug("is username exsist : {}", accountDAO.isUsernameExsist("username"));
//    }
//
//    @Test
//    public void getUserPasswordErrorCount() {
//        log.debug("findUserPasswordErrorCount : {}", accountDAO.findUserPasswordErrorCount("username"));
//    }
//
//    @Test
//    public void testVEmb() {
//        V v = new V();
//
//        HashMap<String, String> cf = new HashMap<String, String>(2);
//        cf.put("a", "ca");
//        cf.put("b", "cb");
//        C c = new C("cN", "cID", cf);
//        v.cs.add(c);
//        v.id = "vid";
//        v.name = "vname";
//        v.fields.put("a", "va");
//        v.fields.put("b", "vb");
//
//        vdao.save(v);
//
//        List<V> list = vdao.find().asList();
//        System.out.println( list );
//    }
//
//}
