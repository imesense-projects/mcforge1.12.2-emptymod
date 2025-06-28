package org.imesense.boilerplate.mcforge.mixin;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import net.minecraft.entity.player.EntityPlayer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;

public class MixinEntityPlayerLogicTest
{
    @Test
    public void testOnPlayerUpdate_PrintsCorrectMessage()
    {
        EntityPlayer mockPlayer = Mockito.mock(EntityPlayer.class);
        when(mockPlayer.getName()).thenReturn("TestPlayer");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try
        {
            MixinEntityPlayerLogic.onPlayerUpdate(mockPlayer);
            assertTrue(outContent.toString().contains("Player TestPlayer updated!"));
        }
        finally
        {
            System.setOut(originalOut);
        }
    }
}
