package org.aad3m.velaplugin

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.error.YAMLException
import java.util.regex.Pattern

class VelaYAMLAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val text = element.text
        val yaml = Yaml()
        try {
            val cleanedText = removeTemplateSyntax(text)
            val data: Map<String, Any> = yaml.load(cleanedText)
            // Validate the data and create annotations for issues
            validate(data, holder, element)
        } catch (e: YAMLException) {
            // Handle parsing exceptions
            holder.createErrorAnnotation(element, "Invalid YAML format: ${e.message}")
        }
    }

    private fun removeTemplateSyntax(content: String): String {
        // Remove lines starting with {{ and ending with }}
        val pattern = Pattern.compile("\\{\\{.*?\\}\\}", Pattern.DOTALL)
        val matcher = pattern.matcher(content)
        return matcher.replaceAll("")
    }

    private fun validate(data: Map<String, Any>, holder: AnnotationHolder, element: PsiElement) {
        val requiredKeys = listOf("version", "stages", "services", "secrets")
        for (key in requiredKeys) {
            if (!data.containsKey(key)) {
                holder.createErrorAnnotation(element, "Missing required key: $key")
            }
        }
    }
}
