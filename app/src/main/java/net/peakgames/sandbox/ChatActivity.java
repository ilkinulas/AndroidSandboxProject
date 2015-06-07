package net.peakgames.sandbox;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.peakgames.sandbox.mediator.ChatViewMediator;
import net.peakgames.sandbox.view.ChatView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ChatActivity extends BaseActivity implements ChatView {

    @InjectView(R.id.chat_message_edit_text)
    EditText chatMessageEditText;

    @InjectView(R.id.send_chat_message_button)
    Button sendButton;

    @Inject
    ChatViewMediator chatViewMediator;

    private RecyclerView recyclerView;
    private ChatMessageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        injectDependencies();

        chatViewMediator.onCreate();

        initializeChatMessageList();

        addTextChangedListener();
    }

    private void addTextChangedListener() {
        chatMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    sendButton.setEnabled(false);
                } else {
                    sendButton.setEnabled(true);
                }
            }
        });
    }

    private void initializeChatMessageList() {
        this.recyclerView = (RecyclerView) findViewById(R.id.chat_message_list);
        this.recyclerView.setHasFixedSize(true); //performance ???
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.adapter = new ChatMessageAdapter();
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatViewMediator.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatViewMediator.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatViewMediator.onDestroy();
    }

    private void injectDependencies() {
        ButterKnife.inject(this);
        getChatApplication().getChatAppComponent().inject(this);
        chatViewMediator.setView(this);
    }

    @OnClick(R.id.send_chat_message_button)
    public void onSendButtonClicked() {
        String message = chatMessageEditText.getText().toString();
        chatViewMediator.sendMessage(message);
        addMessageToRecyclerView(message);
        chatMessageEditText.setText("");
    }

    @Override
    public void onNewChatMessage(String message) {
        addMessageToRecyclerView(message);
    }

    private void addMessageToRecyclerView(String message) {
        this.adapter.addItem(message);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    private static class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {

        private List<String> items = new ArrayList();

        public void addItem(String item) {
            this.items.add(item);
            notifyItemInserted(items.size() - 1);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v =  LayoutInflater.from(viewGroup.getContext()) .inflate(R.layout.message_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Picasso.with(viewHolder.imageView.getContext()).cancelRequest(viewHolder.imageView);
            Picasso.with(viewHolder.imageView.getContext()).load("http://graph.facebook.com/" + getRandomUserId() + "/picture?type=square").into(viewHolder.imageView);
            viewHolder.textView.setText(items.get(i));
            viewHolder.itemView.setTag(i);

        }

        private int getRandomUserId() {
            return (int) (Math.random() * 1000);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public ImageView imageView;
            public ViewHolder(View v) {
                super(v);
                textView = (TextView) v.findViewById(R.id.chat_message_text);
                imageView = (ImageView) v.findViewById(R.id.chat_message_profile_picture);

            }
        }
    }
}
