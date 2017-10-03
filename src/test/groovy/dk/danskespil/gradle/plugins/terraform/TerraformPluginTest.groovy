package dk.danskespil.gradle.plugins.terraform

import dk.danskespil.gradle.plugins.helpers.DSSpecification
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Unroll

class TerraformPluginTest extends DSSpecification {
    def "I can apply the plugin"() {
        given:
        buildFile << """
            plugins {
                id 'dk.danskespil.gradle.plugins.terraform'
            }
        """

        when:
        def result = buildWithTasks('tasks')

        then:
        result
    }

    @Unroll
    def "I have #task in plugin"() {
        given:
        buildFile << """
        plugins {
            id 'dk.danskespil.gradle.plugins.terraform'
        }
        """

        when:
        def result = buildWithTasks(task)

        then:
        result
        result.task(":${task}").outcome == TaskOutcome.SUCCESS

        where:
        task << ['tfPlan', 'tfGet', 'tfInit']
    }

    def "output from Get is input to Plan"() {
        given:
        Project project = ProjectBuilder.builder()
                .withProjectDir(testProjectDir.root)
                .build()

        when:
        project.plugins.apply(TerraformPlugin)
        Plan plan = project.tasks.getByName('tfPlan')
        Get get = project.tasks.getByName('tfGet')

        then:
        plan
        plan.taskDependencies.getDependencies(plan).contains(get)
    }

    def "Plan depends on Init, so A user can call plan on a fresh clone, initialization is done automatically"() {
        given:
        Project project = ProjectBuilder.builder()
                .withProjectDir(testProjectDir.root)
                .build()

        when:
        project.plugins.apply(TerraformPlugin)
        Plan plan = project.tasks.getByName('tfPlan')
        Init init = project.tasks.getByName('tfInit')

        then:
        plan
        plan.taskDependencies.getDependencies(plan).contains(init)
    }

    def "Apply depends on Plan, so A user can call apply directly"() {
        given:
        Project project = ProjectBuilder.builder()
                .withProjectDir(testProjectDir.root)
                .build()

        when:
        project.plugins.apply(TerraformPlugin)
        Plan plan = project.tasks.getByName('tfPlan')
        Apply apply = project.tasks.getByName('tfApply')

        then:
        apply
        apply.taskDependencies.getDependencies(apply).contains(plan)
    }

    @Unroll
    def "Main task '#task' is described when running gradle tasks"() {
        given:
        buildFile << """
          plugins { 
              id 'dk.danskespil.gradle.plugins.terraform'
          }
          
        """

        when:
        def build = buildWithTasks('tasks')

        then:
        build.output.contains(task)

        where:
        task << ['tfPlan', 'tfGet', 'tfInit', 'tfApply']
    }

}
