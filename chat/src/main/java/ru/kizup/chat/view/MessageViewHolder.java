package ru.kizup.chat.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.kizup.chat.R;

/**
 * Created by kizup on 23.09.17.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {

    protected TextView mMessageDateText;
    protected TextView mMessageText;

    public MessageViewHolder(View itemView) {
        super(itemView);
        mMessageDateText = (TextView) itemView.findViewById(R.id.tv_message_date);
        mMessageText = (TextView) itemView.findViewById(R.id.tv_message);
    }

    public TextView getMessageDateTextView() {
        return mMessageDateText;
    }

    public TextView getMessageTextView() {
        return mMessageText;
    }
}
