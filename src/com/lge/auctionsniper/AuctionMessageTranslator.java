package com.lge.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {

	private AuctionEventListener listener;

	public AuctionMessageTranslator(AuctionEventListener listener) {
		this.listener = listener;
		
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		String event = message.getBody();
		if(event.contains("CLOSE")){
			listener.auctionClosed();
		}
		if(event.contains("PRICE")){
			listener.currentPrice(190, 10);
		}
		
	}

	
	
	
	
	
	
	
	
	
	
}
