package pl.alpheratzteam.obfuscator.transformer;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import pl.alpheratzteam.obfuscator.Obfuscator;
import pl.alpheratzteam.obfuscator.util.StringUtil;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author Unix
 * @since 14.04.20
 */

public class BadAnnotationTransformer extends Transformer
{
    private final Set<AnnotationNode> annotationSet;

    public BadAnnotationTransformer(Obfuscator obfuscator) {
        super(obfuscator);
        this.annotationSet = new HashSet<>();

        final String string = StringUtil.generateString(Short.MAX_VALUE);
        IntStream.range(0, 10).forEachOrdered(i -> this.annotationSet.add(new AnnotationNode("@" + string)));
    }

    @Override
    public void visit(Map<String, ClassNode> classMap) {
        classMap.values().forEach(classNode -> {
            if (Objects.isNull(classNode.visibleAnnotations))
                classNode.visibleAnnotations = new ArrayList<>();
            if (Objects.isNull(classNode.invisibleAnnotations))
                classNode.invisibleAnnotations = new ArrayList<>();

            classNode.visibleAnnotations.addAll(annotationSet);
            classNode.invisibleAnnotations.addAll(annotationSet);

            classNode.methods.forEach(methodNode -> {
                if (Objects.isNull(methodNode.visibleAnnotations))
                    methodNode.visibleAnnotations = new ArrayList<>();
                if (Objects.isNull(methodNode.invisibleAnnotations))
                    methodNode.invisibleAnnotations = new ArrayList<>();

                methodNode.visibleAnnotations.addAll(annotationSet);
                methodNode.invisibleAnnotations.addAll(annotationSet);
            });

            classNode.fields.forEach(fieldNode -> {
                if (Objects.isNull(fieldNode.visibleAnnotations))
                    fieldNode.visibleAnnotations = new ArrayList<>();
                if (Objects.isNull(fieldNode.invisibleAnnotations))
                    fieldNode.invisibleAnnotations = new ArrayList<>();

                fieldNode.visibleAnnotations.addAll(annotationSet);
                fieldNode.invisibleAnnotations.addAll(annotationSet);
            });
        });
    }
}