package dev.pitlor.smssync.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev.pitlor.sms.Message;
import dev.pitlor.smssync.R;
import dev.pitlor.smssync.databinding.MessagesListItemBinding;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {
    private List<Message> messages;
    private LayoutInflater layoutInflater;

    public MessagesAdapter(Context context, List<Message> messages) {
        this.messages = messages;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
            if (message.getImage() != null) {
                Picasso.get().load(message.getImage()).into(itemView.messageImage);
            } else {
                itemView.messageImage.setVisibility(View.GONE);
            }
        }
    }
}
