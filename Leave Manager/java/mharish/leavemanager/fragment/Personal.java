package mharish.leavemanager.fragment;
/**
 * Created on: 26-09-2016.
 * Author: Harish Mohan
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.StringTokenizer;

import mharish.leavemanager.R;

public class Personal extends Fragment {

    public Personal(){}
    public Personal(String x){
        this.x=x;

    }
    String x;
    TextView profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_personal, container, false);
        profile=(TextView)v.findViewById(R.id.profiler);
        StringTokenizer st=new StringTokenizer(x);
        String[] arr=new String[15];
        int jx=0;
        while(st.hasMoreTokens()){
            arr[jx++]=st.nextToken("~");
        }

        String nb="";
        nb+="Name : "+arr[0]+"\n\n";
        nb+="Date of Birth : "+arr[12]+"\n\n";
        nb+="Gender : "+arr[11]+"\n\n";
        nb+="Marital Status : "+arr[10]+"\n\n";
        nb+="E-mail : "+arr[1]+"\n\n";
        nb+="Contact Number : "+arr[2]+"\n\n";
        nb+="Address : "+arr[3]+"\n\n";
        nb+="Employee Number : "+arr[6]+"\n\n";
        nb+="Designation : "+arr[5]+"\n\n";
        nb+="Employee Status : "+arr[9]+"\n\n";
        nb+="Department : "+arr[7]+"\n\n";
        nb+="Branch : "+arr[8]+"\n\n";
        nb+="Date of Joining : "+arr[13]+"\n\n";


        profile.setText(nb);

        return v;

    }





}
