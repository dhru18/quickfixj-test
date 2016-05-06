package com.dfanaro.quickfixj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quickfix.*;
import quickfix.MessageFactory;
import quickfix.field.BeginString;
import quickfix.field.HeartBtInt;
import quickfix.field.ResetSeqNumFlag;
import quickfix.fixt11.Logon;

import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Hello world!
 */
public class App {

    private static final Logger LOG = LogManager.getLogger(App.class);

    private static final long FIX_SPEC_VERSION = 50;
    private static volatile boolean keepRunning = true;

    public static void main(String args[]) throws Exception {

        LOG.info("Starting application with FIX v{}.", FIX_SPEC_VERSION);

//        printClasspath();

        if (args.length != 1) {
            LOG.warn("Invalid program parameters. Usage: { acceptor | initiator }");
            return;
        }

        String fileName = args[0].equals("acceptor") ? "quickfixj/server.ini" : "quickfixj/client.ini";
        String socketType = args[0];

        Application application = new FIXApplication();

        SessionSettings settings = new SessionSettings(new FileInputStream(fileName));
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();

        if ("acceptor".equals(socketType)) {

            Acceptor acceptor = new SocketAcceptor(application, storeFactory, settings, logFactory, messageFactory);
            acceptor.start();
            while(keepRunning) {
                Thread.sleep(2000);
            }
            acceptor.stop();

        } if ("initiator".equals(socketType)) {

            Initiator initiator = new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
            initiator.start();
            SessionID sessionId = initiator.getSessions().get(0);
//            sendLogonRequest(sessionId);
            while(keepRunning) {
                Thread.sleep(2000);
            }
            initiator.stop();

        } else {
            LOG.warn("Invalid program parameters. Usage: fileName { acceptor | initiator }");
        }
    }

    public static void printClasspath() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls) {
            LOG.info(url.getFile());
        }
    }
}
