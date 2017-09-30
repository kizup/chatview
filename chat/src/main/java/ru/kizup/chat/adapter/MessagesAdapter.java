package ru.kizup.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.kizup.chat.R;
import ru.kizup.chat.util.ExtendedArrayList;
import ru.kizup.chat.view.AnotherMessageViewHolder;
import ru.kizup.chat.view.MessageViewHolder;
import ru.kizup.chat.model.Message;

import java.util.List;

/**
 * Created by kizup on 23.09.17.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private ExtendedArrayList<Message> mMessagesList;
    private Context mContext;

    public MessagesAdapter(Context context) {
        mContext = context;
        mMessagesList = new ExtendedArrayList<>();
    }

    public void receiveMessage(Message message) {
        mMessagesList.add(message);
        notifyItemInserted(mMessagesList.lastPosition());
    }

    public void receiveHistory(List<Message> messages) {
        mMessagesList.addAll(0, messages);
        this.notifyItemRangeInserted(0, messages.size());
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Message.ANOTHER_MESSAGE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
                return new AnotherMessageViewHolder(view);
            }
            case Message.SELF_MESSAGE: {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_self_message, parent, false);
                return new MessageViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = mMessagesList.get(position);
        switch (message.getType()) {
            case Message.ANOTHER_MESSAGE: {
                AnotherMessageViewHolder viewHolder = (AnotherMessageViewHolder) holder;
                if (message.getUser() != null) {
                    viewHolder.getUserNameTextView().setText(message.getUser().getName());
                }
                viewHolder.getMessageDateText().setText(message.getTime());
                viewHolder.getMessageTextView().setText(message.getMessage());
                break;
            }
            case Message.SELF_MESSAGE: {
                holder.getMessageTextView().setText(message.getMessage());
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mMessagesList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }

    public ExtendedArrayList<Message> getMessages() {
        return mMessagesList;
    }
}
