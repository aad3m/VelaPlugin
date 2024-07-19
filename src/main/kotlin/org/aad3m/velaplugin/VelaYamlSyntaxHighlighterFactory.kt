package org.aad3m.velaplugin

import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory
import org.aad3m.velaplugin.VelaYamlSyntaxHighlighter

class VelaYamlSyntaxHighlighterFactory : SingleLazyInstanceSyntaxHighlighterFactory() {
    override fun createHighlighter() = VelaYamlSyntaxHighlighter()
}