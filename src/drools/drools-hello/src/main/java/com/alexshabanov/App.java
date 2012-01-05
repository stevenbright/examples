package com.alexshabanov;

import com.alexshabanov.model.HelloMessage;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.event.rule.DebugAgendaEventListener;
import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Entry point.
 */
public final class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, drools!");

        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
                .newKnowledgeBuilder();

        // this will parse and compile in one step
        kbuilder.add(ResourceFactory.newClassPathResource("HelloWorld.drl",
                App.class),
                ResourceType.DRL);

        // Check the builder for errors
        if (kbuilder.hasErrors()) {
            System.out.println(kbuilder.getErrors().toString());
            throw new RuntimeException("Unable to compile \"HelloWorld.drl\".");
        }

        // get the compiled packages (which are serializable)
        final Collection<KnowledgePackage> pkgs = kbuilder
                .getKnowledgePackages();

        // add the packages to a knowledgebase (deploy the knowledge packages).
        final KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages( pkgs );

        final StatefulKnowledgeSession ksession = kbase
                .newStatefulKnowledgeSession();
        ksession.setGlobal( "list",
                new ArrayList<Object>() );

        ksession.addEventListener(new DebugAgendaEventListener());
        ksession.addEventListener(new DebugWorkingMemoryEventListener());

        // setup the audit logging
        // Remove comment to use FileLogger
        // KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger( ksession, "./helloworld" );

        // Remove comment to use ThreadedFileLogger so audit view reflects events whilst debugging
        // KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger( ksession, "./helloworld", 1000 );

        final HelloMessage message = new HelloMessage();
        message.setMessage("Hello World");
        message.setStatus(HelloMessage.HELLO);
        ksession.insert(message);

        ksession.fireAllRules();

        // Remove comment if using logging
        // logger.close();

        ksession.dispose();

        System.out.println("-- END SESSION --");
    }
}
