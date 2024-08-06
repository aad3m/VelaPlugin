package org.aad3m.velaplugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.error.YAMLException
import java.util.regex.Pattern

class VelaYAMLChecker(private val project: Project) {

    fun checkVelaYAML(file: VirtualFile) {
        val psiFile: PsiFile? = PsiManager.getInstance(project).findFile(file)
        if (psiFile != null) {
            val content = psiFile.text
            val yaml = Yaml()
            try {
                val cleanedContent = removeTemplateSyntax(content)
                val data: Map<String, Any> = yaml.load(cleanedContent)
                validate(data)
            } catch (e: YAMLException) {
                // Handle parsing exceptions and print the error message
                println("Error parsing YAML: ${e.message}")
            } catch (e: Exception) {
                // Handle other exceptions
                e.printStackTrace()
            }
        }
    }

    private fun removeTemplateSyntax(content: String): String {
        // Remove lines starting with {{ and ending with }}
        val pattern = Pattern.compile("\\{\\{.*?\\}\\}", Pattern.DOTALL)
        val matcher = pattern.matcher(content)
        return matcher.replaceAll("")
    }

    private fun validate(data: Map<String, Any>) {
        val requiredKeys = listOf("version", "stages", "services", "secrets")
        for (key in requiredKeys) {
            if (!data.containsKey(key)) {
                // Log or show error for missing key
                println("Missing required key: $key")
            } else {
                println("Found key: $key")
            }
        }
    }
}
