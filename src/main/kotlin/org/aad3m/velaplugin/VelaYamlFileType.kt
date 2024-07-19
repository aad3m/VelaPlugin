package org.aad3m.velaplugin

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object VelaYamlFileType : LanguageFileType(VelaYamlLanguage) {
    override fun getName() = "Vela YAML File"
    override fun getDescription() = "Vela YAML configuration file"
    override fun getDefaultExtension() = "vela.yml"
    override fun getIcon(): Icon = IconLoader.getIcon("/icons/vela.svg", javaClass)
}