package com.example.isangeet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class folderadapter extends RecyclerView.Adapter<folderadapter.MyViewHolder> {

   ArrayList<String>folderName;

    public folderadapter(ArrayList<String> folderName, ArrayList<videomodel> videomodels, Context context) {
        this.folderName = folderName;
        this.videomodels = videomodels;
        this.context = context;
    }

   public ArrayList<videomodel>videomodels;
   private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.folder_view,parent,false);
        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int index=folderName.get(position).lastIndexOf("/");
        String folderNames=folderName.get(position).substring(index+1);

        holder.name.setText(folderNames);
       holder.countvideo.setText(String.valueOf(countvideos(folderName.get(position))));
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent in=new Intent(context,videofolder.class);
               in.putExtra("folderName",folderName.get(position));
               context.startActivity(in);
           }
       });


    }

    @Override
    public int getItemCount() {
        return folderName.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,countvideo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.foldername);
            countvideo=itemView.findViewById(R.id.foldercount);
        }
    }

    public int countvideos(String folders){
    int count =0;
       for(videomodel model:videomodels){
           if (model.getPath().substring(0,model.getPath().lastIndexOf("/")).endsWith(folders)){
               count++;
           }
       }

        return count;
    }

}
