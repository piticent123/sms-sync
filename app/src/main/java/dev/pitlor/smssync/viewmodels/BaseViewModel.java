package dev.pitlor.smssync.viewmodels;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.io.File;

public class BaseViewModel {
    @BindingAdapter("visibleIf")
    public static void visibleIf(View view, Boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("imageUrl")
    public void setImage(ImageView imageView, File image) {
        Picasso.get().load(image).into(imageView);
    }
}
