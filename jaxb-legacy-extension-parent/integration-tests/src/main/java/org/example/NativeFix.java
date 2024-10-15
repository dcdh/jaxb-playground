package org.example;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {Person.class})
public class NativeFix {
}
