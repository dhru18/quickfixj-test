package com.dfanaro.quickfixj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

import java.util.Scanner;

/**
 * TODO: complete with class description
 */
public class ReceiverApp {

//    private static final Logger LOG = LoggerFactory.getLogger(ReceiverApp.class);

    public static void main(String[] args) throws ConfigError {

        SessionSettings settings = new SessionSettings("quickfixj/server.ini");

        FIXApplication myApp = new FIXApplication();
        FileStoreFactory fileStoreFactory = new FileStoreFactory(settings);
        ScreenLogFactory screenLogFactory = new ScreenLogFactory(settings);
        DefaultMessageFactory msgFactory = new DefaultMessageFactory();

        SocketAcceptor acceptor = new SocketAcceptor(myApp, fileStoreFactory, settings, screenLogFactory, msgFactory);

        myApp.setSocketAcceptor(acceptor);
        acceptor.start();

        Scanner reader = new Scanner(System.in);
//        LOG.info("Press <enter> to quit");
        reader.nextLine();
        acceptor.stop();
    }

}
