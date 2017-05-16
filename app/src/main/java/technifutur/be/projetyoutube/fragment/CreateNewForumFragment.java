package technifutur.be.projetyoutube.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import technifutur.be.projetyoutube.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewForumFragment extends Fragment {

    public interface ForumCreatedListener {
        void forumCreated(String name,String message);
    }

    @BindView(R.id.create_forum_edittext_message)
    EditText createForumEdittextMessage;

    @BindView(R.id.create_forum_edittext_name)
    EditText createForumEdittextName;
    private ForumCreatedListener forumCreatedListener;

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
            forumCreatedListener.forumCreated(name,message);
        }
    }
}
