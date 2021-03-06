package test.agni.server.receiver;

import java.nio.ByteBuffer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agni.server.receiver.InfoRequestReceiver;
import agni.server.receiver.InfoListener;


public class InfoRequestReceiverTest {
    final int headerBytes = 5;
    final byte type = 0x03;
    final String testIp = "192.168.1.1";
    final byte testStatus = 0x01;
    int totalMessageLength; 
    ByteBuffer testBuffer = null;
    byte[] bufferArray = null;

    Mockery context = new Mockery();
    InfoListener mockInfoListener;
    InfoRequestReceiver infoReceiver;
    
    @Before
    public void setUp() throws Exception {
        //prepare the message
        totalMessageLength = (headerBytes + 1);

        
        //populate message buffer
        testBuffer = ByteBuffer.wrap(new byte[100]);
        testBuffer.putInt(totalMessageLength);
        testBuffer.put(type);
        testBuffer.put(testStatus);
        
        testBuffer.flip();
        int length =  testBuffer.remaining();
        bufferArray = new byte[length];
        testBuffer.get(bufferArray);
        infoReceiver = new InfoRequestReceiver();
        
        mockInfoListener = context.mock(InfoListener.class);
        infoReceiver.register(mockInfoListener);
    }

    @After
    public void tearDown() throws Exception {

    }

//    @Test
//    public void correctInputTest() {
//        context.checking(new Expectations() {{
//            oneOf(mockInfoListener).infoRequest("192.168.1.1", testStatus);
//        }});
//        infoReceiver.receiveMessage(testIp, bufferArray);
//        context.assertIsSatisfied();
//    }
//    
//    
//    @Test(expected = NullPointerException.class)
//    public void nullIpTest() {
//        context.checking(new Expectations() {{
//            final String expectedIp = "192.168.1.1";
//            oneOf(mockInfoListener).infoRequest(expectedIp, testStatus);
//        }});
//        infoReceiver.receiveMessage(null, bufferArray);
//        context.assertIsSatisfied();
//    }
//    
//    @Test(expected = NullPointerException.class)
//    public void nullMessageTest() {
//        context.checking(new Expectations() {{
//            final String expectedIp = "192.168.1.1";
//            oneOf(mockInfoListener).infoRequest(expectedIp, testStatus);
//        }});
//        infoReceiver.receiveMessage(testIp, null);
//        context.assertIsSatisfied();
//    }
    
}

