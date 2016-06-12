package itcr.deportizate.Activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import itcr.deportizate.Activities.Menu;
import itcr.deportizate.AnalyticsTracker;
import itcr.deportizate.DBHandler;
import itcr.deportizate.R;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    AnalyticsTracker analyticsTracker;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    public static String PACKAGE_NAME;
    //Boton de login
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
          //  nextActivity(profile);
        }
        @Override
        public void onCancel() {        }
        @Override
        public void onError(FacebookException e) {      }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler db = new DBHandler(this);//Base de Datos

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());

        ImageButton Menu_Jovenes = (ImageButton) findViewById(R.id.BotonMenuJovenes);
        ImageButton Menu_Adultos = (ImageButton) findViewById(R.id.BotonMenuAdultos);
        ImageButton Menu_Adultos_Mayores = (ImageButton) findViewById(R.id.BotonMenuAdultosMayores);
        ImageButton About = (ImageButton) findViewById(R.id.imageButtonAbout);

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), itcr.deportizate.Activities.About.class));
            }
        });

        Menu_Jovenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("persona", "Joven");
                startActivity(intent);
            }
        });

        Menu_Adultos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("persona", "Adulto");
                startActivity(intent);
            }
        });

        Menu_Adultos_Mayores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("persona", "AMayor");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        analyticsTracker.trackScreen("MainActivity");

    }

    public void printKeyHash() {
        //Funcion para imprimir el key hash
        try {
            PACKAGE_NAME = getApplicationContext().getPackageName();
            PackageInfo info = getPackageManager().getPackageInfo(PACKAGE_NAME, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Key Hash -->", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
