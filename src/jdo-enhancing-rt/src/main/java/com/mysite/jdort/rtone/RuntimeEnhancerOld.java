package com.mysite.jdort.rtone;

import com.mysite.jdort.model.Account;
/*
import com.sun.xml.internal.ws.org.objectweb.asm.*;
*/
import com.mysite.jdort.utils.RedefinableClassLoader;
import org.apache.log4j.Logger;

import javax.jdo.JDOEnhancer;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.metadata.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates all the stuff to do runtime enhancement
 */
public class RuntimeEnhancerOld {

    private static Logger LOG = Logger.getLogger(RuntimeEnhancerOld.class);

    public static byte[] createClass(String className) {
        throw new UnsupportedOperationException("Uncomment createClass definition given below --> " + className);
    }
                          /*
    public static byte[] createClass(String className) {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;
        FieldVisitor fv;

        String classNameASM = className.replace('.', '/');
        cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, classNameASM, null,
                "java/lang/Object", new String[]{});

        fv = cw.visitField(Opcodes.ACC_PRIVATE, "name", "Ljava/lang/String;", null, null);
        fv.visitEnd();

        // Default Constructor
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
            mv.visitInsn(Opcodes.RETURN);

            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "L" + classNameASM + ";", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        // String getName()
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "getName", "()Ljava/lang/String;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, classNameASM, "name", "Ljava/lang/String;");
            mv.visitInsn(Opcodes.ARETURN);

            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "L" + classNameASM + ";", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        // void setName(String)
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "setName", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitFieldInsn(Opcodes.PUTFIELD, classNameASM, "name", "Ljava/lang/String;");
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitInsn(Opcodes.RETURN);

            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "L" + classNameASM + ";", null, l0, l2, 0);
            mv.visitLocalVariable("s", "Ljava/lang/String;", null, l0, l2, 1);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }

        // Object getProperty(String)
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "getProperty", "(Ljava/lang/String;)Ljava/lang/Object;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitInsn(Opcodes.ACONST_NULL);
            mv.visitVarInsn(Opcodes.ASTORE, 2);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitLdcInsn("name");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z");
            Label l2 = new Label();
            mv.visitJumpInsn(Opcodes.IFEQ, l2);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, classNameASM, "name", "Ljava/lang/String;");
            mv.visitVarInsn(Opcodes.ASTORE, 2);
            mv.visitLabel(l2);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitInsn(Opcodes.ARETURN);

            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLocalVariable("this", "L" + classNameASM + ";", null, l0, l4, 0);
            mv.visitLocalVariable("propertyName", "Ljava/lang/String;", null, l0, l4, 1);
            mv.visitLocalVariable("o", "Ljava/lang/Object;", null, l1, l4, 2);
            mv.visitMaxs(2, 3);
            mv.visitEnd();
        }

        // void setProperty(String, Object)
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "setProperty", "(Ljava/lang/String;Ljava/lang/Object;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitLdcInsn("name");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z");
            Label l1 = new Label();
            mv.visitJumpInsn(Opcodes.IFEQ, l1);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/String");
            mv.visitFieldInsn(Opcodes.PUTFIELD, classNameASM, "name", "Ljava/lang/String;");
            mv.visitLabel(l1);
            mv.visitInsn(Opcodes.RETURN);

            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("this", "L" + classNameASM + ";", null, l0, l3, 0);
            mv.visitLocalVariable("propertyName", "Ljava/lang/String;", null, l0, l3, 1);
            mv.visitLocalVariable("value", "Ljava/lang/Object;", null, l0, l3, 2);
            mv.visitMaxs(2, 3);
            mv.visitEnd();
        }

        return cw.toByteArray();
    }              */



