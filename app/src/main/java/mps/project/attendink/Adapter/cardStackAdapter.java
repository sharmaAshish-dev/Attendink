package mps.project.attendink.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import mps.project.attendink.Model.studentModel;
import mps.project.attendink.R;

public class cardStackAdapter extends ArrayAdapter<studentModel> {

    private Context mContext;
    private int mResource;

    public cardStackAdapter(@NonNull Context context, int resource, @NonNull List<studentModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final String ID = getItem(position).getId();
        final String sName = getItem(position).getName();
        final String userName = getItem(position).getUsername();
        final String sEmail = getItem(position).getEmail();
        final String sPhone = getItem(position).getPhone();
        final String sWebsite = getItem(position).getWebsite();
        final String sImage = getItem(position).getStudentImage();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView studentName = convertView.findViewById(R.id.name);
        TextView studentPhone = convertView.findViewById(R.id.phone);
        TextView studentEmail = convertView.findViewById(R.id.email);
        ImageView studentImage = convertView.findViewById(R.id.studentImage);

        studentName.setText(sName);
        studentPhone.setText(sPhone);
        studentEmail.setText(sEmail);

        Glide.with(mContext)
                .load(sImage)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(studentImage);

        return convertView;
    }
}
