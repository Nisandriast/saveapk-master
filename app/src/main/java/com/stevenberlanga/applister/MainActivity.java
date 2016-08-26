package com.stevenberlanga.applister;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    String app_loc;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.tv);
        btn = (Button) findViewById(R.id.btn);

        PackageManager pm = getPackageManager();

        Log.d("PackageList", "Hello world!");
        for (ApplicationInfo app : pm.getInstalledApplications(0)) {
            Log.d("PackageList", "package: " + app.packageName + ", sourceDir: " + app.sourceDir);
            String app_name= app.packageName.toString();
            Log.d("appname", "onCreate: "+app_name);

            if(app.packageName.equals("com.whatsapp"))
            {
                app_loc= app.sourceDir.toString();
                Toast.makeText(MainActivity.this, "loc "+app_loc, Toast.LENGTH_SHORT).show();
                Log.d("Apploc", "onCreate: " +app_loc );
                tv.setText("App" +app_name);
                saveapk();
            }
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                File srcFile = new File(Environment.getExternalStorageDirectory().toString()+"/APKFolder/newfile.apk");
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.setType("application/vnd.android.package-archive");
                i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(srcFile));
                startActivity(Intent.createChooser(i, "Sharing"));



            }
        });


    }

    public void saveapk(){

        File f1 =new File(app_loc);

      //  Log.v("file--", " "+f1.getName().toString()+"----"+info.loadLabel(getPackageManager()));
        try{

            String file_name = "newfile";
            Log.d("file_name--", " "+file_name);

            File f2 = new File(Environment.getExternalStorageDirectory().toString()+"/APKFolder");
            f2.mkdirs();
            f2 = new File(f2.getPath()+"/"+file_name+".apk");
            f2.createNewFile();

            InputStream in = new FileInputStream(f1);

            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage() + " in the specified directory.");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    }



