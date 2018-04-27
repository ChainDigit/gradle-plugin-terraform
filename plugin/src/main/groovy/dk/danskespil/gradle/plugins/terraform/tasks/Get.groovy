package dk.danskespil.gradle.plugins.terraform.tasks

import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecSpec

class Get extends TerraformBaseTask {
    @OutputFiles
    FileCollection outputFiles = project.fileTree('.terraform/modules')

    @TaskAction
    action() {
        commandLine.addToEnd('terraform', 'get')

        executor.executeExecSpec(this, { ExecSpec e ->
            e.commandLine this.commandLine
            e.workingDir project.projectDir
        })
    }

    @Override
    String getDescription() {
        return """wraps terraform get. When using the plugin, its called automatically when you call tfPlan"""
    }
}
