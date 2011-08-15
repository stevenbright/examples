package com.truward.serdemo;

import com.truward.serdemo.beans.Profile;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

    private static Profile createProfile(int id, int age,
                                         String email, String passwordHash, String username,
                                         boolean subscribedToNews) {
        final Profile profile = new Profile();
        profile.setId(id);
        profile.setAge(age);
        profile.setEmail(email);
        profile.setPasswordHash(passwordHash);
        profile.setUsername(username);
        profile.setSubscribedToNews(subscribedToNews);
        return profile;
    }

    private static void checkSerializingSimpleTypes() throws IOException, ClassNotFoundException {
        System.out.println("checkSerializingSimpleTypes");

        final int[] intArray = new int[] { 1, 2, 3, 1000, 4 };
        final List<Integer> intList = new ArrayList<Integer>();

        for (final int value : intArray) {
            intList.add(value);
        }

        final byte[] arrIntArray = serializeObj(intArray);
        final byte[] arrIntList = serializeObj(intList);

        System.out.println(MessageFormat.format("int array size = {0}, int list size = {1}",
                arrIntArray.length, arrIntList.length));

        @SuppressWarnings("unchecked")
        final List<Integer> deserIntList = (List<Integer>) deserializeObj(arrIntList);
        if (deserIntList.size() != intList.size()) {
            throw new IllegalArgumentException("Deserialized object does not match to that");
        }
    }

    private static void checkSerializingComplexTypes() throws IOException, ClassNotFoundException {
        System.out.println("checkSerializingComplexTypes");

        final Profile[] profileArray = new Profile[] {
                createProfile(0, 24, "test@test.com", "pwd14", "alex", true),
                createProfile(1, 35, "bob@yahoo.com", "pwd144", "bob", false),
                createProfile(2, 27, "cavin@mail.ru", "pwd15", "cavin", false),
                createProfile(3, 18, "dave@yandex.ru", "pwd2", "dave", true),
                createProfile(4, 42, "eva1@check.cz", "pwd3333", "eva", true),
        };

        final List<Profile> profileList = new ArrayList<Profile>();

        for (final Profile value : profileArray) {
            profileList.add(value);
        }

        final byte[] arrProfileArray = serializeObj(profileArray);
        final byte[] arrProfileList = serializeObj(profileList);

        System.out.println(MessageFormat.format("profile array size = {0}, profile list size = {1}",
                arrProfileArray.length, arrProfileList.length));

        @SuppressWarnings("unchecked")
        final List<Integer> deserProfileList = (List<Integer>) deserializeObj(arrProfileList);
        if (deserProfileList.size() != profileList.size()) {
            throw new IllegalArgumentException("Deserialized object does not match to that");
        }
    }

    public static void checkSerializeSimpleType() throws IOException, ClassNotFoundException {
        System.out.println("Simple serialization check");
        final byte[] objBytes = serializeObj(createProfile(0, 24, "test@test.com", "pwd14", "alex", true));
        final Profile profile = (Profile) deserializeObj(objBytes);
        System.out.println("Deserialized " + profile + ", serialized size = " + objBytes.length);
    }

    public static void main(String[] args) {
        System.out.println("Serializer test app");

        try {
            if (args.length > 10) {
                checkSerializeSimpleType();
            } else {
                checkSerializeSimpleType();
                checkSerializingSimpleTypes();
                checkSerializingComplexTypes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
