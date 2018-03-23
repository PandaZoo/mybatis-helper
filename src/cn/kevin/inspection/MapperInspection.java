package cn.kevin.inspection;

import com.intellij.codeInspection.BaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemDescriptor;

/**
 * extends BaseJavaLocalInspectionTool to gain check elelmnt method.
 * @author yongkang.zhang
 */
public abstract class MapperInspection extends BaseJavaLocalInspectionTool {

    public static final ProblemDescriptor[] EMPTY_ARRAY = new ProblemDescriptor[0];

}
