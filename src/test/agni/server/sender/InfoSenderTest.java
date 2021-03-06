package test.agni.server.sender;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import agni.server.communication.I_MessageSender;
import agni.server.sender.InfoSender;
import test.AgniTestUtilities;

public class InfoSenderTest {

    final String TEST_IP = "localhost";
    final String INFO_BYTE_HEX = "08";

    // ASCII converted to hex at http://www.asciitohex.com/
    final String TEST_INFO_MESSAGE = "Message from the server!";
    final String TEST_INFO_MESSAGE_HEX = "4d6573736167652066726f6d207468652073657276657221";

    I_MessageSender messageSender;
    InfoSender infoSender;
    Mockery context;

    @Before
    public void setup() {
        this.context = new Mockery();
        this.messageSender = context.mock(I_MessageSender.class);
        this.infoSender = new InfoSender(messageSender);
    }

    @Test
    public void normalInfo() {
    	final String LENGTH_HEX = "0000001d"; // 29
        context.checking(new Expectations() {{
            final byte[] expectedMessage = AgniTestUtilities.hexStringToByteArray(LENGTH_HEX +
            																	  INFO_BYTE_HEX +
            																	  TEST_INFO_MESSAGE_HEX);
            oneOf(messageSender).sendMessage(TEST_IP, expectedMessage);
        }});

        infoSender.sendInfo(TEST_IP, TEST_INFO_MESSAGE);
        context.assertIsSatisfied();
    }

    @Test(expected=NullPointerException.class)
    public void nullIp() {
        infoSender.sendInfo(null, TEST_INFO_MESSAGE);
    }

    @Test(expected=NullPointerException.class)
    public void nullInfoMessage() {
        infoSender.sendInfo(TEST_IP, null);
    }
}
