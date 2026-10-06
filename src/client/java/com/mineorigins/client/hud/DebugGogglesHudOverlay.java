package com.mineorigins.client.hud;

import com.mineorigins.Item.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DebugGogglesHudOverlay implements HudElement {

    public static final DebugGogglesHudOverlay INSTANCE = new DebugGogglesHudOverlay();

    @Override
    public void extractRenderState(GuiGraphicsExtractor extractor, DeltaTracker deltaTracker) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;

        if (player == null || (client.gui != null && client.gui.hud.isHidden())) {
            return;
        }

        // Only display when player is wearing DEBUG_GOGGLES in the HEAD slot
        ItemStack headItem = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!headItem.is(ModItems.DEBUG_GOGGLES)) {
            return;
        }

        // If vanilla F3 debug overlay is already active, don't overlap
        if (client.getDebugOverlay().showDebugScreen()) {
            return;
        }

        Font font = client.font;
        int fps = client.getFps();
        BlockPos pos = player.blockPosition();

        Direction facing = player.getDirection();
        String biome = client.level != null
                ? client.level.getBiome(pos).unwrapKey().map(k -> k.identifier().getPath()).orElse("unknown")
                : "unknown";

        // Formatted mini debug lines
        String fpsText = "FPS: " + fps;
        String xyzText = String.format("XYZ: %d / %d / %d", pos.getX(), pos.getY(), pos.getZ());
        String dirText = "Facing: " + facing.getName() + " (" + facing.getAxis().getName().toUpperCase() + ")";
        String biomeText = "Biome: " + biome;

        String[] lines = new String[]{fpsText, xyzText, dirText, biomeText};

        int startX = 6;
        int startY = 6;
        int lineHeight = 11;
        int padding = 4;

        int maxWidth = 0;
        for (String line : lines) {
            int width = font.width(line);
            if (width > maxWidth) {
                maxWidth = width;
            }
        }

        // Render translucent dark background panel with cyan cyber border
        int boxRight = startX + maxWidth + padding * 2;
        int boxBottom = startY + (lines.length * lineHeight) + padding;

        // Background: 0x900B131F (deep tech navy translucent)
        extractor.fill(startX - 2, startY - 2, boxRight, boxBottom, 0x900B131F);
        // Cyan accent border on left edge
        extractor.fill(startX - 2, startY - 2, startX, boxBottom, 0xFF00E5FF);

        // Render text lines with drop shadow
        int currentY = startY + 2;
        // FPS line: bright green if >= 60, yellow if >= 30, red if < 30
        int fpsColor = fps >= 60 ? 0xFF55FF55 : (fps >= 30 ? 0xFFFFFF55 : 0xFFFF5555);
        extractor.text(font, fpsText, startX + padding, currentY, fpsColor, true);
        currentY += lineHeight;

        // Coordinates line: bright cyan
        extractor.text(font, xyzText, startX + padding, currentY, 0xFF00E5FF, true);
        currentY += lineHeight;

        // Facing line: soft white/cyan
        extractor.text(font, dirText, startX + padding, currentY, 0xFFE0F7FA, true);
        currentY += lineHeight;

        // Biome line: gold/light yellow
        extractor.text(font, biomeText, startX + padding, currentY, 0xFFFFD54F, true);
    }
}
