package ru.kizup.chat.view;

import android.view.View;
import android.widget.TextView;

import ru.kizup.chat.R;

/**
 * Created by kizup on 23.09.17.
 */

public class AnotherMessageViewHolder extends MessageViewHolder {

    private RoundImageView mIcon;
    private TextView mUserNameText;
    private TextView mMessageDateText;

    public AnotherMessageViewHolder(View itemView) {
        super(itemView);

        mIcon = (RoundImageView) itemView.findViewById(R.id.iv_user_avatar);
        mUserNameText = (TextView) itemView.findViewById(R.id.tv_user_name);
        mMessageDateText = (TextView) itemView.findViewById(R.id.tv_message_date);
    }

    public RoundImageView getIconView() {
        return mIcon;
    }

    public TextView getUserNameTextView() {
        return mUserNameText;
    }

    public TextView getMessageDateText() {
        return mMessageDateText;
    }
}
