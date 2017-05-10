package technifutur.be.projetyoutube.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import technifutur.be.projetyoutube.model.youtube.User;
import technifutur.be.projetyoutube.realm.RealmManager;
import technifutur.be.projetyoutube.sendBird.SendBirdManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionFragment extends Fragment {


    @BindView(R.id.image_profile_settings)
    ImageView imageProfileSettings;
    @BindView(R.id.name_settings)
    TextView nameSettings;
    @BindView(R.id.button_update_image_profile)
    Button buttonUpdateImageProfile;
    @BindView(R.id.button_update_name_profile)
    Button buttonUpdateNameProfile;
    @BindView(R.id.edittext_change_name)
    EditText edittextChangeName;
    private User user;

    public OptionFragment() {
        // Required empty public constructor
    }

    public static OptionFragment newInstance() {

        Bundle args = new Bundle();

        OptionFragment fragment = new OptionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_option, container, false);
        ButterKnife.bind(this, view);

        user = RealmManager.getRealmManager().getUser();
        reloadImage();
        nameSettings.setText(user.getName());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                ImageShackClient client = new ImageShackClient("3FHPQTUY5cf50cd3945ca3354da0b56b13276e14", "6064e53f11c7264fa198715ef812f5e2");
                try {
                    client.uploadImage(imageFiles.get(0).getPath(), null, null, null, true, true, new ResponseListener() {
                        @Override
                        public void onResponse(ImageShackModel model) {
                            UploadModel upload = (UploadModel) model;
                            RealmManager.getRealmManager().setImage(upload.getImages().get(0).getDirectLink());
                            SendBirdManager.updateUserSetting(user.getName(), user.getImage());
                            reloadImage();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void reloadImage() {
        Glide.with(getContext()).load("http://" + user.getImage()).into(imageProfileSettings);
    }

    @OnClick({R.id.button_update_image_profile, R.id.button_update_name_profile})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_update_image_profile:
                EasyImage.openChooserWithGallery(this, "Choisir nouvelle image", 0);
                break;
            case R.id.button_update_name_profile:
                if(!edittextChangeName.getText().toString().isEmpty()) {
                    RealmManager.getRealmManager().setName(edittextChangeName.getText().toString());
                    edittextChangeName.setText("");
                    nameSettings.setText(user.getName());
                }
                break;
        }
    }
}
