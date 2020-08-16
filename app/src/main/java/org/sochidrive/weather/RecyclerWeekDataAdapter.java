package org.sochidrive.weather;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;


public class RecyclerWeekDataAdapter extends RecyclerView.Adapter<RecyclerWeekDataAdapter.ViewHolder> {
    private ArrayList<String> data;

    public RecyclerWeekDataAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerWeekDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week_rv_layout, parent, false);
        return new RecyclerWeekDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerWeekDataAdapter.ViewHolder holder, int position) {
        String text = data.get(position);
        holder.setTextToTextView(text);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewWeek;
        private TextView textViewDegree;
        private ImageView imageViewWeather;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWeek = itemView.findViewById(R.id.textViewWeek);
            textViewDegree = itemView.findViewById(R.id.textViewDegree);
            imageViewWeather = itemView.findViewById(R.id.imageViewWeather);
        }

        void setTextToTextView(String text) {
            Random random = new Random();
            textViewWeek.setText(text);
            textViewDegree.setText("+"+random.nextInt(50));
            if(random.nextInt(5)==1) {
                imageViewWeather.setImageResource(R.drawable.weather1);
            } else if(random.nextInt(5)==2) {
                imageViewWeather.setImageResource(R.drawable.weather2);
            } else if(random.nextInt(5)==3) {
                imageViewWeather.setImageResource(R.drawable.weather3);
            } else if(random.nextInt(5)==4) {
                imageViewWeather.setImageResource(R.drawable.weather4);
            } else {
                imageViewWeather.setImageResource(R.drawable.weather5);
            }

        }
    }
}
