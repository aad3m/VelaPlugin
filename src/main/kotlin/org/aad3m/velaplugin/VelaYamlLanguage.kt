package org.aad3m.velaplugin

import com.intellij.lang.Language

object VelaYamlLanguage : Language("VelaYAML") {
    private fun readResolve(): Any = VelaYamlLanguage
}