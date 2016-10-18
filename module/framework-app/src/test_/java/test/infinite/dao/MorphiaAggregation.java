package test.infinite.dao;
//
///**
// * Created by hx on 16-10-14.
// */
//
//import com.marstor.dms.mongo.util.service.Users;
//import com.marstor.dms.mongo.util.service.UsersGroup;
//import org.mongodb.morphia.Datastore;
//import org.mongodb.morphia.aggregation.Accumulator;
//import org.mongodb.morphia.aggregation.AggregationPipeline;
//import org.mongodb.morphia.aggregation.Sort;
//import org.mongodb.morphia.logging.Logger;
//import org.mongodb.morphia.logging.MorphiaLoggerFactory;
//import org.mongodb.morphia.query.Query;
//
//import java.util.Iterator;
//
//import static org.mongodb.morphia.aggregation.Group.*;
//
//public class MorphiaAggregation {
//    private final static Logger LOG = MorphiaLoggerFactory.get(MorphiaAggregation.class);
//
//    public static void main(String[] args) {
//        sumWithGroup();
//    }
//
//    private static void sumWithGroup() {
//        Datastore ds = DbUtils_Mongo.getMongoDbConnection();
//        Query<Object> query = ds.getQueryFactory().createQuery(ds);
//        AggregationPipeline pipeline = ds.createAggregation(Users.class)
//                .match(query.filter("grade >", 0))
//                .group(
//                        id(grouping("userName"), grouping("classId")),
//                        grouping("abc", first("userName")),
//                        grouping("classId", first("classId")),
//                        grouping("countAll", new Accumulator("$sum", 1)),
//                        grouping("totalGrade", sum("grade"))
//                )
//                .sort(Sort.ascending("abc"));
//
//        Iterator<UsersGroup> iterator = pipeline.aggregate(UsersGroup.class);
//        System.out.println(iterator.hasNext());
//        while (iterator.hasNext()) {
//            UsersGroup ug = iterator.next();
//            System.out.println(ug);
//        }
//    }
//}