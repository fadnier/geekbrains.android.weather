package org.sochidrive.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.sochidrive.weather.model.WeatherFiveDayRequest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class RecyclerWeekDataAdapter extends RecyclerView.Adapter<RecyclerWeekDataAdapter.ViewHolder> {
    private WeatherFiveDayRequest data;

    public RecyclerWeekDataAdapter(WeatherFiveDayRequest weatherFiveDayRequest) {
        this.data = weatherFiveDayRequest;
    }

    @NonNull
    @Override
    public RecyclerWeekDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week_rv_layout, parent, false);
        return new RecyclerWeekDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerWeekDataAdapter.ViewHolder holder, int position) {
        Timestamp timestamp = new Timestamp(data.getList()[position].getDt()*1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault());
        String date = dateFormat.format(new Date(timestamp.getTime()));
        String degree = String.format("%+d", (int)(data.getList()[position].getMain().getTemp()-273.15f));
        String weather = data.getList()[position].getWeather()[0].getDescription();
        holder.setTextToTextView(date,degree,weather);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.getList().length;
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

        void setTextToTextView(String date,String degree, String weather) {
            textViewWeek.setText(date);
            textViewDegree.setText(degree);

            if(weather.equals("clear sky")) {
                imageViewWeather.setImageResource(R.drawable.weather1);
            } else if(weather.equals("few clouds")) {
                imageViewWeather.setImageResource(R.drawable.weather2);
            } else if(weather.equals("scattered clouds")) {
                imageViewWeather.setImageResource(R.drawable.weather2);
            } else if(weather.equals("broken clouds")) {
                imageViewWeather.setImageResource(R.drawable.weather2);
            } else if(weather.equals("shower rain")) {
                imageViewWeather.setImageResource(R.drawable.weather3);
            } else if(weather.equals("rain")) {
                imageViewWeather.setImageResource(R.drawable.weather3);
            } else if(weather.equals("thunderstorm")) {
                imageViewWeather.setImageResource(R.drawable.weather4);
            } else if(weather.equals("snow")) {
                imageViewWeather.setImageResource(R.drawable.weather5);
            } else if(weather.equals("mist")) {
                imageViewWeather.setImageResource(R.drawable.weather2);
            } else {
                imageViewWeather.setImageResource(R.drawable.weather1);
            }

        }
    }
}
