package com.alexshabanov.sample.springProps;

import com.alexshabanov.sample.springProps.service.FooService;
import org.cojen.tupl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Entry point.
 */
public final class App implements Runnable {
  private final Logger log;
  private final FooService fooService;

  public App(FooService fooService) {
    log = LoggerFactory.getLogger(App.class);
    this.fooService = fooService;
  }

  public static void main(String[] args) {
    initContextOverride(Collections.unmodifiableList(Arrays.asList(args)));

    final String[] contextPaths = {"classpath:/tuplDemo/spring/app-context.xml"};
    // NOTE: DO NOT refresh context here as it will result in spring not being able to resolve all the imports
    try (final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(contextPaths, false)) {
      // this makes environment properties known to spring, it should be called PRIOR to app context refresh
      initialize(context);
      context.refresh();
      context.start();

      // get Runnable application bean and run it
      context.getBean("app", Runnable.class).run();
    }
  }

  @Override
  public void run() {
    try {
      demoDatabase();
    } catch (Exception e) {
      throw new RuntimeException("demo DB", e);
    }

    log.info("Application started");
    fooService.bar();
  }

  //
  // Private
  //

  private static final String KEY = "xlJ0V9XOzbMhMBch";

  private void demoDatabase() throws Exception {
    // See https://github.com/cojen/Tupl.git
    final DatabaseConfig config = new DatabaseConfig();
    config.baseFilePath("/tmp/tuplDemo");
    config.maxCacheSize(1_000_000L);
    config.durabilityMode(DurabilityMode.NO_FLUSH);

    try (final Database db = Database.open(config)) {
      try (final Index userIndex = db.openIndex("users")) {
        final Transaction tx = db.newTransaction();
        try {
          workWithPerson(tx, userIndex);
          tx.commit();
        } finally {
          tx.exit();
        }
      }
    }
  }

  private void workWithPerson(Transaction tx, Index userIndex) throws IOException {
    final byte[] key = KEY.getBytes(StandardCharsets.ISO_8859_1);
    final byte[] output = userIndex.load(tx, key);
    if (output != null) {
      final Person person = fromBytes(new Person(), output);
      log.info("Found existing person={}", person);
      return;
    }

    log.info("Existing person has not been found, creating a new one...");
    userIndex.store(tx, key, toBytes(newSamplePerson()));
    log.info("Person has been stored, restart application to see the changes");
  }

  public static byte[] toBytes(EstimationAwareExternalizable object) throws IOException {
    try (final ByteArrayOutputStream baos = new ByteArrayOutputStream(object.getEstimatedRawSize())) {
      try (final ObjectOutputStream oos = new ObjectOutputStream(baos)) {
        object.writeExternal(oos);
      }
      return baos.toByteArray();
    }
  }

  public static <T extends Externalizable> T fromBytes(T prototype, byte[] input) throws IOException {
    try (final ByteArrayInputStream bais = new ByteArrayInputStream(input)) {
      try (final ObjectInputStream ois = new ObjectInputStream(bais)) {
        prototype.readExternal(ois);
        return prototype;
      } catch (ClassNotFoundException e) {
        throw new IOException("Unexpected error while deserializing " + prototype.getClass(), e);
      }
    }
  }

  private static Person newSamplePerson() {
    final Person person = new Person();
    person.id = KEY;
    person.name = "eva";
    person.age = 25;
    return person;
  }

  private interface EstimationAwareExternalizable extends Externalizable {
    int getEstimatedRawSize();
  }

  private static final class Person implements EstimationAwareExternalizable {
    public static final int VERSION = 1821346954;

    String id;
    String name;
    int age;

    public Person() {
    }

    @Override
    public int getEstimatedRawSize() {
      return 20 + id.length() + name.length();
    }

    @Override
    public void writeExternal(@Nonnull ObjectOutput out) throws IOException {
      out.writeInt(VERSION);
      out.writeUTF(id);
      out.writeUTF(name);
      out.writeInt(age);
    }

    @Override
    public void readExternal(@Nonnull ObjectInput in) throws IOException, ClassNotFoundException {
      final int version = in.readInt();
      if (version != VERSION) {
        throw new IOException("Version of this class does not match, actual=" + version + ", expected=" + VERSION);
      }

      id = in.readUTF();
      name = in.readUTF();
      age = in.readInt();
    }

    @Override
    public String toString() {
      return "{id=" + id + ", name=" + name + ", age=" + age + '}';
    }
  }

  private static void initialize(ConfigurableApplicationContext applicationContext) {
    final List<Resource> resources = new ArrayList<>(2);

    // add core.properties
    resources.add(applicationContext.getResource("classpath:/tuplDemo/core.properties"));

    // add context override
    final String contextOverridePath = System.getProperty("app.contextOverride");
    if (contextOverridePath != null) {
      resources.add(applicationContext.getResource(contextOverridePath));
    }

    try {
      // fill properties
      final Properties properties = new Properties();

      for (final Resource resource : resources) {
        if (!resource.exists()) {
          throw new IllegalStateException("Resource=" + resource.getDescription() + " does not exist");
        }
        PropertiesLoaderUtils.fillProperties(properties, resource);
      }

      // initialize property source based on these properties
      final PropertiesPropertySource propertySource = new PropertiesPropertySource("default", properties);

      // actually insert property source
      applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
    } catch (IOException e) {
      throw new BeanInitializationException("Error while initializing context with properties", e);
    }
  }

  private static void initContextOverride(List<String> args) {
    int overridePropIndex = args.indexOf("--override") + 1;
    if (overridePropIndex > 0) {
      System.setProperty("app.contextOverride", args.get(overridePropIndex));
    }
  }
}
