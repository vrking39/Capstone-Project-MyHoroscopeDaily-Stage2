package com.example.myhoroscopedaily.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhoroscopedaily.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.SunsignViewHolder> {

    private List<String> sunsignList;
    private SunsignClickListener mListener;

    public class SunsignViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_sunsign)
        TextView textv_sunsign;
        @BindView(R.id.iv_sunsign_image)
        AppCompatImageView imagev_sunsign_image;

        SunsignViewHolder(@NonNull View itemView) {
            super(itemView);
            textv_sunsign = itemView.findViewById(R.id.tv_sunsign);
            imagev_sunsign_image = itemView.findViewById(R.id.iv_sunsign_image);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String sunsign = sunsignList.get(position);
            mListener.onSunsignClick(sunsign);
        }
    }

    public MainAdapter(SunsignClickListener listener) {this.mListener = listener;}

    @NonNull
    @Override
    public SunsignViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater
                .from(viewGroup.getContext()).inflate(R.layout.sunsign_list, viewGroup, false);

        return new SunsignViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SunsignViewHolder holder, int position) {
        final String sunsign = sunsignList.get(position);

        holder.textv_sunsign.setText(sunsign);

        String image_url = "";
        switch (sunsign) {
            case "taurus":
                image_url = "Taurus";
                break;
            default: image_url = sunsign;
        }

        Picasso.get()
                .load("https://i2.wp.com/www.pictureboxblue.com/pbb-cont/pbb-up/2019/11/"+image_url+"-s.jpg?w=800&ssl=1")
                .fit()
                .into(holder.imagev_sunsign_image);

    }

    @Override
    public int getItemCount() {
        return sunsignList == null? 0 : sunsignList.size();
    }

    public void setData(List<String> sunsignList) {
        this.sunsignList = sunsignList;
        notifyDataSetChanged();
    }

    public interface SunsignClickListener {
        void onSunsignClick(String sunsign);
    }


}
