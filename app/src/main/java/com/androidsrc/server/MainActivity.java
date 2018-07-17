package com.androidsrc.server;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements TickCount{
	Timer t;

	Server server;
	TextView infoip, msg;
	ArrayList<DataModel>dataModelsList=new ArrayList<>();
	Button btn_stop;
	ListView listView;
	MindfulMorningListAdapter mindfulMorningListAdapter;
	MediaPlayer player;
	int tickCount=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mm_list);
		infoip = (TextView) findViewById(R.id.infoip);
		msg = (TextView) findViewById(R.id.msg);
		btn_stop = (Button)findViewById(R.id.btn_stop);
		server = new Server(this);
		infoip.setText(server.getIpAddress()+":"+server.getPort());

		listView = (ListView)findViewById(R.id.activity_list_view);

		/*for (int i = 0 ; i<2;i++){
			DataModel dataModel = new DataModel();
			dataModel.setName("Tarun "+i);
			dataModel.setTask("Tea "+i);
			dataModelsList.add(dataModel);

		}*/
		mindfulMorningListAdapter = new MindfulMorningListAdapter(this,dataModelsList,this);
		listView.setAdapter(mindfulMorningListAdapter);
		btn_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (player!=null && player.isPlaying()){
					player.stop();
					t.cancel();
				}
			}
		});

		/*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				//if (dataModelsList.get(i).isChecked()){
					dataModelsList.get(i).setChecked(!dataModelsList.get(i).isChecked());
				    mindfulMorningListAdapter.notifyDataSetChanged();
				//}
			}
		});*/
	}

	public void updateList(DataModel dataModel){

		if (!findCustomerByid(dataModel)){
			dataModelsList.add(dataModelsList.size()-tickCount,dataModel);
			mindfulMorningListAdapter.notifyDataSetChanged();
			playMusic();
		}

	}
	private void playMusic(){
		t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				if (dataModelsList.size()>tickCount){
					if (player!=null && player.isPlaying()){
						player.stop();
					}
					Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
					player = MediaPlayer.create(MainActivity.this, notification);
					player.setLooping(false);
					player.start();
				}

			}
		}, 0, 1000*1*60);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		server.onDestroy();
	}


	@Override
	public void setTickCount() {
		tickCount++;
	}

	@Override
	public void removeTickCount() {
		if (tickCount>0)
		tickCount--;
	}


	boolean findCustomerByid(DataModel dataModel){
		for (DataModel customer : dataModelsList) {
			if (customer.getName().equalsIgnoreCase(dataModel.getName()) && !customer.isChecked()) {
				return true;
			}
		}
		return false;
	}
}