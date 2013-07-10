package com.lge.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
	private Chat notToBeGCd;
	String hostname;
	String sniper_id;
	String sniper_password;
	String item_id;
	TextView status;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Intent intent = getIntent();
        if (intent != null) {
        	hostname = intent.getStringExtra("hostname");
        	sniper_id = intent.getStringExtra("sniper_id") + "@localhost";
        	sniper_password = intent.getStringExtra("sniper_password");
        	item_id = "auction-" + intent.getStringExtra("item_id") + "@localhost";
        }
        
		status = (TextView) findViewById(R.id.sniper_status);
        status.setText(R.string.status_joining);
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					joinAuction();
				}
				catch (XMPPException e) {
				}
			}

        }).start();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

    private void joinAuction() throws XMPPException {
		ConnectionConfiguration config = new ConnectionConfiguration(hostname, 5222);
		XMPPConnection connection = new XMPPConnection(config);
		connection.connect();
		connection.login(sniper_id, sniper_password);
		final Chat chat = connection.getChatManager().createChat(
				item_id, new MessageListener() {

					@Override
					public void processMessage(Chat aChat, Message message) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {

								status.setText(R.string.status_lost);
							}
						});
					}
				});
		this.notToBeGCd = chat;
		chat.sendMessage(JOIN_COMMAND_FORMAT);
	}



}
