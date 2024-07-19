package org.aad3m.velaplugin

import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory
import org.aad3m.velaplugin.VelaYamlFileType

class VelaYamlFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(consumer: FileTypeConsumer) {
        consumer.consume(VelaYamlFileType, ".vela.yml")
    }
}