package itcr.deportizate;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
	private static final String TAG = "MyService";

	@Override
	public IBinder onBind(Intent i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(final Intent intent, int flags, int startId) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Log.d(TAG, "FirstService started");
				// El servicio se finaliza a si mismo cuando finaliza su
				// trabajo.
				try {
					// Simulamos trabajo de 10 segundos.
					Thread.sleep(14400000);

					// Instanciamos e inicializamos nuestro manager.
					NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

					NotificationCompat.Builder builder = new NotificationCompat.Builder(
							getBaseContext())
							//.setSmallIcon(android.R.drawable.ic_dialog_info)
							//.setContentTitle("MyService")
							//.setContentText("Termino el servicio!")
                            .setContentTitle("Buenos Días! Deportízate!!!")
                            .setContentText("No olvide realizar alguna actividad física diarimente para mantener su salud")
                           // .setTicker("Es hora de ejercictarse!")
                            .setSmallIcon(R.mipmap.ic_launcher)
							.setWhen(System.currentTimeMillis());


					nManager.notify(12345, builder.build());

					Log.d(TAG, "sleep finished");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

		this.stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "FirstService destroyed");
	}

}
