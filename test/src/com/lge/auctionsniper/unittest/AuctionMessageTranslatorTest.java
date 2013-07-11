package com.lge.auctionsniper.unittest;

import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;

import com.lge.auctionsniper.AuctionEventListener;
import com.lge.auctionsniper.AuctionMessageTranslator;

import junit.framework.TestCase;

public class AuctionMessageTranslatorTest extends TestCase {

	private final Mockery context = new Mockery();
	private final AuctionEventListener listener = 
			context.mock(AuctionEventListener.class);
	
	private final AuctionMessageTranslator translator =
			new AuctionMessageTranslator(listener); 
	
	public void testNotifiesAuctionClosedWhenCLOSEMessageReceived() throws Exception {
		
		context.checking(new Expectations() {{
			oneOf(listener).auctionClosed();
		}});
		
		
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");
		translator.processMessage(null, message);
		
		context.assertIsSatisfied();
		
	}
	
	public void testNotifiesCurrentPriceWhenPRICEMessageReceived() throws Exception {
		
		context.checking(new Expectations() {{
			oneOf(listener).currentPrice(190,10);
		}});
		
		
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 190; Increment: 10; Bidder: Other;");
		translator.processMessage(null, message);
		
		context.assertIsSatisfied();
		
	}
}
		
		
		




		
		
		
		
		
		
		
		
