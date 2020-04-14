package com.switchrecorder;

import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.Vibrator;
import java.util.Timer;
import java.util.TimerTask;
import android.media.SoundPool;
import android.view.View;
import android.widget.CompoundButton;

public class MainActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private double waitsecs = 0;
	private double vibrationOn = 0;
	private double soundOn = 0;
	private double timerOn = 0;
	private double timerDoneSecs = 0;
	private double timerDoneLaps = 0;
	
	private LinearLayout linear1;
	private TextView textview2;
	private TextView textview3;
	private TextView timetext;
	private TextView textview10;
	private LinearLayout linear9;
	private ScrollView vscroll2;
	private LinearLayout linear8;
	private LinearLayout linear3;
	private LinearLayout linear7;
	private LinearLayout linear4;
	private LinearLayout linear6;
	private Button startstop;
	private LinearLayout linear11;
	private Button resettimer;
	private TextView textview4;
	private SeekBar seekbar1;
	private TextView secondsalerttext;
	private Switch audioswitch;
	private Switch vibrationon;
	private TextView textview9;
	private LinearLayout linear2;
	private TextView textview7;
	private Switch themeswitch;
	private TextView textview8;
	
	private SharedPreferences theme;
	private Vibrator vibr;
	private TimerTask time;
	private TimerTask timeLaps;
	private SoundPool cue;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		timetext = (TextView) findViewById(R.id.timetext);
		textview10 = (TextView) findViewById(R.id.textview10);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		vscroll2 = (ScrollView) findViewById(R.id.vscroll2);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		startstop = (Button) findViewById(R.id.startstop);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		resettimer = (Button) findViewById(R.id.resettimer);
		textview4 = (TextView) findViewById(R.id.textview4);
		seekbar1 = (SeekBar) findViewById(R.id.seekbar1);
		secondsalerttext = (TextView) findViewById(R.id.secondsalerttext);
		audioswitch = (Switch) findViewById(R.id.audioswitch);
		vibrationon = (Switch) findViewById(R.id.vibrationon);
		textview9 = (TextView) findViewById(R.id.textview9);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		textview7 = (TextView) findViewById(R.id.textview7);
		themeswitch = (Switch) findViewById(R.id.themeswitch);
		textview8 = (TextView) findViewById(R.id.textview8);
		theme = getSharedPreferences("current", Activity.MODE_PRIVATE);
		vibr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		startstop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (timerOn == 0) {
					timerOn = 1;
					seekbar1.setVisibility(View.GONE);
				}
				if (timerOn == 1) {
					time = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									timerDoneSecs = timerDoneSecs + 1;
									timetext.setText(String.valueOf((long)(timerDoneSecs)).concat(" s | ".concat(String.valueOf((long)(timerDoneLaps)).concat(" clips"))));
								}
							});
						}
					};
					_timer.scheduleAtFixedRate(time, (int)(0), (int)(1000));
					timeLaps = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									timerDoneLaps = timerDoneLaps + 1;
									if (soundOn == 1) {
										
									}
									if (vibrationOn == 1) {
										vibr.vibrate((long)(350));
									}
								}
							});
						}
					};
					_timer.scheduleAtFixedRate(timeLaps, (int)(0), (int)(waitsecs * 1000));
				}
			}
		});
		
		resettimer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (timerOn == 0) {
					timerDoneSecs = -1;
					timerDoneLaps = -1;
					timetext.setText(String.valueOf((long)(timerDoneSecs)).concat(" s | ".concat(String.valueOf((long)(timerDoneLaps)).concat(" clips"))));
				}
				if (timerOn == 1) {
					time.cancel();
					timeLaps.cancel();
					timerOn = 0;
					seekbar1.setVisibility(View.VISIBLE);
				}
			}
		});
		
		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged (SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				if (_progressValue == 0) {
					waitsecs = 5;
				}
				if (_progressValue == 1) {
					waitsecs = 10;
				}
				if (_progressValue == 2) {
					waitsecs = 15;
				}
				if (_progressValue == 3) {
					waitsecs = 20;
				}
				if (_progressValue == 4) {
					waitsecs = 25;
				}
				secondsalerttext.setText(String.valueOf((long)(waitsecs)).concat(" seconds"));
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		audioswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					soundOn = 1;
				}
				else {
					soundOn = 0;
				}
			}
		});
		
		vibrationon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					vibrationOn = 1;
				}
				else {
					vibrationOn = 0;
				}
			}
		});
		
		themeswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					theme.edit().putString("current", "D").commit();
					linear1.setBackgroundColor(0xFF424242);
					startstop.setTextColor(0xFF424242);
					resettimer.setTextColor(0xFF424242);
					startstop.setBackgroundColor(0xFFFAFAFA);
					resettimer.setBackgroundColor(0xFFFAFAFA);
					audioswitch.setTextColor(0xFFFAFAFA);
					vibrationon.setTextColor(0xFFFAFAFA);
					textview2.setTextColor(0xFFFAFAFA);
					textview3.setTextColor(0xFFFAFAFA);
					timetext.setTextColor(0xFFFAFAFA);
					textview4.setTextColor(0xFFFAFAFA);
					secondsalerttext.setTextColor(0xFFFAFAFA);
					textview9.setTextColor(0xFFFAFAFA);
					textview7.setTextColor(0xFFFAFAFA);
					textview8.setTextColor(0xFFFAFAFA);
				}
				else {
					theme.edit().putString("current", "L").commit();
					linear1.setBackgroundColor(0xFFFAFAFA);
					startstop.setTextColor(0xFFFAFAFA);
					resettimer.setTextColor(0xFFFAFAFA);
					startstop.setBackgroundColor(0xFF424242);
					resettimer.setBackgroundColor(0xFF424242);
					audioswitch.setTextColor(0xFF424242);
					vibrationon.setTextColor(0xFF424242);
					textview2.setTextColor(0xFF424242);
					textview3.setTextColor(0xFF424242);
					timetext.setTextColor(0xFF424242);
					textview4.setTextColor(0xFF424242);
					secondsalerttext.setTextColor(0xFF424242);
					textview9.setTextColor(0xFF424242);
					textview7.setTextColor(0xFF424242);
					textview8.setTextColor(0xFF424242);
				}
			}
		});
	}
	private void initializeLogic() {
		timerOn = 0;
		timerDoneSecs = -1;
		timerDoneLaps = -1;
		waitsecs = 25;
		secondsalerttext.setText(String.valueOf((long)(waitsecs)).concat(" seconds"));
		soundOn = 0;
		vibrationOn = 0;
		if (theme.getString("current", "").equals("")) {
			theme.edit().putString("current", "L").commit();
		}
		if (theme.getString("current", "").equals("L")) {
			linear1.setBackgroundColor(0xFFFAFAFA);
			startstop.setTextColor(0xFFFAFAFA);
			resettimer.setTextColor(0xFFFAFAFA);
			startstop.setBackgroundColor(0xFF424242);
			resettimer.setBackgroundColor(0xFF424242);
			audioswitch.setTextColor(0xFF424242);
			vibrationon.setTextColor(0xFF424242);
			textview2.setTextColor(0xFF424242);
			textview3.setTextColor(0xFF424242);
			timetext.setTextColor(0xFF424242);
			textview4.setTextColor(0xFF424242);
			secondsalerttext.setTextColor(0xFF424242);
			textview9.setTextColor(0xFF424242);
			textview7.setTextColor(0xFF424242);
			textview8.setTextColor(0xFF424242);
		}
		if (theme.getString("current", "").equals("D")) {
			themeswitch.setChecked(true);
			linear1.setBackgroundColor(0xFF424242);
			startstop.setTextColor(0xFF424242);
			resettimer.setTextColor(0xFF424242);
			startstop.setBackgroundColor(0xFFFAFAFA);
			resettimer.setBackgroundColor(0xFFFAFAFA);
			audioswitch.setTextColor(0xFFFAFAFA);
			vibrationon.setTextColor(0xFFFAFAFA);
			textview2.setTextColor(0xFFFAFAFA);
			textview3.setTextColor(0xFFFAFAFA);
			timetext.setTextColor(0xFFFAFAFA);
			textview4.setTextColor(0xFFFAFAFA);
			secondsalerttext.setTextColor(0xFFFAFAFA);
			textview9.setTextColor(0xFFFAFAFA);
			textview7.setTextColor(0xFFFAFAFA);
			textview8.setTextColor(0xFFFAFAFA);
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
