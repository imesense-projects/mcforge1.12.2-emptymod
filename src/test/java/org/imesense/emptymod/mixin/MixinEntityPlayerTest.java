package org.imesense.boilerplate.mcforge.mixin;

import org.junit.jupiter.api.Test;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MixinEntityPlayerTest
{
    private static final String MIXIN_CLASS =
        "org.imesense.boilerplate.mcforge.mixin.MixinEntityPlayer";
    private static final String METHOD_NAME =
        "onPlayerUpdate";
    private static final String METHOD_DESCRIPTOR =
        "(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V";
    private static final String METHOD_ANNOTATION =
        "Lorg/spongepowered/asm/mixin/injection/Inject;";

    @Test
    public void mixinEntityPlayer_Class_ChecksSignatureIsCorrect() throws IOException
    {
        ClassReader reader = new ClassReader(MIXIN_CLASS);
        ClassWriter writer = new ClassWriter(0);
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer)
        {
            @Override
            public MethodVisitor visitMethod(
                int access, String name, String descriptor, String signature,
                String[] exceptions
            )
            {
                if (name.equals(METHOD_NAME))
                {
                    return new MethodVisitor(
                        Opcodes.ASM9,
                        super.visitMethod(access, name, descriptor, signature, exceptions)
                    )
                    {
                        @Override
                        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible)
                        {
                            // Check @Inject annotation
                            if (descriptor.equals(METHOD_ANNOTATION))
                            {
                                assertTrue(
                                    (access & Opcodes.ACC_PRIVATE) != 0,
                                    "Method must be private"
                                );
                            }
                            return super.visitAnnotation(descriptor, visible);
                        }
                    };
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        },
        0);

        assertNotNull(
            writer.toByteArray(),
            "Class must exist"
        );
    }

    @Test
    public void mixinEntityPlayer_onPlayerUpdate_ChecksLogicMethodCall() throws IOException
    {
        List<String> operations = new ArrayList<>();
        List<String> methodCalls = new ArrayList<>();

        ClassReader reader = new ClassReader(MIXIN_CLASS);
        ClassWriter writer = new ClassWriter(0);
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer)
        {
            @Override
            public MethodVisitor visitMethod(
                int access, String name, String descriptor, String signature,
                String[] exceptions
            )
            {
                if (METHOD_NAME.equals(name) &&
                    METHOD_DESCRIPTOR.equals(descriptor))
                {
                    return new MethodVisitor(Opcodes.ASM9)
                    {
                        @Override
                        public void visitMethodInsn(
                            int opcode, String owner, String name,
                            String descriptor, boolean isInterface)
                        {
                            methodCalls.add(owner + "." + name + descriptor);
                            operations.add("INVOKE: " + name);
                        }

                        @Override
                        public void visitFieldInsn(int opcode, String owner, String name, String descriptor)
                        {
                            operations.add("FIELD: " + name);
                        }

                        @Override
                        public void visitLdcInsn(Object value)
                        {
                            operations.add("LDC: " + value);
                        }
                    };
                }
                return null;
            }
        },
        0);

        boolean callsCorrectMethod = methodCalls
            .stream()
            .anyMatch(s ->
                s.contains("MixinEntityPlayerLogic.onPlayerUpdate"));
        assertTrue(
            callsCorrectMethod,
            "Method should call MixinEntityPlayerLogic.onPlayerUpdate() method"
        );

        System.out.println("Operations in method:");
        operations.forEach(System.out::println);
    }
}
