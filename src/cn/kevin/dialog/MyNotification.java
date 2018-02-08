package cn.kevin.dialog;

import com.intellij.notification.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * created by yongkang.zhang
 * added at 2018/2/1
 */
public class MyNotification extends Notification {

    private static final String GROUP_ID = "mybatis po sync notification";

    static {
        NotificationGroup group = new NotificationGroup(GROUP_ID, NotificationDisplayType.BALLOON, false);
    }

    public MyNotification(@Nullable String content, @NotNull NotificationType type, @Nullable NotificationListener listener) {
        super(GROUP_ID, null, "Mybatis Po Sync Notification", null, content, type, listener);
    }
}
