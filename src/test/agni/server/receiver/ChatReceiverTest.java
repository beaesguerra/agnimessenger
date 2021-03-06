package test.agni.server.receiver;

import agni.server.receiver.ChatReceiver;
import agni.server.receiver.ChatListener;

import java.nio.ByteBuffer;
import java.sql.SQLException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ChatReceiverTest {
    final int headerBytes = 5;
    final byte type = 0x05;
    final String testIp = "192.168.1.1";
    final String testString = "Hello World!";
    byte[] testArray = null; 
    byte[] bufferArray = null;
    int totalMessageLength; 
    ByteBuffer testBuffer = null;

    Mockery context = new Mockery();
    ChatListener mockChatListener;
    ChatReceiver chatReceiver;
    
    @Before
    public void setUp() throws Exception {
        //prepare the message
        testArray = testString.getBytes("us-ascii");
        totalMessageLength = (headerBytes + testArray.length);

        
        //populate message buffer
        testBuffer = ByteBuffer.wrap(new byte[100]);
        testBuffer.putInt(totalMessageLength);
        testBuffer.put(type);
        testBuffer.put(testArray);
           
        testBuffer.flip();
        int length =  testBuffer.remaining();
        bufferArray = new byte[length];
        testBuffer.get(bufferArray);
        chatReceiver = new ChatReceiver();
        
        mockChatListener = context.mock(ChatListener.class);
        chatReceiver.register(mockChatListener);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void correctInputTest() {
        try {
			context.checking(new Expectations() {{
			    oneOf(mockChatListener).chatRequest("192.168.1.1", testString);
			}});
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			chatReceiver.receiveMessage(testIp, bufferArray);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        context.assertIsSatisfied();
    }
    
    
//    @Test(expected = NullPointerException.class)
//    public void nullIpTest() {
//        context.checking(new Expectations() {{
//            final String expectedIp = "192.168.1.1";
//            oneOf(mockChatListener).chatRequest(expectedIp, testString);
//        }});
//        chatReceiver.receiveMessage(null, bufferArray);
//        context.assertIsSatisfied();
//    }
//    
//    @Test(expected = NullPointerException.class)
//    public void nullMessageTest() {
//        context.checking(new Expectations() {{
//            final String expectedIp = "192.168.1.1";
//            oneOf(mockChatListener).chatRequest(expectedIp, testString);
//        }});
//        chatReceiver.receiveMessage(testIp, null);
//        context.assertIsSatisfied();
//    }
//    
}

