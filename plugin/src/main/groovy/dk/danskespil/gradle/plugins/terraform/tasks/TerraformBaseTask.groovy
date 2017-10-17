package dk.danskespil.gradle.plugins.terraform.tasks

import dk.danskespil.gradle.plugins.terraform.plugin.CommandLine
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal

abstract class TerraformBaseTask extends DefaultTask {
    @Internal
    abstract String getDescription()
    @Internal
    String group = "Terraform"

    @Internal
    CommandLine commandLine = new CommandLine()
}
