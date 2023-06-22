package sg.edu.np.mad.mad_assignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainSportAdapter extends RecyclerView.Adapter<MainSportAdapter.ViewHolder> {

    private List<Sport> dataList;

    public MainSportAdapter(List<Sport> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_sport_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Sport data = dataList.get(position);
        if(data.getId() == 1) holder.imageView.setImageResource(R.mipmap.badminton_round);
        else if(data.getId() == 2) holder.imageView.setImageResource(R.mipmap.netball_round);
        else if(data.getId() == 3) holder.imageView.setImageResource(R.mipmap.football_round);
        else if(data.getId() == 4) holder.imageView.setImageResource(R.mipmap.tennis_round);
        else holder.imageView.setImageResource(R.mipmap.basketball_round);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailSport.class);
                intent.putExtra("sport", data);
                v.getContext().startActivity(intent);
           }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sportsImage);
        }
    }
}