package mharish.leavemanager.fragment;
/**
 * Created on: 26-09-2016.
 * Author: Harish Mohan
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import mharish.leavemanager.R;



public class ApplyLeave extends Fragment implements View.OnClickListener {

    EditText efrom,eto,ereason,emngid;
    Button eapply;
    String sfrom,sto,sreason,smngid,xemail;
   // Context ctt=this;

    public ApplyLeave(String x){
        xemail=x;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v=inflater.inflate(R.layout.fragment_apply_leave, container, false);
        efrom=(EditText)v.findViewById(R.id.from);
        eto=(EditText)v.findViewById(R.id.to);
        ereason=(EditText)v.findViewById(R.id.reason);
        emngid=(EditText)v.findViewById(R.id.managerid);
        eapply=(Button)v.findViewById(R.id.appli);
        eapply.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {

        sfrom=efrom.getText().toString();
        sto=eto.getText().toString();
        sreason=ereason.getText().toString();
        smngid=emngid.getText().toString();

        if(!sfrom.matches("")||!sto.matches("")||!sreason.matches("")||smngid.matches("")){

            Background1 b1=new Background1();
            String xx="";
            try {
                xx=b1.execute(sfrom,sto,sreason,smngid).get();
                Toast.makeText(getActivity(), "Application forwarded successfully", Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        else  Toast.makeText(getActivity(), "Empty Fields must be field!", Toast.LENGTH_SHORT).show();

    }



    class Background1 extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {

        }


        @Override
        public String doInBackground(String... params) {

            String bfrom=params[0],data="";
            String bto=params[1],breason=params[2],bmngid=params[3];
            int tp;
            try {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yy");
                SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm");
                String dat=sdf1.format(new Date());
                String tim=sdf5.format(new Date());
                // Toast.makeText(this,s+" "+s1,Toast.LENGTH_LONG).show();
                dat+=" "; dat+=tim;
                URL url= new URL("http://mitcommuterpass.net16.net/leaveapply.php?");
                String urlparams="from="+bfrom+"&to="+bto+"&reason="+breason+"&mngid="+bmngid+"&email="+xemail;
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                os.write(urlparams.getBytes());
                os.flush();
                os.close();
                InputStream is=httpURLConnection.getInputStream();
                while((tp=is.read())!=-1){
                    data+=(char)tp;
                }

                is.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return data;
        }
    }

}
