package moze_intel.projecte.gameObjs.items.armor;

import javax.annotation.Nonnull;
import moze_intel.projecte.PECore;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class RMArmor extends PEArmor {

	public RMArmor(EquipmentSlotType armorType, Properties props) {
		super(RMArmorMaterial.INSTANCE, armorType, props);
	}

	@Override
	public float getFullSetBaseReduction() {
		return 0;
	}

	@Override
	public float getMaxDamageAbsorb(EquipmentSlotType slot, DamageSource source) {
		if (source.isExplosion()) {
			return 0.5f;
		}
		if (slot == EquipmentSlotType.FEET && source == DamageSource.FALL) {
			return 0.1f / getPieceEffectiveness(slot);
		} else if (slot == EquipmentSlotType.HEAD && source == DamageSource.DROWN) {
			return 0.1f / getPieceEffectiveness(slot);
		}
		if (source.isUnblockable()) {
			return 0;
		}
		//If the source is not unblockable, allow our piece to block a certain amount of damage
		if (slot == EquipmentSlotType.HEAD || slot == EquipmentSlotType.FEET) {
			return 0.25f;
		}
		return 0.35f;
	}

	private static class RMArmorMaterial implements IArmorMaterial {

		private static final RMArmorMaterial INSTANCE = new RMArmorMaterial();

		@Override
		public int getDurability(@Nonnull EquipmentSlotType slot) {
			return 0;
		}

		@Override
		public int getDamageReductionAmount(@Nonnull EquipmentSlotType slot) {
			if (slot == EquipmentSlotType.FEET) {
				return 3;
			} else if (slot == EquipmentSlotType.LEGS) {
				return 6;
			} else if (slot == EquipmentSlotType.CHEST) {
				return 8;
			} else if (slot == EquipmentSlotType.HEAD) {
				return 3;
			}
			return 0;
		}

		@Override
		public int getEnchantability() {
			return 1;
		}

		@Nonnull
		@Override
		public SoundEvent getSoundEvent() {
			return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
		}

		@Nonnull
		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.EMPTY;
		}

		@Nonnull
		@Override
		public String getName() {
			//Only used on the client
			return PECore.rl("red_matter").toString();
		}

		@Override
		public float getToughness() {
			return 0.3f;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.2F;
		}
	}
}
