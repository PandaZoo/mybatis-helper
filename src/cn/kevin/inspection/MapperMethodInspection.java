package cn.kevin.inspection;

import cn.kevin.annotation.Annotation;
import cn.kevin.dom.model.Select;
import cn.kevin.generate.StatementGenerator;
import cn.kevin.locator.MapperLocator;
import cn.kevin.service.JavaService;
import cn.kevin.util.JavaUtils;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * inspect problem method
 * @author yongkang.zhang
 */
public class MapperMethodInspection extends MapperInspection {

    /**
     * overwrite checkMethod to find ProblemDescriptor
     */
    @Nullable
    @Override
    public ProblemDescriptor[] checkMethod(@NotNull PsiMethod method, @NotNull InspectionManager manager, boolean isOnTheFly) {
        // could process method or isAnnotationedWith Annotation.STATEMENT_SYMMETRIES
        if (!MapperLocator.getInstance(method.getProject()).process(method) || JavaUtils.isAnyAnnotationPresent(method, Annotation.STATEMENT_SYMMETRIES))
            return EMPTY_ARRAY;
        List<ProblemDescriptor> res = createProblemDescriptors(method, manager, isOnTheFly);
        return res.toArray(new ProblemDescriptor[res.size()]);
    }

    private List<ProblemDescriptor> createProblemDescriptors(PsiMethod method, InspectionManager manager, boolean isOnTheFly) {
        ArrayList<ProblemDescriptor> res = new ArrayList<>();
        Optional<ProblemDescriptor> p1 = checkStatementExists(method, manager, isOnTheFly);
        p1.ifPresent(res::add);
        Optional<ProblemDescriptor> p2 = checkResultType(method, manager, isOnTheFly);
        p2.ifPresent(res::add);
        return res;
    }

    private Optional<ProblemDescriptor> checkResultType(PsiMethod method, InspectionManager manager, boolean isOnTheFly) {
        Optional<DomElement> ele = JavaService.getInstance(method.getProject()).findStatement(method);
        if (ele.isPresent()) {
            DomElement domElement = ele.get();
            if (domElement instanceof Select) {
                Select select = (Select) domElement;
                Optional<PsiClass> target = StatementGenerator.getSelectResultType(method);
                PsiClass clazz = select.getResultType().getValue();
                PsiIdentifier ide = method.getNameIdentifier();
                if (null != ide && null == select.getResultMap().getValue()) {
                    if (target.isPresent() && (null == clazz || !target.get().equals(clazz))) {
                        return Optional.of(manager.createProblemDescriptor(ide, "Result type not match for select id=\"#ref\"",
                                new ResultTypeQuickFix(select, target.get()), ProblemHighlightType.GENERIC_ERROR, isOnTheFly));
                    } else if (!target.isPresent() && null != clazz) {
                        return Optional.of(manager.createProblemDescriptor(ide, "Result type not match for select id=\"#ref\"",
                                (LocalQuickFix) null, ProblemHighlightType.GENERIC_ERROR, isOnTheFly));
                    }
                }
            }
        }
        return Optional.empty();
    }

    private Optional<ProblemDescriptor> checkStatementExists(PsiMethod method, InspectionManager manager, boolean isOnTheFly) {
        PsiIdentifier ide = method.getNameIdentifier();
        if (!JavaService.getInstance(method.getProject()).findStatement(method).isPresent() && null != ide) {
            return Optional.of(manager.createProblemDescriptor(ide, "Statement with id=\"#ref\" not defined in mapper xml",
                    new StatementNotExistsQuickFix(method), ProblemHighlightType.GENERIC_ERROR, isOnTheFly));
        }
        return Optional.empty();
    }

}
