package dev.pitlor.smssync.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.pitlor.sms.Message;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.TextViewHolder> {
    private List<Message> dataSet;

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater
            .from(parent.getContext())
            .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new TextViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        holder.textView.setText(dataSet.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public TextViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }
}
