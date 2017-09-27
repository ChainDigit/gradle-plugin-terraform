package dk.danskespil.gradle.plugins.terraform.dk.danskespil.gradle.plugins.dk.danskespil.gradle.plugins.helpers

class BadPathException extends RuntimeException{
    BadPathException(String badInput) {
        super("You are trying to parse '#{badInput}' as a path. Hm. Does not work. Try something like 'path/to/something'")
    }
}
