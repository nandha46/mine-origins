package com.mineorigins.client.render;

import com.mineorigins.MineOrigins;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class EagleModel extends EntityModel<EagleRenderState> {
    public static final ModelLayerLocation EAGLE_LAYER = new ModelLayerLocation(
            MineOrigins.id("eagle"),
            "main"
    );

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;

    public EagleModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.leftWing = this.body.getChild("left_wing");
        this.rightWing = this.body.getChild("right_wing");
        this.tail = this.body.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.0f, -3.0f, -5.0f, 6.0f, 6.0f, 10.0f),
                PartPose.offset(0.0f, 16.0f, 0.0f)
        );

        // Head with hooked beak
        PartDefinition head = body.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-2.0f, -4.0f, -4.0f, 4.0f, 5.0f, 5.0f)
                        .texOffs(18, 16)
                        .addBox(-1.0f, -2.0f, -6.0f, 2.0f, 3.0f, 2.0f),
                PartPose.offset(0.0f, -1.0f, -4.5f)
        );

        // Left Wing
        body.addOrReplaceChild(
                "left_wing",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(0.0f, 0.0f, -4.0f, 12.0f, 1.0f, 8.0f),
                PartPose.offset(3.0f, -2.0f, 0.0f)
        );

        // Right Wing
        body.addOrReplaceChild(
                "right_wing",
                CubeListBuilder.create()
                        .texOffs(32, 9)
                        .addBox(-12.0f, 0.0f, -4.0f, 12.0f, 1.0f, 8.0f),
                PartPose.offset(-3.0f, -2.0f, 0.0f)
        );

        // Tail feathers
        body.addOrReplaceChild(
                "tail",
                CubeListBuilder.create()
                        .texOffs(0, 26)
                        .addBox(-2.5f, 0.0f, 0.0f, 5.0f, 1.0f, 6.0f),
                PartPose.offset(0.0f, -1.0f, 5.0f)
        );

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(EagleRenderState state) {
        super.setupAnim(state);
        this.resetPose();

        float flapCycle = state.flap;
        if (!state.isOnGround) {
            // Dynamic wing flapping motion
            float wingAngle = Mth.cos(flapCycle * 0.6f) * (float) Math.PI * 0.25f;
            this.leftWing.zRot = wingAngle;
            this.rightWing.zRot = -wingAngle;
            this.tail.xRot = 0.2f + Mth.sin(flapCycle * 0.3f) * 0.1f;
        } else {
            // Folded wings at rest
            this.leftWing.zRot = 0.8f;
            this.rightWing.zRot = -0.8f;
            this.tail.xRot = 0.3f;
        }

        // Slight pitch tilt when pitching or looking
        this.head.xRot = state.xRot * ((float) Math.PI / 180.0f);
        this.head.yRot = state.yRot * ((float) Math.PI / 180.0f);
    }
}
