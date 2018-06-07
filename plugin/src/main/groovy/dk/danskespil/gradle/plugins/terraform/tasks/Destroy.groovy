package dk.danskespil.gradle.plugins.terraform.tasks

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecSpec

/**
 * Wraps cli command: terraform destroy
 */
class Destroy extends TerraformBaseTask implements TerraformVariables {
    @Optional
    @Input
    boolean autoApprove = false

    @TaskAction
    action() {
        commandLine.addToEnd('terraform', 'destroy')

        if (autoApprove) {
            commandLine.addToEnd('-auto-approve')
        }
        addVariablesToEnd(commandLine)

        executor.executeExecSpec(this, { ExecSpec e ->
            e.commandLine this.commandLine
            e.workingDir project.projectDir
        })
    }

    @Override
    String getDescription() {
        return """Wraps cli command: terraform destroy"""
    }
}
