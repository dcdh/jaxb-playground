package org.example.deployment;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.BytecodeTransformerBuildItem;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;

public class JaxbLegacyExtensionProcessor {

    private static final String FEATURE = "Jaxb-legacy-extension";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void transformAllJavaxToJakartaClasses(final CombinedIndexBuildItem combinedIndexBuildItem,
                                           final BuildProducer<BytecodeTransformerBuildItem> transformers) {
        final IndexView index = combinedIndexBuildItem.getIndex();
        for (final ClassInfo classInfo : index.getKnownClasses()) {
            final String className = classInfo.name().toString();

            transformers.produce(new BytecodeTransformerBuildItem(className, (className2, classVisitor) -> new ImportTransformer(classVisitor)));
        }
    }

    @BuildStep
    void registerNativeXmlRootElementClasses(final CombinedIndexBuildItem combinedIndexBuildItem,
                                             final BuildProducer<ReflectiveClassBuildItem> reflectiveClassBuildItemBuildProducer) {
        final IndexView index = combinedIndexBuildItem.getIndex();

        final String[] classesHavingXmlRootElement = index.getKnownClasses()
                .stream()
                .filter(classInfo -> classInfo.hasAnnotation(DotName.createSimple("javax.xml.bind.annotation.XmlRootElement")))
                .map(ClassInfo::name)
                .map(DotName::toString)
                .toArray(String[]::new);
        reflectiveClassBuildItemBuildProducer.produce(
                ReflectiveClassBuildItem.builder(classesHavingXmlRootElement)
                        .constructors(true)
                        .methods(true)
                        .fields(true)
                        .weak(true)
                        .serialization(true)
                        .build()
        );
    }
}
