package mharish.leavemanager;
/**
 * Created on: 26-09-2016.
 * Author: Harish Mohan
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.StringTokenizer;

import mharish.leavemanager.fragment.About;
import mharish.leavemanager.fragment.ApplyLeave;
import mharish.leavemanager.fragment.Approve;
import mharish.leavemanager.fragment.LeaveHistory;
import mharish.leavemanager.fragment.Personal;

import static mharish.leavemanager.R.styleable.NavigationView;


public class Navigator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    String prox="";
    TextView tname,temail;
    String[] arr=new String[15];
    int jx=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        Bundle bundle=getIntent().getExtras();
        prox=bundle.getString("profile");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        StringTokenizer st=new StringTokenizer(prox);
        while(st.hasMoreTokens()){
            arr[jx++]=st.nextToken("~");
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


         View header=navigationView.inflateHeaderView(R.layout.nav_header_navigator);
        //ImageView img=(ImageView)header.findViewById(R.id.imageView);
        //img.setVisibility(View.VISIBLE);
        TextView tvname=(TextView)header.findViewById(R.id.dispname);
        tvname.setText(arr[0]);
        tvname.setVisibility(View.VISIBLE);
        TextView tvemail=(TextView)header.findViewById(R.id.temail);
        tvemail.setText(arr[1]);
        tvemail.setVisibility(View.VISIBLE);
        /*tname=(TextView)header.findViewById(R.id.dispname);
        temail=(TextView)header.findViewById(R.id.temail);
        tname
        temail.setText(arr[1]);
*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        FragmentManager manager1 = getSupportFragmentManager();
        FragmentTransaction transaction1 = manager1.beginTransaction();
        Personal x1=new Personal(prox);
        transaction1.replace(R.id.dumbi, x1);
        transaction1.commit();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Personal x=new Personal(prox);
        ApplyLeave apl=new ApplyLeave(arr[1]);
        Approve apr=new Approve(arr[1]);
        LeaveHistory lh=new LeaveHistory(arr[1]);
        About ab=new About();
        if (id == R.id.nav_camera) {
            transaction.replace(R.id.dumbi,x);


        } else if (id == R.id.nav_gallery) {
            transaction.replace(R.id.dumbi, lh);

        } else if (id == R.id.nav_slideshow) {
            transaction.replace(R.id.dumbi,apl);

        } else if (id == R.id.nav_manage) {
            transaction.replace(R.id.dumbi,apr);


        } else if (id == R.id.nav_share) {
            transaction.replace(R.id.dumbi,ab);

        }
        else if(id==R.id.nav_send){
            finish();
            Intent intent=new Intent();
            intent.setClass(this,LoginActivity.class);
            startActivity(intent);
           // System.exit(0);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        transaction.commit();

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Navigator Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://mharish.leavemanager/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Navigator Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://mharish.leavemanager/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