    public static void populateMetadata(JDOMetadata jdomd) {
        PackageMetadata pmd = jdomd.newPackageMetadata("com.mysite.jdort.model");

        // account table
        {
            ClassMetadata cmd = pmd.newClassMetadata("Account");
            cmd.setTable("ACCOUNTZ").setDetachable(true).setIdentityType(javax.jdo.annotations.IdentityType.DATASTORE);
            cmd.setPersistenceModifier(javax.jdo.metadata.ClassPersistenceModifier.PERSISTENCE_CAPABLE);

            FieldMetadata fmd = cmd.newFieldMetadata("username");
            fmd.setNullValue(javax.jdo.annotations.NullValue.DEFAULT).setColumn("USER_NAME").setIndexed(true).setUnique(true);

            InheritanceMetadata inhmd = cmd.newInheritanceMetadata();
            inhmd.setStrategy(javax.jdo.annotations.InheritanceStrategy.NEW_TABLE);

//        DiscriminatorMetadata dmd = inhmd.newDiscriminatorMetadata();
//        dmd.setColumn("disc").setValue("Client").setStrategy(javax.jdo.annotations.DiscriminatorStrategy.VALUE_MAP).setIndexed(Indexed.TRUE);

            VersionMetadata vermd = cmd.newVersionMetadata();
            vermd.setStrategy(javax.jdo.annotations.VersionStrategy.VERSION_NUMBER).setColumn("version").setIndexed(Indexed.TRUE);
        }

        // blog item table
        {
            ClassMetadata cmd = pmd.newClassMetadata("BlogItem");
            cmd.setTable("BLOG_ITEM").setDetachable(true).setIdentityType(javax.jdo.annotations.IdentityType.DATASTORE);
            cmd.setPersistenceModifier(javax.jdo.metadata.ClassPersistenceModifier.PERSISTENCE_CAPABLE);
        }
    }

    public static void defineClass(RedefinableClassLoader classLoader, String className) {
        final byte[] classBytes = createClass(className);
        classLoader.defineClass(className, classBytes);
    }

    public static PersistenceManagerFactory createPMF(int dummy) {
        try {
            final RedefinableClassLoader classLoader = new RedefinableClassLoader(
                    Thread.currentThread().getContextClassLoader());

            if (dummy == 456) {
                defineClass(classLoader, "com.mysite.jdort.model.Account");
                defineClass(classLoader, "com.mysite.jdort.model.BlogItem");
            }

            final Map<String, Object> props = new HashMap<String, Object>();

            props.put("javax.jdo.PersistenceManagerFactoryClass","org.datanucleus.jdo.JDOPersistenceManagerFactory");
            props.put("datanucleus.ConnectionDriverName","org.hsqldb.jdbcDriver");
            props.put("datanucleus.ConnectionURL","jdbc:hsqldb:mem:jdort-db");
            props.put("datanucleus.ConnectionUserName","sa");
            props.put("datanucleus.ConnectionPassword","");
            props.put("datanucleus.autoStartMechanism","None");
            props.put("datanucleus.autoCreateSchema","true");
            props.put("datanucleus.autoCreateTables","true");
            props.put("datanucleus.autoCreateColumns","true");
            props.put("datanucleus.primaryClassLoader ", classLoader);
            props.put("datanucleus.rdbms.stringDefaultLength","255");

            final JDOEnhancer enhancer = JDOHelper.getEnhancer();
            enhancer.setVerbose(true);
            //enhancer.addPersistenceUnit("JdoSampleProgPersistenceUnit");
            enhancer.addClasses("com.mysite.jdort.model.Account", "com.mysite.jdort.model.BlogItem");
            enhancer.enhance();

            PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(props);
//            final JDOMetadata jdomd = pmf.newMetadata();
//            populateMetadata(jdomd);
//            pmf.registerMetadata(jdomd);

            final PersistenceManager pm = pmf.getPersistenceManager();
//            final ClassLoaderResolver clr = ((JDOPersistenceManagerFactory)pmf).getOMFContext().getClassLoaderResolver(classLoader);

//            final Class accountClass = clr.classForName("com.mysite.jdort.model.Account");
//            final Object o = accountClass.newInstance();

            pm.currentTransaction().begin();
            Object p = new Account();
            pm.makePersistent(p);
            pm.currentTransaction().commit();

            return pmf;
        } catch (Exception e) {
            LOG.error(e);
            throw new RuntimeException("PMF creation error", e);
        }
    }

    /**
     * Hidden
     */
    private RuntimeEnhancerOld() {}
}
