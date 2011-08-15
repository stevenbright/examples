package com.truward.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import java.util.Set;

/**
 * Entry point
 */
public class App {

    private static void testMongoDB1() throws Exception {
        Mongo m = new Mongo("test.db.host");
        DB db = m.getDB("mydb");

        Set<String> names = db.getCollectionNames();
        for (String n : names) {
            System.out.println("colname = " + n);
        }

        // assume db has "things" collection (mongodb sample)
        DBCollection col = db.getCollection("things");
        DBCursor cur = col.find();
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }
    }


    public static void main(String[] args) {
        System.out.println("Mongodb test!");

        try {
            testMongoDB1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
