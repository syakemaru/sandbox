package syakemaru.getsyake.syake;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class SyakeActivity extends Activity implements SensorEventListener {
	public int width;
	public int height;
	
	private Bitmap syake;
	private SensorManager mSensorManager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        // ディスプレイのインスタンス生成
        Display disp = wm.getDefaultDisplay();
        width = disp.getWidth();
        height = disp.getHeight();

        syake = BitmapFactory.decodeResource(getResources(), R.drawable.syake);
        SyakeView sview = new SyakeView(this);
        setContentView(sview);
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }
	
    @Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		 
        if (sensors.size() > 0) {
            Sensor s = sensors.get(0);
            mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
        }
	}
    
    @Override
    protected void onStop() {
    	super.onStop();
    	mSensorManager.unregisterListener(this);
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		if (event.values[0] > 19){
		Toast.makeText(SyakeActivity.this, "success", Toast.LENGTH_SHORT).show();
		}
	}
    
    public class SyakeView extends View{
    	Paint paint = new Paint();
    	int imght = syake.getHeight();
    	int imgwt = syake.getWidth();
    	int position = -imght;
    	int move = 10;
    	
    	public SyakeView(Context context){
    		super(context);	
    	}

		@Override
		protected void onDraw(Canvas canvas) {
			position += move;
			if (position>height){
				position=-imght;
			}
			
			super.onDraw(canvas);
			canvas.drawColor(Color.CYAN);
			paint.setColor(Color.RED);
			canvas.drawLine(0, height/2, width, height/2, paint);
			canvas.drawBitmap(syake,width/2-imgwt/2,position,paint);
			
			invalidate();
		}
    	
    }


}

