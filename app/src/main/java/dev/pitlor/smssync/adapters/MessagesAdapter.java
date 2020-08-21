package dev.pitlor.smssync.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev.pitlor.sms.Message;
import dev.pitlor.smssync.R;
import dev.pitlor.smssync.databinding.MessagesListItemBinding;
import dev.pitlor.smssync.viewmodels.ConversationsListItemViewModel;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {
    private List<Message> messages;
    private LayoutInflater layoutInflater;

    public MessagesAdapter(@NonNull Context context, List<Message> messages) {
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
            itemView.setViewModel(new ConversationsListItemViewModel());
        }

        public void setMessage(Message message) {
            itemView.getViewModel().setMessage(message);
        }
    }
}
