package org.imesense.emptymod.transformer;

import net.minecraft.launchwrapper.IClassTransformer;

public final class ClassTransformer implements IClassTransformer
{
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        return basicClass;
    }
}
