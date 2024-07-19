package org.aad3m.velaplugin

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.jetbrains.yaml.YAMLParserDefinition
import org.jetbrains.yaml.YAMLTokenTypes

class VelaYamlSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = YAMLParserDefinition().createLexer(null)

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            YAMLTokenTypes.TEXT -> TEXT_KEYS
            YAMLTokenTypes.SCALAR_KEY -> KEY_KEYS
            YAMLTokenTypes.COMMENT -> COMMENT_KEYS
            // Custom highlighting for anchors and templates
            YAMLTokenTypes.ANCHOR -> ANCHOR_KEYS
            YAMLTokenTypes.ALIAS -> ALIAS_KEYS
            else -> EMPTY_KEYS
        }
    }

    companion object {
        private val TEXT_KEYS = arrayOf(DefaultLanguageHighlighterColors.STRING)
        private val KEY_KEYS = arrayOf(DefaultLanguageHighlighterColors.KEYWORD)
        private val COMMENT_KEYS = arrayOf(DefaultLanguageHighlighterColors.LINE_COMMENT)
        private val ANCHOR_KEYS = arrayOf(DefaultLanguageHighlighterColors.LABEL)
        private val ALIAS_KEYS = arrayOf(DefaultLanguageHighlighterColors.IDENTIFIER)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }
}