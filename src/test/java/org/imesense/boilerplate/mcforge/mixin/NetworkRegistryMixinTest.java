package org.imesense.boilerplate.mcforge.mixin;

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
import org.objectweb.asm.Type;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for verifying the correctness of {@link NetworkRegistryMixin} class and its methods.
 * <p>
 * This class uses ASM (Java bytecode manipulation and analysis framework) to inspect
 * the mixin class's structure, annotations, and behavior at the bytecode level.
 *
 * @see NetworkRegistryMixin
 */
public class NetworkRegistryMixinTest
{
    /**
     * Fully qualified name of the mixin class being tested.
     *
     * @see NetworkRegistryMixin
     */
    private static final String MIXIN_CLASS =
        "org.imesense.boilerplate.mcforge.mixin.NetworkRegistryMixin";

    /**
     * Internal name of the target class for the {@link Mixin} annotation.
     *
     * @see Mixin
     * @see NetworkRegistry
     */
    private static final String TARGET_CLASS =
        "net/minecraftforge/fml/common/network/NetworkRegistry";

    /**
     * Name of the method being tested in the mixin class.
     */
    private static final String METHOD_NAME =
        "removeBukkit";

    /**
     * Method descriptor (return type in JVM internal format).
     *
     * @see Side
     */
    private static final String METHOD_DESCRIPTOR =
        "()[Lnet/minecraftforge/fml/relauncher/Side;";

    /**
     * Annotation descriptor for the {@link Mixin} annotation in JVM internal format.
     *
     * @see Mixin
     */
    private static final String MIXIN_ANNOTATION =
        "Lorg/spongepowered/asm/mixin/Mixin;";

    /**
     * Annotation descriptor for the {@link Redirect} annotation in JVM internal format.
     *
     * @see Redirect
     */
    private static final String REDIRECT_ANNOTATION =
        "Lorg/spongepowered/asm/mixin/injection/Redirect;";

    /**
     * Annotation descriptor for the {@link At} annotation in JVM internal format.
     *
     * @see At
     */
    private static final String AT_ANNOTATION =
        "Lorg/spongepowered/asm/mixin/injection/At;";

    /**
     * First target method descriptor for the {@link Redirect} annotation.
     */
    private static final String TARGET_METHOD_1 =
        "newChannel(Ljava/lang/String;[Lio/netty/channel/ChannelHandler;)Ljava/util/EnumMap;";

    /**
     * Second target method descriptor for the {@link Redirect} annotation.
     */
    private static final String TARGET_METHOD_2 =
        "newChannel(Lnet/minecraftforge/fml/common/ModContainer;Ljava/lang/String;[Lio/netty/channel/ChannelHandler;)Ljava/util/EnumMap;";

    /**
     * Target descriptor for the {@link At} annotation within {@link Redirect}.
     */
    private static final String AT_TARGET =
        "Lnet/minecraftforge/fml/relauncher/Side;values()[Lnet/minecraftforge/fml/relauncher/Side;";

    /**
     * Internal name of the {@link Side} enum class.
     *
     * @see Side
     */
    private static final String SIDE_INTERNAL_NAME =
        "net/minecraftforge/fml/relauncher/Side";

