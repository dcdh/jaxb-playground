package org.example.deployment;

import org.objectweb.asm.*;

public class ImportTransformer extends ClassVisitor {

    public ImportTransformer(ClassVisitor cv) {
        super(Opcodes.ASM9, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println("Visiting class: " + name);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        descriptor = transformDescriptor(descriptor);
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        descriptor = transformDescriptor(descriptor);
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new MethodAdapter(mv);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        descriptor = transformDescriptor(descriptor);
        return super.visitAnnotation(descriptor, visible);
    }

    private String transformDescriptor(String descriptor) {
        if (descriptor != null && descriptor.contains("javax")) {
            descriptor = descriptor.replace("javax", "jakarta");
            System.out.println("Descriptor transformed: " + descriptor);
        }
        return descriptor;
    }

    class MethodAdapter extends MethodVisitor {
        public MethodAdapter(MethodVisitor mv) {
            super(Opcodes.ASM9, mv);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            type = transformDescriptor(type);
            super.visitTypeInsn(opcode, type);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            owner = transformDescriptor(owner);
            descriptor = transformDescriptor(descriptor);
            super.visitFieldInsn(opcode, owner, name, descriptor);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            owner = transformDescriptor(owner);
            descriptor = transformDescriptor(descriptor);
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }
}
