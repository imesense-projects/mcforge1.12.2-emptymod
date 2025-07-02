package org.imesense.emptymod.mixin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for verifying the correctness of the {@link MixinEntityPlayer} class and its methods.
 * <p>
 * This class uses ASM (Java bytecode manipulation and analysis framework) to inspect
 * the mixin class's structure and behavior at the bytecode level.
 *
 * @see MixinEntityPlayer
 */
public class MixinEntityPlayerTest
{
    /**
     * Fully qualified name of the mixin class being tested.
     *
     * @see MixinEntityPlayer
     */
    private static final String MIXIN_CLASS =
        "org.imesense.emptymod.mixin.MixinEntityPlayer";

    /**
     * Name of the method being tested in the mixin class.
     */
    private static final String METHOD_NAME =
        "onPlayerUpdate";

    /**
     * Method descriptor (parameter and return types in JVM internal format).
     *
     * @see CallbackInfo
     */
    private static final String METHOD_DESCRIPTOR =
        "(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V";

    /**
     * Annotation descriptor for the {@link Inject} annotation in JVM internal format.
     *
     * @see Inject
     */
    private static final String METHOD_ANNOTATION =
        "Lorg/spongepowered/asm/mixin/injection/Inject;";

    /**
     * Tests that the mixin class has the correct signature for the target method.
     * Specifically verifies that the method is private and has the correct annotation.
     *
     * @throws IOException if there's an error reading the class file
     */
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

    /**
     * Tests that {@code onPlayerUpdate} method contains the expected bytecode instructions,
     * particularly verifying that it makes a call to {@code System.out.println}.
     * Also prints all operations found in the method for debugging purposes.
     *
     * @throws IOException if there's an error reading the class file
     */
    @Test
    public void mixinEntityPlayer_onPlayerUpdate_ChecksPrintMethodCall() throws IOException
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
                s.contains("println"));
        assertTrue(
            callsCorrectMethod,
            "Method should call System.out.println() method"
        );

        System.out.println("Operations in method:");
        operations.forEach(System.out::println);
    }
}
