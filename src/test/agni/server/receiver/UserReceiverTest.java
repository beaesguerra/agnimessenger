package test.agni.server.receiver;

import java.nio.ByteBuffer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agni.server.receiver.UserListener;
import agni.server.receiver.UserReceiver;

public class UserReceiverTest {

	final int headerBytes = 5;
	final byte type = 0x01;
	final String testIp = "192.168.1.1";
	final byte testAction = 0x01;
	int totalMessageLength; 
	ByteBuffer testBuffer = null;

	Mockery context = new Mockery();
	UserListener mockUserListener;
	UserReceiver userReceiver;
	
	@Before
	public void setUp() throws Exception {
		//prepare the message
		totalMessageLength = (headerBytes + 1);

		
		//populate message buffer
		testBuffer = ByteBuffer.wrap(new byte[100]);
		testBuffer.putInt(totalMessageLength);
		testBuffer.put(type);
		testBuffer.put(testAction);

		userReceiver = new UserReceiver();
		
		mockUserListener = context.mock(UserListener.class);
		userReceiver.register(mockUserListener);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void correctInputTest() {
		context.checking(new Expectations() {{
			oneOf(mockUserListener).infoRequest("192.168.1.1", testAction);
		}});
		userReceiver.receiveMessage(testIp, testBuffer);
		context.assertIsSatisfied();
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void nullIpTest() {
		context.checking(new Expectations() {{
			final String expectedIp = "192.168.1.1";
			oneOf(mockUserListener).infoRequest(expectedIp, testAction);
		}});
		userReceiver.receiveMessage(null, testBuffer);
		context.assertIsSatisfied();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullMessageTest() {
		context.checking(new Expectations() {{
			final String expectedIp = "192.168.1.1";
			oneOf(mockUserListener).infoRequest(expectedIp, testAction);
		}});
		userReceiver.receiveMessage(testIp, null);
		context.assertIsSatisfied();
	}
	
}
