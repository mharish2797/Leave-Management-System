package mharish.leavemanager;

/**
 * Created on: 26-09-2016.
 * Author: Harish Mohan
 */
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class SignUp extends Activity implements View.OnClickListener {

Context ctx=this;
    Button upload;
    EditText uname,uemail,upswd,ucpswd,uphone,uaddress,udesignation,uenum,udep,ubranch;
    RadioGroup uestat,umstat,ugender;
    DatePicker udob,udoj;
    int dayb,dayj,monb,monj,yearb,yearj;
    String sname,semail,spswd,scpswd,sphone,saddress,sdesignation,senum,sdep,sbranch,sestat,smstat,sgender,sdob,sdoj;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        uname=(EditText)findViewById(R.id.name);
        uemail=(EditText)findViewById(R.id.semail);
        upswd=(EditText)findViewById(R.id.spassword);
        ucpswd=(EditText)findViewById(R.id.cnfpassword);
        uphone=(EditText)findViewById(R.id.phone);
        uaddress=(EditText)findViewById(R.id.address);
        udesignation=(EditText)findViewById(R.id.empdsg);
        uenum=(EditText)findViewById(R.id.enumer);
        udep=(EditText)findViewById(R.id.department);
        ubranch=(EditText)findViewById(R.id.branch);

        uestat=(RadioGroup)findViewById(R.id.stat);
        uestat.check(R.id.permanent);
        umstat=(RadioGroup)findViewById(R.id.mary);
        umstat.check(R.id.single);
        ugender=(RadioGroup)findViewById(R.id.gender);
        ugender.check(R.id.male);

        udob=(DatePicker)findViewById(R.id.dob);
        udoj=(DatePicker)findViewById(R.id.doj);
        //udob.setMinDate(dayb);
        upload=(Button)findViewById(R.id.upload);
        upload.setOnClickListener(this);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {

        if (!isNetworkAvailable()) {
            LinearLayout layout=(LinearLayout)findViewById(R.id.linlay);

            Snackbar snackbar = Snackbar.make(layout, "No internet connection!", Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect to Internet!", Toast.LENGTH_SHORT).show();
                }
            });

// Changing message text color

            snackbar.setActionTextColor(Color.RED);

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
            //  Toast.makeText(this, "Check your internet connectivity", Toast.LENGTH_LONG).show();

        }
        else {


            sname = uname.getText().toString();
            semail = uemail.getText().toString();
            spswd = upswd.getText().toString();
            scpswd = ucpswd.getText().toString();
            sphone = uphone.getText().toString();
            saddress = uaddress.getText().toString();

            sdesignation = udesignation.getText().toString();
            senum = uenum.getText().toString();
            sdep = udep.getText().toString();
            sbranch = ubranch.getText().toString();

            int sfx = uestat.getCheckedRadioButtonId();
            if (sfx == R.id.permanent) sestat = "Permanent";
            else sestat = "Temporary";

            int mfx = umstat.getCheckedRadioButtonId();
            if (mfx == R.id.single) smstat = "Single";
            else smstat = "Married";

            int gfx = ugender.getCheckedRadioButtonId();
            if (gfx == R.id.male) sgender = "Male";
            else sgender = "Female";


            dayb = udob.getDayOfMonth();
            monb = udob.getMonth() + 1;
            yearb = udob.getYear();
            sdob = dayb + "." + monb + "." + yearb;
            dayj = udoj.getDayOfMonth();
            monj = udoj.getMonth() + 1;
            yearj = udoj.getYear();
            sdoj = dayj + "." + monj + "." + yearj;


            if (sname.matches("") || semail.matches("") || spswd.matches("") || scpswd.matches("") || sphone.matches("") || saddress.matches("") || sdesignation.matches("")
                    || senum.matches("") || sdep.matches("") || sbranch.matches("")
                    )
                Toast.makeText(this, "Empty Fields must be field!", Toast.LENGTH_SHORT).show();
            else if (!spswd.equals(scpswd))
                Toast.makeText(this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
            else if (spswd.length() < 6)
                Toast.makeText(this, "Weak Password!", Toast.LENGTH_SHORT).show();
            else if (sphone.length() != 10)
                Toast.makeText(this, "Invalid Mobile Number!", Toast.LENGTH_SHORT).show();
            else if (!semail.contains("@"))
                Toast.makeText(this, "E-mail ID invalid!", Toast.LENGTH_SHORT).show();
            else {
                Background b = new Background();
                String xil = "";
                try {
                    xil = b.execute(sname, semail, spswd, sphone, saddress, sdesignation, senum, sdep, sbranch, sestat, smstat, sgender, sdob, sdoj).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (!xil.contains("Error")) {
                    Toast.makeText(this, "Data successfully uploaded !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Error occured.Please try again!", Toast.LENGTH_SHORT).show();
                /*uname.setText("");
                uemail.setText("");
                upswd.setText("");
                uphone.setText("");
                ucpswd.setText("");
                uaddress.setText("");*/

                }
            }
        }

    }


    class Background extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {

        }


        @Override
        public String doInBackground(String... params) {

            String bname=params[0],data="";
            String bemail=params[1],bpswd=params[2],bphone=params[3],baddress=params[4];
            //sdesignation,senum,sdep,sbranch,sestat,smstat,sgender,sdob,sdoj
            String bdesignation=params[5],benum=params[6],bdep=params[7],bbranch=params[8],bestat=params[9],bmstat=params[10],bgender=params[11],bdob=params[12],bdoj=params[13];
            int tp;
            try {
               SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yy");
                SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm");
                String dat=sdf1.format(new Date());
                String tim=sdf5.format(new Date());
                // Toast.makeText(this,s+" "+s1,Toast.LENGTH_LONG).show();
                dat+=" "; dat+=tim;
                URL url= new URL("http://mitcommuterpass.net16.net/leavesign.php?");                          //sdesignation,senum,sdep,sbranch,sestat,smstat,sgender,sdob,sdoj
                String urlparams="name="+bname+"&email="+bemail+"&password="+bpswd+"&phone="+bphone+"&address="+baddress+"&time="+dat+"&desgn="+bdesignation+"&enum="+benum+"&dep="+bdep+"&branch="+bbranch+"&estat="+bestat+"&mstat="+bmstat+"&gender="+bgender+"&dob="+bdob+"&doj="+bdoj;
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