    /**
     * Tests that the mixin class has the correct {@link Mixin} annotation targeting
     * {@link NetworkRegistry} and that the class is abstract.
     *
     * @throws IOException if there's an error reading the class file
     */
    @Test
    public void networkRegistryMixin_Class_ChecksMixinAnnotationIsCorrect()
        throws IOException
    {
        ClassReader reader = new ClassReader(MIXIN_CLASS);
        ClassWriter writer = new ClassWriter(0);

        boolean[] isAbstract = { false };
        boolean[] hasMixinAnnotation = { false };
        boolean[] targetsNetworkRegistry = { false };

        reader.accept(new ClassVisitor(Opcodes.ASM5, writer)
        {
            @Override
            public void visit(
                int version, int access, String name, String signature,
                String superName, String[] interfaces
            )
            {
                isAbstract[0] = (access & Opcodes.ACC_ABSTRACT) != 0;
                super.visit(version, access, name, signature, superName, interfaces);
            }

            @Override
            public AnnotationVisitor visitAnnotation(String descriptor, boolean visible)
            {
                if (descriptor.equals(MIXIN_ANNOTATION))
                {
                    hasMixinAnnotation[0] = true;
                    return new AnnotationVisitor(Opcodes.ASM5)
                    {
                        @Override
                        public AnnotationVisitor visitArray(String name)
                        {
                            if ("value".equals(name))
                            {
                                return new AnnotationVisitor(Opcodes.ASM5)
                                {
                                    @Override
                                    public void visit(String name, Object value)
                                    {
                                        if (value instanceof Type)
                                        {
                                            Type type = (Type) value;
                                            if (TARGET_CLASS.equals(
                                                type.getInternalName()
                                            ))
                                            {
                                                targetsNetworkRegistry[0] = true;
                                            }
                                        }
                                    }
                                };
                            }
                            return super.visitArray(name);
                        }
                    };
                }
                return super.visitAnnotation(descriptor, visible);
            }
        },
        0);

        assertTrue(
            isAbstract[0],
            "Class must be abstract"
        );
        assertTrue(
            hasMixinAnnotation[0],
            "Class must have @Mixin annotation"
        );
        assertTrue(
            targetsNetworkRegistry[0],
            "Class must target NetworkRegistry"
        );
        assertNotNull(
            writer.toByteArray(),
            "Class must exist"
        );
    }

