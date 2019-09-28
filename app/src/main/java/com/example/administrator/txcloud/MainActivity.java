package com.example.administrator.txcloud;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.GroupChatManagerKit;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit;
import com.tencent.qcloud.tim.uikit.utils.FileUtil;

public class MainActivity extends AppCompatActivity {
    private int mCurrentTab;
    private TextView mConversationBtn;
    private TextView mContactBtn;
    private TextView mLastButton;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        // 从布局文件中获取会话列表面板
//        ConversationLayout conversationLayout = findViewById(R.id.conversation_layout);
//        // 初始化聊天面板
//        conversationLayout.initDefault();

        //title bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }
    public void logout(boolean autoLogin) {
        DemoLog.i(TAG, "logout");
        autoLogin(autoLogin);
        clean();
        Intent intent = new Intent(this, LoginForDevActivity.class);
        intent.putExtra(Constans.LOGOUT, true);
        startActivity(intent);
    }
    private void autoLogin(boolean autoLogin) {
        SharedPreferences shareInfo = getSharedPreferences(Constans.USERINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shareInfo.edit();
        editor.putBoolean(Constans.AUTO_LOGIN, autoLogin);
        editor.commit();
    }
    private void clean() {
        ConversationManagerKit.getInstance().destroyConversation();
        TUIKit.removeIMEventListener(null);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mConversationBtn = findViewById(R.id.conversation);
        mContactBtn = findViewById(R.id.contact);
       // mProfileSelfBtn = findViewById(R.id.mine);
        getFragmentManager().beginTransaction().replace(R.id.empty_view, new ConversationFragment()).commitAllowingStateLoss();
        FileUtil.initPath(); // 从application移入到这里，原因在于首次装上app，需要获取一系列权限，如创建文件夹，图片下载需要指定创建好的文件目录，否则会下载本地失败，聊天页面从而获取不到图片、表情

//        // 未读消息监视器
//        ConversationManagerKit.getInstance().addUnreadWatcher((ConversationManagerKit.MessageUnreadWatcher) this);
//        GroupChatManagerKit.getInstance();
//        mLastButton = mConversationBtn;

    }
    public void tabClick(View view) {
        if (mCurrentTab == view.getId()) {
            return;
        }
        mCurrentTab = view.getId();
        Fragment current = null;
        changeMenuState();
        switch (view.getId()) {
            case R.id.conversation_btn_group:
                if (mLastButton == mConversationBtn) {
                    return;
                }
                current = new ConversationFragment();;
                mConversationBtn.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
                mConversationBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.conversation_selected), null, null);
                mLastButton = mConversationBtn;
                break;
            case R.id.contact_btn_group:
                if (mLastButton == mContactBtn) {
                    return;
                }
                current = new ContactFragment();
                mContactBtn.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
                mContactBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.contact_selected), null, null);
                mLastButton = mContactBtn;
                break;
        }

        if (current != null && !current.isAdded()) {
            getFragmentManager().beginTransaction().replace(R.id.empty_view, current).commitAllowingStateLoss();
            getFragmentManager().executePendingTransactions();
        } else {
            DemoLog.w(TAG, "fragment added!");
        }
    }
    private void changeMenuState() {
        if (mLastButton == null) {
            return;
        }
        switch (mLastButton.getId()) {
            case R.id.conversation:
                mConversationBtn.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                mConversationBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.conversation_normal), null, null);
                break;
            case R.id.contact:
                mContactBtn.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                mContactBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.contact_normal), null, null);
                break;

        }
    }

}
