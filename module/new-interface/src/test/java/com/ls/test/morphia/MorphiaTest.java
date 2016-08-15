package com.ls.test.morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import org.junit.After;
import org.junit.Before;
import org.mongodb.morphia.Morphia;

/**
 * Created by hx on 16-7-27.
 */
public class MorphiaTest {
    private MongoClient mongo;
    private Morphia morphia;

    @Before
    public void init() {
        try {
            mongo = new MongoClient("127.0.0.1", 27017);
        } catch (MongoException e) {
            e.printStackTrace();
        }
        morphia = new Morphia();
    }

    private void print(Object o) {
        if (o != null) {
            System.out.println(o);
        }
    }

    /**
     * <b>function:</b> morphia对象的简单测试
     */
//    @Test
//    public void testMorphia() {
//        // 创建一个Datastore，过时的方法不推荐使用
//        Datastore ds = morphia.createDatastore(mongo, "myTestDB");
//        print("createDatastore: " + ds);
//        // 创建ds
//        ds = morphia.createDatastore(mongo, "myTestDB");
//        print("createDatastore: " + ds);
//        // 创建一个带用户名、密码的ds
//        //ds = morphia.createDatastore(mongo, "myTestDB", "admin", new char[] { '1', '2', '3', '4', '5', '6' });
//
//        // 设置操作资源对象，这里设置User.class 就可以完成对User的一系列操作
//        //morphia.map(User.class);
//        //morphia.mapPackage("com.hoo.entity");//会把整个包下面的类都加载进来
//        // 将对象转成DBObject
//        print("toDBObject: " + morphia.toDBObject(new User(System.currentTimeMillis(), "jackson", true, 22, null)));
//        // 将参数2转换成参数1的类型
//        print("fromDBObject: " + morphia.fromDBObject(ds, User.class, BasicDBObjectBuilder.start("sex", true).get()));
//        print("getMapper: " + morphia.getMapper());
//        print("isMapped: " + morphia.isMapped(User.class));
//    }

    /**
     * <b>function:</b> 对Mapper对象相关操作
     */
//    @Test
//    public void testMapper() {
//        Mapper mapper = morphia.getMapper();
//        // 添加对象映射
//        print("addMappedClass: " + mapper.addMappedClass(User.class));
//        //print(mapper.addMappedClass(mapper.addMappedClass(User.class));
//
//        // 创建实体缓存
//        print("createEntityCache: " + mapper.createEntityCache());
//        print(mapper.getCollectionName("myTestDB"));
//        print(mapper.getConverters());
//        PersistentUser user = new PersistentUser(System.currentTimeMillis(), "jackson", true, 22, null);
//        user.setId(1306814012734L);
//        print(mapper.getId(user));
//
//        for (EntityInterceptor ei : mapper.getInterceptors()) {
//            System.out.println("EntityInterceptor: " + ei);
//        }
//
//        // 查询主键
//        print("getKey: " + mapper.getKey(user));
//        // 所有已经映射的class
//        for (MappedClass mc : mapper.getMappedClasses()) {
//            System.out.println("getMappedClasses: " + mc);
//        }
//
//        print("mcMap: " + mapper.getMCMap());
//
//        print("getOptions: " + mapper.getOptions());
//        print("keyToRef: " + mapper.keyToDBRef(mapper.getKey(user)));
//        print("refToKey: " + mapper.refToKey(mapper.keyToDBRef(mapper.getKey(user))));
//    }

    /**
     * <b>function:</b> 实体缓存
     */
//    @Test
//    public void testEntityCache() {
//        EntityCache ec = morphia.getMapper().createEntityCache();
//        print("EntityCache: " + ec);
//        Datastore ds = morphia.createDatastore(mongo, "myTestDB");
//        PersistentUser user = new PersistentUser(System.currentTimeMillis(), "jackson", true, 22, null);
//        user.setId(1306814012734L);
//
//        // 添加实体
//        ec.putEntity(ds.getKey(user), user);
//        // 代理
//        ec.putProxy(ds.getKey(user), user);
//
//        print("getKey: " + ds.getKey(user));
//        print("getProxy: " + ec.getProxy(ds.getKey(user)));
//        print("getEntity: " + ec.getEntity(ds.getKey(user)));
//
//        print(ec.exists(ds.getKey(user)));
//
//        print("stats: " + ec.stats());
//    }

    @After
    public void destory() {
        mongo = null;
        morphia = null;
        System.gc();
    }
}
