package com.truward.langexp;

import java.io.*;

/**
 * Entry point
 */
public class App {

    private static byte[] serializeObj(Object obj) throws IOException {
        // Serialize to a byte array
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream) ;
        try {
            objectOutputStream.writeObject(obj);

            // Get the bytes of the serialized object
            return byteArrayOutputStream.toByteArray();
        } finally {
            objectOutputStream.close();
            byteArrayOutputStream.close();
        }
    }

    private static Object deserializeObj(byte[] arr) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arr);
        final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        try {
            return objectInputStream.readObject();
        } finally {
            objectInputStream.close();
            byteArrayInputStream.close();
        }
    }

    private static void serializerTest() throws Exception {
        byte[] arr;
        Object obj;

        arr = serializeObj(546);
        obj = deserializeObj(arr);
        System.out.println("obj = " + obj);

        arr = serializeObj("some string");
        obj = deserializeObj(arr);
        System.out.println("obj = " + obj);

        arr = serializeObj(true);
        obj = deserializeObj(arr);
        System.out.println("obj = " + obj);

        arr = serializeObj(3.14159);
        obj = deserializeObj(arr);
        System.out.println("obj = " + obj);
    }

    public static void main(String[] args) {
        System.out.println("[App] langexp starts");

        try {
            serializerTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
