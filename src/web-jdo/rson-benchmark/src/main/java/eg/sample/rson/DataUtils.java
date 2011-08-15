package eg.sample.rson;

import eg.sample.rson.domain.UserProfile;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Data utilities
 */
public final class DataUtils {

    public static UserProfile createProfile(int id, String name, String email,
                                            boolean online,
                                            double latitude, double longitude) {
        final UserProfile profile = new UserProfile();
        profile.setId(id);
        profile.setName(name);
        profile.setEmail(email);
        profile.setOnline(online);
        profile.setLatitude(latitude);
        profile.setLongitude(longitude);
        return profile;
    }

    //private final static TempCharSequence TEMP_CHAR_SEQUENCE = new TempCharSequence();



    public static void profileToJson(UserProfile profile, Appendable appendable) throws IOException {
        appendable.append("{\"id\":");

        // If uncommented - produces the worse results than just Integer.toString
        //IntegerUtils.appendInteger(TEMP_CHAR_SEQUENCE, appendable, profile.getId());
        appendable.append(Integer.toString(profile.getId()));

        appendable.append(",\"name\":\"");
        appendable.append(profile.getName());
        appendable.append("\",\"email\":\"");
        appendable.append(profile.getEmail());
        appendable.append("\",\"online\":");
        appendable.append(profile.isOnline() ? "true" : "false");
        appendable.append(",\"latitude\":");
        appendable.append(Double.toString(profile.getLatitude()));
        appendable.append(",\"longitude\":");
        appendable.append(Double.toString(profile.getLongitude()));
        appendable.append("}");
    }

    public static String profileToString(UserProfile profile) {
        return MessageFormat.format("[ {0}, \"{1}\", \"{2}\", {3}, {4}, {5} ]",
                profile.getId(), profile.getName(), profile.getEmail(),
                profile.isOnline(),
                profile.getLatitude(), profile.getLongitude());
    }

    /** Hidden ctor. */
    private DataUtils() {}
}
