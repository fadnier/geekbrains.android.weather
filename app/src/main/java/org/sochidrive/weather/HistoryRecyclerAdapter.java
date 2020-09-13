package org.sochidrive.weather;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.sochidrive.weather.model.EducationSource;
import org.sochidrive.weather.model.sql.Weather;

import java.util.Date;
import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {
    private EducationSource dataSource;

    public HistoryRecyclerAdapter(EducationSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_rv_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Weather> students = dataSource.getWeaters();
        Weather weather = students.get(position);
        holder.setTextToTextView(weather.dt, weather.city, weather.temp);
    }

    @Override
    public int getItemCount() {
        return (int) dataSource.getCountWeather();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dt;
        private TextView city;
        private TextView degree;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dt = itemView.findViewById(R.id.itemTextViewHistoryDt);
            city = itemView.findViewById(R.id.itemTextViewHistoryCity);
            degree = itemView.findViewById(R.id.itemTextViewHistoryDegree);
        }

        void setTextToTextView(Long dt, String city, String degree) {
            String dateString = DateFormat.format("dd.MM.yyyy", new Date(dt)).toString();
            this.dt.setText(dateString);
            this.city.setText(city);
            this.degree.setText(degree);
        }

    }

}
