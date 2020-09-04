package org.sochidrive.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.sochidrive.weather.model.WeatherWeekData;



public class RecyclerWeekDataAdapter extends RecyclerView.Adapter<RecyclerWeekDataAdapter.ViewHolder> {
    private WeatherWeekData data;

    public RecyclerWeekDataAdapter(WeatherWeekData weatherWeekData) {
        this.data = weatherWeekData;
    }

    @NonNull
    @Override
    public RecyclerWeekDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week_rv_layout, parent, false);
        return new RecyclerWeekDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerWeekDataAdapter.ViewHolder holder, int position) {
        String date = data.getWeek().get(position).getDate();
        String degree = data.getWeek().get(position).getDegree();
        String weather = data.getWeek().get(position).getWeatherDesc();
        holder.setTextToTextView(date,degree,weather);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.getWeek().size();
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
            setImageWeather(weather);
        }

        private void setImageWeather(String nameWeather) {
            switch (nameWeather) {
                case "few clouds":
                case "scattered clouds":
                case "broken clouds":
                case "mist":
                    imageViewWeather.setImageResource(R.drawable.weather2);
                    break;
                case "shower rain":
                case "rain":
                    imageViewWeather.setImageResource(R.drawable.weather3);
                    break;
                case "thunderstorm":
                    imageViewWeather.setImageResource(R.drawable.weather4);
                    break;
                case "snow":
                    imageViewWeather.setImageResource(R.drawable.weather5);
                    break;
                case "clear sky":
                default:
                    imageViewWeather.setImageResource(R.drawable.weather1);
                    break;
            }
        }
    }
}
