package com.example.isangeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String>folder=new ArrayList<>();
    private ArrayList<videomodel>videolist=new ArrayList<>();
    folderadapter folderadapter;
    RecyclerView recyclerView;

    String []permission={"android.permission.READ_MEDIA_VIDEO"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycleview_filder);
        videolist=fetchAllVideo(this);
        if(folder!=null&& folder.size()>0&&videolist!=null){
            folderadapter=new folderadapter(folder,videolist,this);
            recyclerView.setAdapter(folderadapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        }else{
            Toast.makeText(this, "Can't find any video Folder", Toast.LENGTH_SHORT).show();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission,23);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 23) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "granterd", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private ArrayList<videomodel>fetchAllVideo(Context contex){
        ArrayList<videomodel>videomodels=new ArrayList<>();
        Uri uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String orderby=MediaStore.Video.Media.DATE_ADDED +" DESC";
        String projection[]={
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.RESOLUTION

        };
        Cursor cursor= contex.getContentResolver().query(uri,projection,null,null,orderby);
         if(cursor!=null){
             while (cursor.moveToNext()){
                 String id=cursor.getString(0);
                 String path=cursor.getString(1);
                 String title=cursor.getString(2);
                 String size=cursor.getString(3);
                 String resolution=cursor.getString(4);
                 String duration=cursor.getString(5);
                 String disname=cursor.getString(6);
                 String width_height=cursor.getString(7);


                 videomodel videofiles=new videomodel(id,path,title,size,resolution,duration,disname,width_height);

                int slashfirstIndex=path.lastIndexOf("/");
                String subsring =path.substring(0,slashfirstIndex);

                 if(!folder.contains(subsring)){
                     folder.add(subsring);
                 }
                 videomodels.add(videofiles);
             }
             cursor.close();
         }

        return videomodels;
    }


}
