//package com.ls.test.service;
//
//import com.infinite.framework.core.util.JsonUtil;
//import org.bson.BsonArray;
//import org.bson.BsonDocument;
//import org.bson.BsonValue;
//
//import javax.print.Doc;
//import java.util.Arrays;
//import java.util.Map;
//
//public class AndBsonable extends AbstractBsonable {
//    private final Iterable<IBsonable> filters;
//
//    public AndBsonable(final Iterable<IBsonable> filters) {
//        this.filters = notNull("filters", filters);
//    }
//
//    @Override
//    public String toJson() {
//
//        BsonDocument andRenderable = new BsonDocument();
//
//        for (IBsonable filter : filters) {
//            Document renderedRenderable = Document.parse(filter.toJson());
//            for (Map.Entry<String, Object> element : renderedRenderable.entrySet()) {
//                addClause(andRenderable, element);
//            }
//        }
//
//        if (andRenderable.isEmpty()) {
//            andRenderable.append("$and", new BsonArray());
//        }
//
//        return super.toJson();
//    }
//
//    private void addClause(final Document document, final Map.Entry<String, Object> clause) {
//        if (clause.getKey().equals("$and")) {
////            for (Object value : clause.getValue().asArray()) {
////                for (Map.Entry<String, BsonValue> element : value.asDocument().entrySet()) {
////                    addClause(document, element);
////                }
////            }
//        } else if (document.size() == 1 && document.keySet().iterator().next().equals("$and")) {
//            document.get("$and").asArray().add(new BsonDocument(clause.getKey(), clause.getValue()));
//        } else if (document.containsKey(clause.getKey())) {
//            if (document.get(clause.getKey()).isDocument() && clause.getValue().isDocument()) {
//                BsonDocument existingClauseValue = document.get(clause.getKey()).asDocument();
//                BsonDocument clauseValue = clause.getValue().asDocument();
//                if (keysIntersect(clauseValue, existingClauseValue)) {
//                    promoteRenderableToDollarForm(document, clause);
//                } else {
//                    existingClauseValue.putAll(clauseValue);
//                }
//            } else {
//                promoteRenderableToDollarForm(document, clause);
//            }
//        } else {
//            document.append(clause.getKey(), clause.getValue());
//        }
//    }
//
//    private boolean keysIntersect(final BsonDocument first, final BsonDocument second) {
//        for (String name : first.keySet()) {
//            if (second.containsKey(name)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void promoteRenderableToDollarForm(final BsonDocument document, final Map.Entry<String, BsonValue> clause) {
//        BsonArray clauses = new BsonArray();
//        for (Map.Entry<String, BsonValue> queryElement : document.entrySet()) {
//            clauses.add(new BsonDocument(queryElement.getKey(), queryElement.getValue()));
//        }
//        clauses.add(new BsonDocument(clause.getKey(), clause.getValue()));
//        document.clear();
//        document.put("$and", clauses);
//    }
//
//    public static void main(String[] args) {
//        String json = "{\n" +
//                "\t\"a\": \"a\",\n" +
//                "\t\"b\": {\n" +
//                "\t\t\"b1\": \"b1\",\n" +
//                "\t\t\"b2\": 2\n" +
//                "\t},\n" +
//                "\t\"c\": [\n" +
//                "\t\t{\n" +
//                "\t\t\t\"c1a\": \"c1a\",\n" +
//                "\t\t\t\"c1b\": \"c1b\"\n" +
//                "\t\t},\n" +
//                "\t\t{\n" +
//                "\t\t\t\"c2a\": \"c2a\",\n" +
//                "\t\t\t\"c2b\": \"c2b\"\n" +
//                "\t\t}\n" +
//                "\t],\n" +
//                "\t\"d\": {\n" +
//                "\t\t\"b1\": \"d1\",\n" +
//                "\t\t\"b2\": 3\n" +
//                "\t}\n" +
//                "}";
//        Document document = JsonUtil.fromJson(json, Document.class);
//        System.out.println( document.toJson() );
//    }
//
//}
