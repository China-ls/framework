package com.ls.test.morphia;

/**
 * Created by hx on 16-7-27.
 */
public class UserDAOTest {
//    private final String host = "127.0.0.1";
//    private final int port = 27017;
//
//    private MongoClient mongo;
//    private Morphia morphia;
//    private UserDAO userDAO;
//
//    @Before
//    public void init() {
//        mongo = new MongoClient(host, port);
//        morphia = new Morphia();
//        userDAO = new UserDAO(User.class, mongo, morphia, "temp");
//    }
//
//    @After
//    public void release() {
//        mongo.close();
//    }
//
//    private void print(QueryResults queryResults, String title) {
//        System.out.println( "========== " + title + " ==========" );
//        List list = queryResults.asList();
//        for (Object o : list) {
//            System.out.println( o );
//        }
//    }
//
//    @Test
//    public void deleteAll() {
//        WriteResult result = userDAO.deleteByQuery(userDAO.createQuery());
//        System.out.println( "delete count:" + result.getN() );
//    }
//
//    @Test
//    public void query() {
//        print( userDAO.find(), "find all user" );
//    }
//
//    @Test
//    public void insert() {
//        PersistentUser user = new PersistentUser();
//        user.setName("yoyo");
//        user.setAddress("address");
//        user.setAge(12);
//        user.setSex(false);
//        user.setId(1);
//        userDAO.save(user);
//        query();
//    }
//
//    @Test
//    public void update() {
//        print(userDAO.find(userDAO.createQuery().filter("id =", 1)), "find id = 1");
//        UpdateResults result = userDAO.update(userDAO.createQuery().filter("id =", 1), userDAO.createUpdateOperations().set("name", "haha") );
//        System.out.println( "update count:" + result.getUpdatedCount() );
//        query();
//    }
//
//    @Test
//    public void delete() {
//        WriteResult result = userDAO.deleteByQuery(userDAO.createQuery().filter("id =", 1));
//        System.out.println( "delete count:" + result.getN() );
//        query();
//    }
}
