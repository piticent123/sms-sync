package dev.pitlor.smssync.viewmodels;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.io.File;

import dev.pitlor.sms.Message;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ConversationsListItemViewModel extends BaseViewModel {
    @Setter public Message message;
}
