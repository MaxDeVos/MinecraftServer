package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class EntityDragonFireball extends EntityFireball {

    public EntityDragonFireball(EntityTypes<? extends EntityDragonFireball> entitytypes, World world) {
        super(entitytypes, world);
    }

    public EntityDragonFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
        super(EntityTypes.DRAGON_FIREBALL, entityliving, d0, d1, d2, world);
    }

    @Override
    protected void a(MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.getType() != MovingObjectPosition.EnumMovingObjectType.ENTITY || !((MovingObjectPositionEntity) movingobjectposition).getEntity().s(this.shooter)) {
            if (!this.world.isClientSide) {
                List<EntityLiving> list = this.world.a(EntityLiving.class, this.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
                EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.locX, this.locY, this.locZ);

                entityareaeffectcloud.setSource(this.shooter);
                entityareaeffectcloud.setParticle(Particles.DRAGON_BREATH);
                entityareaeffectcloud.setRadius(3.0F);
                entityareaeffectcloud.setDuration(600);
                entityareaeffectcloud.setRadiusPerTick((7.0F - entityareaeffectcloud.getRadius()) / (float) entityareaeffectcloud.getDuration());
                entityareaeffectcloud.a(new MobEffect(MobEffects.HARM, 1, 1));
                if (!list.isEmpty()) {
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext()) {
                        EntityLiving entityliving = (EntityLiving) iterator.next();
                        double d0 = this.h(entityliving);

                        if (d0 < 16.0D) {
                            entityareaeffectcloud.setPosition(entityliving.locX, entityliving.locY, entityliving.locZ);
                            break;
                        }
                    }
                }

                this.world.triggerEffect(2006, new BlockPosition(this.locX, this.locY, this.locZ), 0);
                this.world.addEntity(entityareaeffectcloud);
                this.die();
            }

        }
    }

    @Override
    public boolean isInteractable() {
        return false;
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        return false;
    }

    @Override
    protected ParticleParam i() {
        return Particles.DRAGON_BREATH;
    }

    @Override
    protected boolean L_() {
        return false;
    }
}
