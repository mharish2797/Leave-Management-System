package mharish.leavemanager.fragment;
/**
 * Created on: 26-09-2016.
 * Author: Harish Mohan
 */

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import mharish.leavemanager.R;

public class LeaveHistory extends Fragment {
    TextView tvd;
    String fx;
    public LeaveHistory(String x){
        fx=x;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_leave_history, container, false);
        tvd=(TextView)v.findViewById(R.id.carder);
        BackgroundL bl=new BackgroundL();
       // Toast.makeText(getActivity(),fx,Toast.LENGTH_SHORT).show();
        String js="";
        try {
            js=bl.execute(fx).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(!js.contains("Error")){
            String fin="\n\nLeave History:\n\n";
            int i=1;
            StringTokenizer stz=new StringTokenizer(js);
            while(stz.hasMoreTokens()) {
                String xxz = stz.nextToken("~");
                if (!xxz.contains("<")) {
                    StringTokenizer zs = new StringTokenizer(xxz);
                    fin+=i+") ";
                    while (zs.hasMoreTokens()) {
                        fin += zs.nextToken(" ") + "\t\t";
                    }
                    fin += "\n";
                    i++;
                }
            }
            tvd.setText(fin);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                tvd.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
        }

        return v;
    }



    class BackgroundL extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {

        }


        @Override
        public String doInBackground(String... params) {

            String bemail=params[0],data="";
            int tp;
            try {
                URL url= new URL("http://mitcommuterpass.net16.net/leavehistory.php?");
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
