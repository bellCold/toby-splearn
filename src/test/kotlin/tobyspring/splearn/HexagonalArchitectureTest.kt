package tobyspring.splearn

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures

@AnalyzeClasses(packages = ["tobyspring.splearn"], importOptions = [ImportOption.DoNotIncludeTests::class])
class HexagonalArchitectureTest {
    @ArchTest
    fun hexagonalArchitecture(classes: JavaClasses) {
        Architectures.layeredArchitecture()
            .consideringAllDependencies()
            .layer("domain").definedBy("tobyspring.splearn.domain..")
            .layer("application").definedBy("tobyspring.splearn.application..")
            .layer("adapter").definedBy("tobyspring.splearn.adapter..")
            .whereLayer("domain").mayOnlyBeAccessedByLayers("application", "adapter")
            .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")
            .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
            .check(classes)
    }
}