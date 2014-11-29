package net.glassup.example1;

import glassup.service.GlassUpAgent;
import glassup.service.GlassUpAgentInterface.ConnectionListener;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	//Used to send the message to the handler class
	public final int MSG_WHAT_SEND_CONFIG	= 1;
	
	//Used to get the connection state and the configuration state.
	public static boolean mServiceConnected;
	public static boolean mAppConfigured;
	
	/*The GlassUp agent and its listners*/
	public static GlassUpAgent mAgent;
	AgentEventListner eventListener;
	AgentContentListener contentResultListener;
	ConnectionListener mConnListener;
	ConfigurationHandler mHandler;
	
	/*The conter id*/
	int counter=0;
	
	EditText txtContent;
	Button btnSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtContent = (EditText)findViewById(R.id.txt_Content);
		btnSend = (Button)findViewById(R.id.btn_Send);
		btnSend.setOnClickListener(this);
		
		/*The listners declariations*/
		eventListener = new AgentEventListner();
		contentResultListener = new AgentContentListener();
		mConnListener = new mConnectionListener();
		mAgent = new GlassUpAgent();
		/*The agent follows the Activity lifecycle*/
		mAgent.onCreate(this);
		
		/*Set the agent's listners*/
		mAgent.setEventListener(eventListener);
		mAgent.setContentResultListener(contentResultListener);
		mAgent.setConnectionListener(mConnListener);
		
		/*Declare the configuration handler*/
		mHandler = new ConfigurationHandler();
		
		mAppConfigured = mAgent.isConfigured(); // the agent is configured?
		if (!mAppConfigured){		
			/*Not configured*/
			Log.d("TAG","App not configured, Scheduling send configure");
			mAppConfigured = false;
			/*Send the configuration message*/
			mHandler.sendEmptyMessage(MSG_WHAT_SEND_CONFIG);
		}	
		else {
			/*Already configured*/
			Log.d("TAG","App Already configured");
			mAppConfigured = true;
			Toast.makeText(getApplicationContext(), "App already configured", Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		/*The agent follows the Activity lifecycle*/
		mAgent.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		/*The agent follows the Activity lifecycle*/
		mAgent.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/*The agent follows the Activity lifecycle*/
		mAgent.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_Send:
			/*Send the content to the GlassUp Service*/
			mAgent.sendContent(counter++, 0, null, new String[]{txtContent.getText().toString()});
			break;

		default:
			break;
		}
		
	}
	
	

}
