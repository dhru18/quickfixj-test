package com.dfanaro.quickfixj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quickfix.*;
import quickfix.field.*;
import quickfix.fix50.NewOrderSingle;

import java.util.Date;
import java.util.Scanner;

/**
 * TODO: complete with class description
 */
public class SenderApp {

    private static final Logger LOG = LogManager.getLogger(SenderApp.class);

//    private Map<Session, >

    public static void main(String[] args) throws ConfigError, InterruptedException, SessionNotFound {

        SessionSettings settings = new SessionSettings("quickfixj/client.ini");
        Application myApp = new FIXApplication();
        FileStoreFactory fileStoreFactory = new FileStoreFactory(settings);
        ScreenLogFactory screenLogFactory = new ScreenLogFactory(settings);
        DefaultMessageFactory msgFactory = new DefaultMessageFactory();
        SocketInitiator initiator = new SocketInitiator(myApp, fileStoreFactory, settings, screenLogFactory, msgFactory);

        initiator.start();

        Thread.sleep(5000);

        SessionID sessionID = new SessionID("FIXT.1.1", "drojas", "ROFX");

        for (int i = 0; i < 10000; i++) {
            NewOrderSingle order = new NewOrderSingle(new ClOrdID("DLF"), new Side(Side.BUY), new TransactTime(new Date()), new OrdType(OrdType.LIMIT));
            order.set(new OrderQty(45.00));
            order.set(new Price(25.40));
            order.set(new Symbol("BHP"));
            order.set(new RefOrderID("SARASA"));
            Session.sendToTarget(order, sessionID);
        }

        Scanner reader = new Scanner(System.in);
        LOG.info("Press <enter> to quit");
        reader.nextLine();
        initiator.stop();
    }
}
