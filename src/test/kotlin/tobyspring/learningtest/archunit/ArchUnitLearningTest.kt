package tobyspring.learningtest.archunit

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

@AnalyzeClasses
class ArchUnitLearningTest {
    @ArchTest
    fun application(classes: JavaClasses) {
        classes().that().resideInAPackage("..application..")
            .should().onlyHaveDependentClassesThat().resideInAnyPackage("..application..", "..adapter..")
            .check(classes)
    }

    @ArchTest
    fun adapter(classes: JavaClasses) {
        noClasses().that().resideInAnyPackage("..application..")
            .should().dependOnClassesThat().resideInAPackage("..adapter..")
            .check(classes)
    }

    @ArchTest
    fun domain(classes: JavaClasses) {
        classes().that().resideInAPackage("..domain..")
            .should().onlyDependOnClassesThat().resideInAnyPackage("..domain..", "java..", "kotlin..", "org.jetbrains.annotations..")
            .check(classes)
    }
}