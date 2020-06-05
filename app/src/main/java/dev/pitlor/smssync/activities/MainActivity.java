package dev.pitlor.smssync.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dev.pitlor.smssync.databinding.ActivityMainBinding;
import dev.pitlor.smssync.util.PermissionUtilities;
import dev.pitlor.smssync.util.TelephonyUtilities;

public class MainActivity extends AppCompatActivity {
    public static final int READ_SMS_CODE = 0;
    private ActivityMainBinding view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        view.smsList.setHasFixedSize(true);
        view.smsList.setLayoutManager(new LinearLayoutManager(this));
        if (PermissionUtilities.AssertPermission(this, Manifest.permission.READ_SMS, READ_SMS_CODE)) {
            listTexts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_SMS_CODE:
                listTexts();
        }
    }

    private void listTexts() {
        view.smsList.setAdapter(new MyAdapter(TelephonyUtilities.getAllSmsFromProvider(this, 10)));
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<String> mDataset;

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(List<String> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new MyViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.textView.setText(mDataset.get(position));
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;

        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }
}