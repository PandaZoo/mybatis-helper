package cn.kevin.alias;

import cn.kevin.util.JavaUtils;
import com.google.common.collect.Sets;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.spring.CommonSpringModel;
import com.intellij.spring.SpringManager;
import com.intellij.spring.model.SpringBeanPointer;
import com.intellij.spring.model.utils.SpringPropertyUtils;
import com.intellij.spring.model.xml.beans.SpringPropertyDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author yongkang.zhang
 */
public class BeanAliasResolver extends PackageAliasResolver {

    private static final String MAPPER_ALIAS_PACKAGE_CLASS = "org.mybatis.spring.SqlSessionFactoryBean";
    private static final String MAPPER_ALIAS_PROPERTY = "typeAliasesPackage";
    private ModuleManager moduleManager;
    private SpringManager springManager;

    public BeanAliasResolver(Project project) {
        super(project);
        this.moduleManager = ModuleManager.getInstance(project);
        this.springManager = SpringManager.getInstance(project);
    }

    @NotNull
    @Override
    public Collection<String> getPackages(@Nullable PsiElement element) {
        Set<String> res = Sets.newHashSet();
        for (Module module : moduleManager.getModules()) {
            for (CommonSpringModel springModel : springManager.getCombinedModel(module).getModelsToProcess()) {
                addPackages(res, springModel);
            }
        }
        return res;
    }

    /**
     * see <a href="https://github.com/Infeligo/free-idea-mybatis/blob/master/src/com/seventh7/mybatis/alias/BeanAliasResolver.java">
     * 兼容</a>
     *
     * @param res
     * @param springModel
     */
    private void addPackages(Set<String> res, CommonSpringModel springModel) {
        Optional<PsiClass> sqlSessionFactoryClazzOpt = JavaUtils.findClazz(project, MAPPER_ALIAS_PACKAGE_CLASS);
        if (sqlSessionFactoryClazzOpt.isPresent()) {
            Collection<SpringBeanPointer> allDomBeans = springModel.getAllDomBeans();
            PsiClass sqlSessionFactoryClazz = sqlSessionFactoryClazzOpt.get();
            for (Object domBean : allDomBeans) {
                SpringBeanPointer pointer = (SpringBeanPointer) domBean;
                PsiClass beanClass = pointer.getBeanClass();
                if (beanClass != null && beanClass.equals(sqlSessionFactoryClazz)) {
                    SpringPropertyDefinition basePackages = SpringPropertyUtils.findPropertyByName(
                            pointer.getSpringBean(), MAPPER_ALIAS_PROPERTY
                    );
                    if (basePackages != null) {
                        final String value = Objects.requireNonNull(basePackages.getValueElement()).getStringValue();
                        if (value != null) {
                            res.add(value);
                        }
                    }
                }
            }

        }
    }

}
