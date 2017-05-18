package technifutur.be.projetyoutube.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.imageshack.client.ImageShackClient;
import com.imageshack.listener.ResponseListener;
import com.imageshack.model.ImageShackModel;
import com.imageshack.model.UploadModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.realm.RealmManager;
import technifutur.be.projetyoutube.sendBird.SendBirdManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewForumFragment extends Fragment {

    public interface ForumCreatedListener {
        void forumCreated(String name, String message, String imagePath);
    }

    @BindView(R.id.create_forum_edittext_message)
    EditText createForumEdittextMessage;
    @BindView(R.id.create_forum_fragment_image)
    ImageView createForumFragmentImage;
    @BindView(R.id.create_forum_edittext_name)
    EditText createForumEdittextName;
    private ForumCreatedListener forumCreatedListener;
    private String imagePath = "http://imageshack.com/a/img923/7205/T1xEDj.jpg";

    public CreateNewForumFragment() {
        // Required empty public constructor
    }

    public static CreateNewForumFragment newInstance(ForumCreatedListener listener) {
        CreateNewForumFragment fragment = new CreateNewForumFragment();
        fragment.forumCreatedListener = listener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_forum, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_create_forum_add)
    public void onViewClicked() {
        String name = createForumEdittextName.getText().toString();
        String message = createForumEdittextMessage.getText().toString();
        if (!name.isEmpty() && !message.isEmpty()) {
            forumCreatedListener.forumCreated(name, message, imagePath);
        }
    }

    @OnClick(R.id.create_forum_button_add_image)
    public void onClick() {
        EasyImage.openChooserWithGallery(this, "Choisir nouvelle image", 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                Glide.with(getContext()).load(imageFiles.get(0).getPath()).into(createForumFragmentImage);
                ImageShackClient client = new ImageShackClient("3FHPQTUY5cf50cd3945ca3354da0b56b13276e14", "6064e53f11c7264fa198715ef812f5e2");
                try {
                    client.uploadImage(imageFiles.get(0).getPath(), null, null, null, true, true, new ResponseListener() {
                        @Override
                        public void onResponse(ImageShackModel model) {
                            UploadModel upload = (UploadModel) model;
                            imagePath = "http://"+upload.getImages().get(0).getDirectLink();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