    /**
     * Tests that the {@code removeBukkit} method has the correct signature.
     * Specifically verifies that the method is private and has the correct return type.
     *
     * @throws IOException if there's an error reading the class file
     */
    @Test
    public void networkRegistryMixin_removeBukkit_ChecksSignatureIsCorrect()
        throws IOException
    {
        ClassReader reader = new ClassReader(MIXIN_CLASS);
        ClassWriter writer = new ClassWriter(0);

        boolean[] methodFound = { false };
        boolean[] isPrivate = { false };

        reader.accept(new ClassVisitor(Opcodes.ASM5, writer)
        {
            @Override
            public MethodVisitor visitMethod(
                int access, String name, String descriptor, String signature,
                String[] exceptions
            )
            {
                if (name.equals(METHOD_NAME) && descriptor.equals(METHOD_DESCRIPTOR))
                {
                    methodFound[0] = true;
                    isPrivate[0] = (access & Opcodes.ACC_PRIVATE) != 0;
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        },
        0);

        assertTrue(
            methodFound[0],
            "Method removeBukkit must exist with correct descriptor"
        );
        assertTrue(
            isPrivate[0],
            "Method must be private"
        );
        assertNotNull(
            writer.toByteArray(),
            "Class must exist"
        );
    }

    /**
     * Tests that the {@code removeBukkit} method has the correct {@link Redirect}
     * annotation with the expected method targets, {@link At} injection point,
     * and remap setting.
     *
     * @throws IOException if there's an error reading the class file
     */
    @Test
    public void networkRegistryMixin_removeBukkit_ChecksRedirectAnnotationIsCorrect()
        throws IOException
    {
        ClassReader reader = new ClassReader(MIXIN_CLASS);
        ClassWriter writer = new ClassWriter(0);

        boolean[] hasRedirectAnnotation = { false };
        List<String> methodTargets = new ArrayList<>();
        boolean[] atValueIsInvoke = { false };
        boolean[] atTargetIsCorrect = { false };
        boolean[] remapIsFalse = { false };

        reader.accept(new ClassVisitor(Opcodes.ASM5, writer)
        {
            @Override
            public MethodVisitor visitMethod(
                int access, String name, String descriptor, String signature,
                String[] exceptions
            )
            {
                if (name.equals(METHOD_NAME) && descriptor.equals(METHOD_DESCRIPTOR))
                {
                    return new MethodVisitor(
                        Opcodes.ASM5,
                        super.visitMethod(access, name, descriptor, signature, exceptions)
                    )
                    {
                        @Override
                        public AnnotationVisitor visitAnnotation(
                            String descriptor, boolean visible
                        )
                        {
                            if (descriptor.equals(REDIRECT_ANNOTATION))
                            {
                                hasRedirectAnnotation[0] = true;
                                return new AnnotationVisitor(Opcodes.ASM5)
                                {
                                    @Override
                                    public AnnotationVisitor visitArray(String name)
                                    {
                                        if ("method".equals(name))
                                        {
                                            return new AnnotationVisitor(Opcodes.ASM5)
                                            {
                                                @Override
                                                public void visit(
                                                    String name, Object value
                                                )
                                                {
                                                    if (value instanceof String)
                                                    {
                                                        methodTargets.add((String) value);
                                                    }
                                                }
                                            };
                                        }
                                        return super.visitArray(name);
                                    }

                                    @Override
                                    public AnnotationVisitor visitAnnotation(
                                        String name, String descriptor
                                    )
                                    {
                                        if ("at".equals(name) &&
                                            descriptor.equals(AT_ANNOTATION))
                                        {
                                            return new AnnotationVisitor(Opcodes.ASM5)
                                            {
                                                @Override
                                                public void visit(
                                                    String name, Object value
                                                )
                                                {
                                                    if ("value".equals(name) &&
                                                        "INVOKE".equals(value))
                                                    {
                                                        atValueIsInvoke[0] = true;
                                                    }
                                                    if ("target".equals(name) &&
                                                        AT_TARGET.equals(value))
                                                    {
                                                        atTargetIsCorrect[0] = true;
                                                    }
                                                }
                                            };
                                        }
                                        return super.visitAnnotation(name, descriptor);
                                    }

                                    @Override
                                    public void visit(String name, Object value)
                                    {
                                        if ("remap".equals(name) &&
                                            Boolean.FALSE.equals(value))
                                        {
                                            remapIsFalse[0] = true;
                                        }
                                    }
                                };
                            }
                            return super.visitAnnotation(descriptor, visible);
                        }
                    };
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        },
        0);

        assertTrue(
            hasRedirectAnnotation[0],
            "Method must have @Redirect annotation"
        );
        assertEquals(
            2,
            methodTargets.size(),
            "Redirect must target exactly 2 methods"
        );
        assertTrue(
            methodTargets.contains(TARGET_METHOD_1),
            "Redirect must target first newChannel method"
        );
        assertTrue(
            methodTargets.contains(TARGET_METHOD_2),
            "Redirect must target second newChannel method"
        );
        assertTrue(
            atValueIsInvoke[0],
            "At annotation value must be INVOKE"
        );
        assertTrue(
            atTargetIsCorrect[0],
            "At annotation target must be Side.values()"
        );
        assertTrue(
            remapIsFalse[0],
            "Remap must be false"
        );
    }

    /**
     * Tests that the {@code removeBukkit} method returns an array containing only
     * {@link Side#CLIENT} and {@link Side#SERVER}, verified at the bytecode level
     * by inspecting {@code GETSTATIC} field access instructions.
     *
     * @throws IOException if there's an error reading the class file
     */
    @Test
    public void networkRegistryMixin_removeBukkit_ChecksReturnsCorrectSides()
        throws IOException
    {
        ClassReader reader = new ClassReader(MIXIN_CLASS);
        ClassWriter writer = new ClassWriter(0);

        List<String> staticFields = new ArrayList<>();

        reader.accept(new ClassVisitor(Opcodes.ASM5, writer)
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
                    return new MethodVisitor(Opcodes.ASM5)
                    {
                        @Override
                        public void visitFieldInsn(
                            int opcode, String owner, String name, String descriptor
                        )
                        {
                            if (opcode == Opcodes.GETSTATIC &&
                                SIDE_INTERNAL_NAME.equals(owner))
                            {
                                staticFields.add(name);
                            }
                        }
                    };
                }
                return null;
            }
        },
        0);

        assertEquals(
            2,
            staticFields.size(),
            "Method must access exactly 2 static fields of Side"
        );
        assertTrue(
            staticFields.contains("CLIENT"),
            "Method must access Side.CLIENT"
        );
        assertTrue(
            staticFields.contains("SERVER"),
            "Method must access Side.SERVER"
        );
        assertFalse(
            staticFields.contains("BUKKIT"),
            "Method must not access Side.BUKKIT"
        );
    }
}
