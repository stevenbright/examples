package eg.sample.rson;

import com.google.gson.Gson;
import eg.sample.rson.domain.UserProfile;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Random;

import static eg.sample.rson.DataUtils.createProfile;
import static eg.sample.rson.DataUtils.profileToJson;
import static eg.sample.rson.DataUtils.profileToString;

/**
 * Entry point
 */
public class App {

    private static final UserProfile[] profiles = new UserProfile[10000];

    private static final String[] FIRST_NAMES = new String[] {
            "alex", "bob", "nick", "olaf", "olga", "ann", "joanna", "cole", "henry", "natalia"
    };

    private static final String[] SECOND_NAMES = new String[] {
            "black", "white", "brown", "rickes", "tames", "may", "jackson", "bates", "wilson"
    };

    private static void createProfiles() {
        final Random random = new Random();

        for (int i = 0; i < profiles.length; ++i) {
            final String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            profiles[i] = createProfile(i,
                    firstName + " " + SECOND_NAMES[random.nextInt(SECOND_NAMES.length)],
                    firstName + i + "@mail.com",
                    random.nextBoolean(),
                    random.nextDouble(),
                    random.nextDouble());
        }
    }

    private static void printProfiles() {
        for (int i = 0; i < profiles.length; ++i) {
            System.out.println("profile = " + profileToString(profiles[i]));
        }
    }

    private static void gsonTest(Appendable appendable) throws IOException {
        Gson gson = new Gson();
        for (int i = 0; i < profiles.length; ++i) {
            if (i > 0) {
                appendable.append(", ");
            }
            gson.toJson(profiles[i], appendable);
        }
    }

    private static void rsonTest(Appendable appendable) throws IOException {
        for (int i = 0; i < profiles.length; ++i) {
            if (i > 0) {
                appendable.append(", ");
            }
            profileToJson(profiles[i], appendable);
        }
    }

    private static void writerTest() throws IOException {
        StringWriter writer = new StringWriter();
        writer.append("\n#GSON: ");
        gsonTest(writer);
        writer.append("\n#RSON: ");
        rsonTest(writer);
        writer.append("\n");

        System.out.println(writer.toString());
    }

    public static void main(String[] args) {
        try {
            createProfiles();

            if (args.length < 10) {
                final HollowAppendable hollow = new HollowAppendable();

                // time == 1544
                {
                    long start = System.currentTimeMillis();
                    gsonTest(hollow);
                    System.out.println("#GSON time = " + (System.currentTimeMillis() - start));
                }

                // time == 40
                {
                    long start = System.currentTimeMillis();
                    rsonTest(hollow);
                    System.out.println("#RSON time = " + (System.currentTimeMillis() - start));
                }

            } else {
                System.out.println("NP");
                printProfiles();
                IntegerUtils.appendInteger(null, null, 0);

                writerTest();
                System.out.println("MP");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
