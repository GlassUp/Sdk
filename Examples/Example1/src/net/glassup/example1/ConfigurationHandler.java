package net.glassup.example1;


import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author Blescia Antonio
 * This class send a message to the GlassUpService and set its configuration.
 */
public class ConfigurationHandler extends Handler{
	public static final int	MSG_WHAT_SEND_CONFIG	= 1;
	
	@Override
	public void handleMessage(Message msg)
	{
		super.handleMessage(msg);
		if (msg.what == MSG_WHAT_SEND_CONFIG)
		{
			removeMessages(MSG_WHAT_SEND_CONFIG);

			MainActivity.mAppConfigured = MainActivity.mAgent.isConfigured();
			
			if (!MainActivity.mAppConfigured)
			{
				if (MainActivity.mServiceConnected){
					if (MainActivity.mAgent.isConnected())
					{
						MainActivity.mAgent.sendConfigure(new int[]{R.drawable.ic_launcher});
					}
				}
				sendEmptyMessageDelayed(MSG_WHAT_SEND_CONFIG, 10000);
			}

			else 
			{
				//Toast.makeText(getApplicationContext(), "App configured", Toast.LENGTH_LONG).show();					
			}
		}

	}
}
