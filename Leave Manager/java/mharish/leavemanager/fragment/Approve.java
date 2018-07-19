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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import mharish.leavemanager.R;

public class Approve extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    String fx,selection="";
    Spinner req;
    List<String> zs = new ArrayList<String>();
    RadioGroup rg;
    Button conf;
    TextView tv,dv;
    public Approve(String x){
        fx=x;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View vx= inflater.inflate(R.layout.fragment_approve, container, false);
        req=(Spinner)vx.findViewById(R.id.approve);
        rg=(RadioGroup)vx.findViewById(R.id.apstat);
        rg.check(R.id.appro);
        conf=(Button)vx.findViewById(R.id.go);
        dv=(TextView)vx.findViewById(R.id.disp);
        tv=(TextView)vx.findViewById(R.id.myst);
        tv.setVisibility(View.GONE);
        conf.setOnClickListener(this);
        Background2 b=new Background2();
        String xx="";
        try {
            xx=b.execute(fx).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        int c=0;

        if(xx.contains("Error")){
            req.setVisibility(View.GONE);
            conf.setVisibility(View.GONE);
            rg.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
            dv.setVisibility(View.GONE);
            //Toast.makeText(getActivity(),"No request currently",Toast.LENGTH_LONG).show();

        }
        else {

            StringTokenizer st=new StringTokenizer(xx);
            String eg=st.nextToken(" ");
            String ez="";
            while(st.hasMoreTokens()){
                ez=st.nextToken("~");
                if(!ez.contains("<")) {
                    zs.add(ez);
                    c++;
                   }
            }

            if(c==0){
                req.setVisibility(View.GONE);
                conf.setVisibility(View.GONE);
                rg.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                dv.setVisibility(View.GONE);
                // Toast.makeText(getActivity(),"No request currently",Toast.LENGTH_LONG).show();

            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, zs);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            req.setAdapter(dataAdapter);
            req.setOnItemSelectedListener(this);


        }
        return vx;
    }

    @Override
    public void onClick(View v) {

        StringTokenizer st1=new StringTokenizer(selection);
        String appz=st1.nextToken(" "),cek="";
        String from=st1.nextToken(" "),to=st1.nextToken(" "),reason=st1.nextToken(" ");

        Background3 bg3=new Background3();
            if(rg.getCheckedRadioButtonId()==R.id.appro){
                try {
                    cek=bg3.execute(appz,"Approve",from,to,reason,fx).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        else
            {
                try {
                    cek=bg3.execute(appz,"Reject",from,to,reason,fx).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        if(cek.contains("Success")) Toast.makeText(getActivity(),"Data processed !",Toast.LENGTH_LONG).show();
        else Toast.makeText(getActivity(),"Error!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
       selection=parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    class Background3 extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {

        }


        @Override
        public String doInBackground(String... params) {

            String bemail=params[0],data="",bstat=params[1];
            String bfrom=params[2],bto=params[3],breason=params[4],bmng=params[5];
            int tp;
            try {
                URL url= new URL("http://mitcommuterpass.net16.net/leaveapprej.php?");
                String urlparams="email="+bemail+"&stat="+bstat+"&from="+bfrom+"&to="+bto+"&reason="+breason+"&mng="+bmng;
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


    class Background2 extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {

        }


        @Override
        public String doInBackground(String... params) {

            String bemail=params[0],data="";
            int tp;
            try {
                URL url= new URL("http://mitcommuterpass.net16.net/leaveapprove.php?");
                String urlparams="email="+bemail;
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
