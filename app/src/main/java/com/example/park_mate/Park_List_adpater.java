package com.example.park_mate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Park_List_adpater extends RecyclerView.Adapter<Park_List_adpater.MyviewHolders> {

    Context context;
    ArrayList<Park> parks;

    public Park_List_adpater(Context c, ArrayList<Park> u) {
        context = c;
        parks = u;
    }

    @NonNull
    @Override
    public Park_List_adpater.MyviewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Park_List_adpater.MyviewHolders(LayoutInflater.from(context).inflate(R.layout.parklist_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull Park_List_adpater.MyviewHolders holder, final int position) {

        holder.parkname.setText("Park Name: "+parks.get(position).getParkname());
        holder.parktype.setText("Park Type: " + parks.get(position).getType());

        System.out.println(parks.get(position).getType());

           Picasso.get().load(parks.get(position).getImageurl()).into(holder.parkpic);

              holder.btn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent= new Intent(context, ParkDetail.class);

                      // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      intent.putExtra("parkname",parks.get(position).getParkname());
                      intent.putExtra("parktype",parks.get(position).getType());
                      intent.putExtra("parkcity",parks.get(position).getCity());
                      intent.putExtra("parkaddress",parks.get(position).getAddress());
                      intent.putExtra("parkimageurl",parks.get(position).getImageurl());
                      intent.putExtra("parkstate",parks.get(position).getState());
                      intent.putExtra("parkcountry",parks.get(position).getCountry());




                      context.startActivity(intent);
                  }
              });


    }

    @Override
    public int getItemCount() {
        return parks.size();
    }

    public class MyviewHolders extends RecyclerView.ViewHolder {

        TextView parkname,parktype;
        ImageView parkpic;
        Button btn;

        public MyviewHolders(@NonNull View itemView) {
            super(itemView);
            parkname = (TextView) itemView.findViewById(R.id.parkname);
            parktype= (TextView) itemView.findViewById(R.id.Parktype);

            parkpic = (ImageView) itemView.findViewById(R.id.pic);
            btn = (Button) itemView.findViewById(R.id.viewpark);


        }


    }
}
