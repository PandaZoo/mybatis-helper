package cn.kevin.util;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.PlatformIcons;

import javax.swing.*;

/**
 * - iconName.png W x H pixels (Will be used on non-Retina devices with default look and feel)
 * - iconName@2x.png 2*W x 2*H pixels (Will be used on Retina devices with default look and feel)
 * - iconName_dark.png W x H pixels (Will be used on non-Retina devices with Darcula look and feel)
 * - iconName@2x_dark.png 2*W x 2*H pixels (Will be used on Retina devices with Darcula look and feel)
 * @author yongkang.zhang
 */
public interface Icons {

    Icon MYBATIS_LOGO = IconLoader.getIcon("/javaee/persistenceId.png");

    Icon PARAM_COMPLETION_ICON = PlatformIcons.PARAMETER_ICON;

    Icon MAPPER_LINE_MARKER_ICON = IconLoader.getIcon("/images/down.png");

    Icon STATEMENT_LINE_MARKER_ICON = IconLoader.getIcon("/images/up.png");

    Icon SPRING_INJECTION_ICON = IconLoader.getIcon("/images/injection.png");
}