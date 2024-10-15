package org.example.deployment;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.BytecodeTransformerBuildItem;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.IndexView;

public class JaxbLegacyExtensionProcessor {

    private static final String FEATURE = "Jaxb-legacy-extension";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void transformClasses(final CombinedIndexBuildItem combinedIndexBuildItem,
                          final BuildProducer<BytecodeTransformerBuildItem> transformers) {
        final IndexView index = combinedIndexBuildItem.getIndex();

        for (final ClassInfo classInfo : index.getKnownClasses()) {
            final String className = classInfo.name().toString();

            transformers.produce(new BytecodeTransformerBuildItem(className, (className2, classVisitor) -> new ImportTransformer(classVisitor)));
        }
    }
}
