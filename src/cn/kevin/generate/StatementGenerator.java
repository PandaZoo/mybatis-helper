package cn.kevin.generate;

import cn.kevin.dom.model.GroupTwo;
import cn.kevin.dom.model.Mapper;
import cn.kevin.service.EditorService;
import cn.kevin.service.JavaService;
import cn.kevin.setting.MybatisSetting;
import cn.kevin.ui.ListSelectionListener;
import cn.kevin.ui.UiComponentFacade;
import cn.kevin.util.CollectionUtils;
import cn.kevin.util.JavaUtils;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.CommonProcessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * @author yongkang.zhang
 */
public abstract class StatementGenerator {

    public static final StatementGenerator UPDATE_GENERATOR = new UpdateGenerator("update", "modify", "set");

    public static final StatementGenerator SELECT_GENERATOR = new SelectGenerator("select", "get", "look", "find", "list", "search", "count", "query");

    public static final StatementGenerator DELETE_GENERATOR = new DeleteGenerator("del", "cancel");

    public static final StatementGenerator INSERT_GENERATOR = new InsertGenerator("insert", "add", "new");

    public static final Set<StatementGenerator> ALL = ImmutableSet.of(UPDATE_GENERATOR, SELECT_GENERATOR, DELETE_GENERATOR, INSERT_GENERATOR);

    private static final Function<Mapper, String> FUN = mapper -> {
        VirtualFile vf = mapper.getXmlTag().getContainingFile().getVirtualFile();
        if (null == vf) return "";
        return vf.getCanonicalPath();
    };
    private Set<String> patterns;

    public StatementGenerator(@NotNull String... patterns) {
        this.patterns = Sets.newHashSet(patterns);
    }

    public static Optional<PsiClass> getSelectResultType(@Nullable PsiMethod method) {
        if (null == method) {
            return Optional.empty();
        }
        PsiType returnType = method.getReturnType();
        if (returnType instanceof PsiPrimitiveType && returnType != PsiType.VOID) {
            return JavaUtils.findClazz(method.getProject(), ((PsiPrimitiveType) returnType).getBoxedTypeName());
        } else if (returnType instanceof PsiClassReferenceType) {
            PsiClassReferenceType type = (PsiClassReferenceType) returnType;
            if (type.hasParameters()) {
                PsiType[] parameters = type.getParameters();
                if (parameters.length == 1) {
                    type = (PsiClassReferenceType) parameters[0];
                }
            }
            return Optional.ofNullable(type.resolve());
        }
        return Optional.empty();
    }

    public static void applyGenerate(@Nullable final PsiMethod method) {
        if (null == method) return;
        final StatementGenerator[] generators = getGenerators(method);
        if (1 == generators.length) {
            generators[0].execute(method);
        } else {
            UiComponentFacade.getInstance(method.getProject()).showListPopup("[ Select target statement ]", new ListSelectionListener() {
                @Override
                public void selected(int index) {
                    generators[index].execute(method);
                }

                @Override
                public boolean isWriteAction() {
                    return true;
                }

            }, generators);
        }
    }

    @NotNull
    public static StatementGenerator[] getGenerators(@NotNull PsiMethod method) {
        GenerateModel model = MybatisSetting.getInstance().getStatementGenerateModel();
        String target = method.getName();
        List<StatementGenerator> result = Lists.newArrayList();
        for (StatementGenerator generator : ALL) {
            if (model.matchesAny(generator.getPatterns(), target)) {
                result.add(generator);
            }
        }
        return CollectionUtils.isNotEmpty(result) ? result.toArray(new StatementGenerator[result.size()]) : ALL.toArray(new StatementGenerator[ALL.size()]);
    }

    public void execute(@NotNull final PsiMethod method) {
        PsiClass psiClass = method.getContainingClass();
        if (null == psiClass) return;
        CommonProcessors.CollectProcessor<Mapper> processor = new CommonProcessors.CollectProcessor<Mapper>();
        JavaService.getInstance(method.getProject()).process(psiClass, processor);
        final List<Mapper> mappers = Lists.newArrayList(processor.getResults());
        if (1 == mappers.size()) {
            setupTag(method, Iterables.getOnlyElement(mappers, null));
        } else if (mappers.size() > 1) {
            UiComponentFacade.getInstance(method.getProject()).showListPopup("Choose target mapper xml to generate", new ListSelectionListener() {
                @Override
                public void selected(int index) {
                    setupTag(method, mappers.get(index));
                }

                @Override
                public boolean isWriteAction() {
                    return true;
                }
            }, mappers.stream().map(FUN).toArray(String[]::new));
        }
    }

    private void setupTag(PsiMethod method, Mapper mapper) {
        GroupTwo target = getTarget(mapper, method);
        target.getId().setStringValue(method.getName());
        target.setValue(" ");
        XmlTag tag = target.getXmlTag();
        int offset = tag.getTextOffset() + tag.getTextLength() - tag.getName().length() + 1;
        EditorService editorService = EditorService.getInstance(method.getProject());
        editorService.format(tag.getContainingFile(), tag);
        editorService.scrollTo(tag, offset);
    }

    @Override
    public String toString() {
        return this.getDisplayText();
    }

    @NotNull
    protected abstract GroupTwo getTarget(@NotNull Mapper mapper, @NotNull PsiMethod method);

    @NotNull
    public abstract String getId();

    @NotNull
    public abstract String getDisplayText();

    public Set<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(Set<String> patterns) {
        this.patterns = patterns;
    }

}
