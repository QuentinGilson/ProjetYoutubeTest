package technifutur.be.projetyoutube.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.imageshack.client.ImageShackClient;
import com.imageshack.listener.ResponseListener;
import com.imageshack.model.ImageShackModel;
import com.imageshack.model.UploadModel;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.animation.ResizeAnimationHeight;
import technifutur.be.projetyoutube.item.AchievementItem;
import technifutur.be.projetyoutube.model.youtube.Achievement;
import technifutur.be.projetyoutube.model.youtube.User;
import technifutur.be.projetyoutube.realm.RealmManager;
import technifutur.be.projetyoutube.sendBird.SendBirdManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionFragment extends Fragment {


    @BindView(R.id.name_settings)
    TextView nameSettings;
    @BindView(R.id.button_update_image_profile)
    Button buttonUpdateImageProfile;
    @BindView(R.id.button_update_name_profile)
    Button buttonUpdateNameProfile;
    @BindView(R.id.edittext_change_name)
    EditText edittextChangeName;
    @BindView(R.id.image_profile_settings)
    CircleImageView imageProfileSettings;
    @BindView(R.id.layout_edit_profil)
    RelativeLayout layoutEditProfil;
    @BindView(R.id.recyclerview_achievments)
    RecyclerView recyclerviewAchievments;

    private User user;
    private boolean isSettingsOpen = false;
    private boolean isAchievementOpen = false;
    private FastItemAdapter<AbstractItem> achievementAdapter;

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

        initRecyclerView();

        return view;
    }

    private void initRecyclerView(){
        achievementAdapter = new FastItemAdapter<>();
        recyclerviewAchievments.setAdapter(achievementAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewAchievments.setLayoutManager(gridLayoutManager);

        List<Achievement> allAchievement = RealmManager.getRealmManager().getAllAchievement();
        for(Achievement achievement : allAchievement){
            achievementAdapter.add(new AchievementItem(achievement));
        }
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
                            RealmManager.getRealmManager().setUserImage(upload.getImages().get(0).getDirectLink());
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

    @OnClick({R.id.button_update_image_profile, R.id.button_update_name_profile, R.id.button_show_achievement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_update_image_profile:
                EasyImage.openChooserWithGallery(this, "Choisir nouvelle image", 0);
                break;
            case R.id.button_update_name_profile:
                if (!edittextChangeName.getText().toString().isEmpty()) {
                    String nametxt = edittextChangeName.getText().toString();
                    RealmManager.getRealmManager().setUserName(nametxt.substring(0, 1).toUpperCase() + nametxt.substring(1));

                    edittextChangeName.setText("");
                    nameSettings.setText(user.getName());

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                    Achievement achievement = RealmManager.getRealmManager().getAchievement("His name is !");
                    RealmManager.getRealmManager().setAchievementCount(achievement);
                    ShowAchievementFragment showAchievementFragment = ShowAchievementFragment.newInstance(achievement);
                    getFragmentManager().beginTransaction().add(R.id.activity_pager,showAchievementFragment).addToBackStack(null).commit();
                    achievementAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.button_show_achievement:
                ResizeAnimationHeight anim = new ResizeAnimationHeight(recyclerviewAchievments);
                anim.setDuration(500);
                if(isAchievementOpen){
                    anim.setParams(400, 1);
                }else{
                    anim.setParams(recyclerviewAchievments.getLayoutParams().height,400);
                }
                isAchievementOpen = !isAchievementOpen;
                recyclerviewAchievments.startAnimation(anim);
                break;
        }
    }

    @OnClick(R.id.button_show_settings)
    public void onClick() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutEditProfil.getLayoutParams();
        ResizeAnimationHeight a = new ResizeAnimationHeight(layoutEditProfil);
        a.setDuration(500);
        if (isSettingsOpen) {
            a.setParams(200, 1);
        } else {
            a.setParams(lp.height, 200);
        }
        isSettingsOpen = !isSettingsOpen;
        layoutEditProfil.startAnimation(a);
    }
}
