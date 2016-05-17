package com.dfanaro.quickfixj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;
import quickfix.field.*;
import quickfix.fix50.ExecutionReport;
import quickfix.fix50.MarketDataSnapshotFullRefresh;
import quickfix.fix50.NewOrderSingle;

import java.util.ArrayList;

/**
 * FIX Class with callback methods
 */
public class FIXApplication implements Application {

    private static final Logger LOG = LoggerFactory.getLogger(FIXApplication.class);

    private SocketAcceptor socketAcceptor;

    @Override
    public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {

    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {

        if (message.getHeader().getField(new StringField(35)).getValue().equals("D")) {
            NewOrderSingle newOrder = (NewOrderSingle) message;
            ExecutionReport response = new ExecutionReport(new OrderID(newOrder.getRefOrderID().getValue()), new ExecID("1"), new ExecType('0'), new OrdStatus('0'), newOrder.getSide(), new LeavesQty(0), new CumQty(0));

            MarketDataSnapshotFullRefresh mdMessage = new MarketDataSnapshotFullRefresh();
            mdMessage.set(new Symbol("MERV - XMEV - PESOS - 10D"));
            mdMessage.set(new SecurityExchange("ROFX"));
            mdMessage.set(new MDReqID("TOP1"));
            mdMessage.set(new NoMDEntries(0));

            try {
                Session.sendToTarget(response, sessionId);
                ArrayList<SessionID> sessions = socketAcceptor.getSessions();
                for (SessionID session : sessions) {
                    Session.sendToTarget(mdMessage, sessionId);
                }
            }
            catch (SessionNotFound e ) {}
        }
    }

    @Override
    public void onCreate(SessionID arg0) {}

    @Override
    public void onLogon(SessionID arg0) {}

    @Override
    public void onLogout(SessionID arg0) {}

    @Override
    public void toAdmin(Message message, SessionID sessionId) {}

    @Override
    public void toApp(Message arg0, SessionID arg1) throws DoNotSend {}

    public void setSocketAcceptor(SocketAcceptor socketAcceptor){
        this.socketAcceptor = socketAcceptor;
    }

//    public void onMessage(NewOrderSingle message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
//        LOG.info("Inside onMessage for New Order Single");
//        super.onMessage(message, sessionID);
//    }
//
//    public void onMessage(SecurityDefinition message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
//        LOG.info("Inside onMessage for SecurityDefinition");
//        super.onMessage(message, sessionID);
//    }
//
//    public void onMessage(Logon message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
//        LOG.info("Inside Logon Message");
//        super.onMessage(message, sessionID);
//    }
}
