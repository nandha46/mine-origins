package com.mineorigins.component;

import java.util.function.Consumer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public record AdvancedCustomComponent(int aluminiumCapacity) implements TooltipProvider {

    public static final Codec<AdvancedCustomComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("aluminiumCapacity").forGetter(AdvancedCustomComponent::aluminiumCapacity)
    ).apply(builder, AdvancedCustomComponent::new));

    public static final StreamCodec<FriendlyByteBuf, AdvancedCustomComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            AdvancedCustomComponent::aluminiumCapacity,
            AdvancedCustomComponent::new
    );

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag flag,
            DataComponentGetter components) {
        tooltip.accept(Component.translatable("item.mine-origins.aluminium_capacity.info", this.aluminiumCapacity)
                .withStyle(ChatFormatting.GOLD));
    }
}