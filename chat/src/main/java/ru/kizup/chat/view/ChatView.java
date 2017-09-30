package ru.kizup.chat.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import ru.kizup.chat.R;
import ru.kizup.chat.adapter.MessagesAdapter;
import ru.kizup.chat.interfaces.OnRefreshChatListener;
import ru.kizup.chat.model.Message;

import java.util.List;

/**
 * Created by kizup on 23.09.17.
 */

public class ChatView extends RelativeLayout
        implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mMessagesList;
    private EditText mInputMessageEditText;
    private ImageButton mSendButton;
    private InputMethodManager mInputMethodManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private OnRefreshChatListener mOnRefreshChatListener;
    private LinearLayoutManager mLayoutManager;
    private MessagesAdapter mMessagesAdapter;
    private FloatingActionButton mFabScrollToBottom;

    private boolean isOnBottomList = false;

    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View parent = LayoutInflater.from(context).inflate(R.layout.chat_view, this);
        mMessagesList = (RecyclerView) parent.findViewById(R.id.message_list);
        mSendButton = (ImageButton) parent.findViewById(R.id.bn_send);
        mInputMessageEditText = (EditText) parent.findViewById(R.id.et_message_input);
        mSwipeRefreshLayout = (SwipeRefreshLayout) parent.findViewById(R.id.sr_refresh_chat);
        mFabScrollToBottom = (FloatingActionButton) parent.findViewById(R.id.fab_scroll_to_bottom);
        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        mMessagesAdapter = new MessagesAdapter(context);

        mLayoutManager = new LinearLayoutManager(context);
        mMessagesList.setLayoutManager(mLayoutManager);
        mMessagesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    isOnBottomList = true;
                    mFabScrollToBottom.hide();
                } else {
                    isOnBottomList = false;
                    mFabScrollToBottom.show();
                }

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (!isOnBottomList) mFabScrollToBottom.show();
                        break;
                    default:
                        if (isOnBottomList) mFabScrollToBottom.hide();
                        break;
                }
            }
        });
        mMessagesList.setAdapter(mMessagesAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mFabScrollToBottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToBottom();
            }
        });
    }

    private void scrollToBottom() {
        if (mMessagesAdapter.getItemCount() == 0) return;
        mMessagesList.smoothScrollToPosition(mMessagesAdapter.getItemCount() - 1);
    }

    public void clearInput() {
        mInputMessageEditText.setText("");
    }

    public void hideKeyboard() {
        mInputMethodManager.hideSoftInputFromWindow(getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        mMessagesList.requestFocus();
    }

    public boolean isEmptyInput() {
        return TextUtils.isEmpty(mInputMessageEditText.getText());
    }

    public String getInputText() {
        return mInputMessageEditText.getText().toString();
    }

    public void setOnSendClickListener(View.OnClickListener listener) {
        mSendButton.setOnClickListener(listener);
    }

    public void setOnRefreshChatListener(OnRefreshChatListener onRefreshChatListener) {
        mOnRefreshChatListener = onRefreshChatListener;
    }

    public void loadHistory() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void send(Message message) {
        mMessagesAdapter.receiveMessage(message);
        clearInput();
        hideKeyboard();
        scrollToBottom();
    }

    public void addMessage(final Message message) {
        post(new Runnable() {
            @Override
            public void run() {
                mMessagesAdapter.receiveMessage(message);
                if (isOnBottomList) {
                    scrollToBottom();
                }
            }
        });
    }

    public void addHistory(List<Message> history) {
        mSwipeRefreshLayout.setRefreshing(false);
        mMessagesAdapter.receiveHistory(history);
    }

    @Override
    public void onRefresh() {
        if (mOnRefreshChatListener != null) {
            mOnRefreshChatListener.onRefresh(mMessagesAdapter.getMessages().first().getId());
        }
    }
}
