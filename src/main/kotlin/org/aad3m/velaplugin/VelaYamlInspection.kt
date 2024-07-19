package org.aad3m.velaplugin

import com.intellij.codeInspection.*
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.yaml.psi.YAMLFile
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.YAMLMapping

class VelaYamlInspection : LocalInspectionTool() {

    override fun getDisplayName(): String {
        return "Vela YAML Inspection"
    }

    override fun getShortName(): String {
        return "VelaYamlInspection"
    }

    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<ProblemDescriptor> {
        val descriptors = mutableListOf<ProblemDescriptor>()
        if (file !is YAMLFile) return descriptors.toTypedArray()

        val rootMapping = PsiTreeUtil.getChildOfType(file, YAMLMapping::class.java) ?: return descriptors.toTypedArray()

        // Custom validation logic
        validateMapping(rootMapping, manager, descriptors)

        return descriptors.toTypedArray()
    }

    private fun validateMapping(mapping: YAMLMapping, manager: InspectionManager, descriptors: MutableList<ProblemDescriptor>) {
        for (keyValue in mapping.keyValues) {
            validateKeyValue(keyValue, manager, descriptors)
        }
    }

    private fun validateKeyValue(keyValue: YAMLKeyValue, manager: InspectionManager, descriptors: MutableList<ProblemDescriptor>) {
        // Example: Validate the existence of required fields
        if (keyValue.keyText == "stages" && keyValue.value !is YAMLMapping) {
            descriptors.add(
                manager.createProblemDescriptor(
                    keyValue,
                    "Stages should be a mapping",
                    FixStagesMapping(),
                    ProblemHighlightType.ERROR,
                    true
                )
            )
        }
    }

    private class FixStagesMapping : LocalQuickFix {
        override fun getName(): String = "Fix stages mapping"

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val keyValue = descriptor.psiElement as? YAMLKeyValue ?: return

        }

        override fun getFamilyName(): String = name
    }
}