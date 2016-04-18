package com.dfanaro.quickfixj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quickfix.*;

import java.util.Scanner;

/**
 * TODO: complete with class description
 */
public class ReceiverApp {

    private static final Logger LOG = LogManager.getLogger(ReceiverApp.class);

    public static void main(String[] args) throws ConfigError {
        SessionSettings settings = new SessionSettings("quickfixj/server.ini");
        Application myApp = new FIXApplication();
        FileStoreFactory fileStoreFactory = new FileStoreFactory(settings);
        ScreenLogFactory screenLogFactory = new ScreenLogFactory(settings);
        DefaultMessageFactory msgFactory = new DefaultMessageFactory();
        SocketAcceptor acceptor = new SocketAcceptor(myApp, fileStoreFactory, settings, screenLogFactory, msgFactory);
        acceptor.start();
        Scanner reader = new Scanner(System.in);
        LOG.info("Press <enter> to quit...");
        reader.nextLine();
        acceptor.stop();
    }

}
