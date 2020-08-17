package dev.pitlor.smssync.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.pitlor.sms.Message;
import dev.pitlor.smssync.R;
import dev.pitlor.smssync.databinding.MessagesListItemBinding;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {
    private Context context;
    private List<Message> messages;

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MessagesListItemBinding view = DataBindingUtil.inflate(layoutInflater, R.layout.messages_list_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.setMessage(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private MessagesListItemBinding itemView;

        public MessageViewHolder(@NonNull MessagesListItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        public void setMessage(Message message) {
            itemView.setSender(message.getSender());
            itemView.setBody(message.getBody());
        }
    }
}
