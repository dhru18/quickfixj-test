package com.dfanaro.quickfixj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;
import quickfix.field.*;
import quickfix.fix50.ExecutionReport;
import quickfix.fix50.NewOrderSingle;

//import quickfix.fix50.SecurityDefinition;
//import quickfix.fixt11.Logon;

/**
 * FIX Class with callback methods
 */
public class FIXApplication implements Application {

    private static final Logger LOG = LoggerFactory.getLogger(FIXApplication.class);

    @Override
    public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
//        LOG.info("Successfully called fromAdmin for sessionId : " + arg0);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
//        LOG.info("Successfully called fromApp for sessionId : " + message);



        if (message.getHeader().getField(new StringField(35)).getValue().equals("D")) {
            NewOrderSingle newOrder = (NewOrderSingle) message;
            ExecutionReport response = new ExecutionReport(new OrderID(newOrder.getRefOrderID().getValue()), new ExecID("1"), new ExecType('0'), new OrdStatus('0'), newOrder.getSide(), new LeavesQty(0), new CumQty(0));
            try {
//                LOG.info("Sending Execution Report");
                Session.sendToTarget(response, sessionId);
            }
            catch (SessionNotFound e ) {}
        }
    }

    @Override
    public void onCreate(SessionID arg0) {
//        LOG.info("Successfully called onCreate for sessionId : " + arg0);
    }

    @Override
    public void onLogon(SessionID arg0) {
//        LOG.info("Successfully logged on for sessionId : " + arg0);
    }

    @Override
    public void onLogout(SessionID arg0) {
//        LOG.info("Successfully logged out for sessionId : " + arg0);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
//        LOG.info("Inside toAdmin");
    }

    @Override
    public void toApp(Message arg0, SessionID arg1) throws DoNotSend {
//        LOG.info("Message : " + arg0.toString().replace("\u0001", " | ") + " for sessionid : " + arg1);
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
